package com.gszcn.security.config;

import com.gszcn.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/11/30 上午11:49
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService myUserDetailsService;
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
        // 自定义自己的登录页面
        http.formLogin()
                // 登录页面设置
                .loginPage("/login.html")
                // 登录访问路径
                .loginProcessingUrl("/user/login")
                // 登录成功之后的跳转路径
                .defaultSuccessUrl("/test/index").permitAll()
                .and().authorizeRequests()
                    // 放行路径
                    .antMatchers("/","/user/login","/test/hello").permitAll()
                .anyRequest().authenticated()
                // 关闭csrf防护
                .and().csrf().disable();
    }
}
