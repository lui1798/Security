package com.gszcn.security.config;

import com.gszcn.security.filter.ValidateFilter;
import com.gszcn.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/11/30 上午11:49
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private ValidateFilter validateFilter;
    @Autowired
    UserAuthenticionFailureHandler userAuthenticionFailureHandler;
    @Autowired
    UserAuthenticionSuccessHandler userAuthenticionSuccessHandler;
    @Autowired
    private DataSource dataSource;
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 自动创建"记住我"表
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passWord());
    }

    @Bean
    PasswordEncoder passWord(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.userDetailsService(myUserDetailsService).authorizeRequests()
            .and()
                //添加前置过滤器（这里是验证码）
                .addFilterBefore(validateFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
            .and().
                // 自定义自己的登录页面
                formLogin()
                // 登录页面设置
                .loginPage("/login.html")
                // 登录访问路径
                .loginProcessingUrl("/users/login")
                .failureHandler(userAuthenticionFailureHandler)
                .successHandler(userAuthenticionSuccessHandler)
                // 登录成功之后的跳转路径.defaultSuccessUrl("/test/index").permitAll()
//                .defaultSuccessUrl("/loginSuccess.html").permitAll()
            .and()
                // 登出配置
                .logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccess.html").permitAll()
            .and()
                .authorizeRequests()
                // 放行路径
                .antMatchers("/","/login.html","/user/login","/test/hello","/users/**","/public/**","/register.html").permitAll()
                // 权限。
                // 登录用户具有admins权限才能访问，单个权限：hasAuthority("admins") 多个权限：hasAnyAuthority("admins,manager")
//                .antMatchers("/test/index").hasAnyAuthority("admins")
                // 角色。
                // 登录用户拥有，单个角色hasRole("sale");多个角色hasAnyRole("sale,role1")
                .antMatchers("/test/index").hasAnyRole("sale,role").anyRequest().authenticated()
            .and()
                // 403没有权限页面配置
                .exceptionHandling().accessDeniedPage("/unauth.html")
            .and()
                //记住我
                .rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(60).userDetailsService(myUserDetailsService)
            .and()
                // 关闭csrf防护
                .csrf().disable();
    }
}
