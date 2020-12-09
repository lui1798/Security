package com.gszcn.security.controller;

import com.gszcn.security.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/11/25 下午5:53
 */
@RestController
@RequestMapping("test")
@Slf4j
public class DemoController {


    @GetMapping("hello")
    public String getUserInfo() {
        String b= "你好！";
        return b;
    }

    @GetMapping("logout")
    public String logout() {
        String b= "退出成功！";
        return b;
    }

    @GetMapping("index")
    public String helloPost() {
        String b= "index需要登录后才能看到的文字！";
        return b;
    }

    /**
     * 角色控制,有角色才可以访问
     * Secured({"ROLE_sale"})，需要在MyUserDetailsService中配置角色
     * @return
     */
    @GetMapping("saleRole")
    @Secured({"ROLE_sale"})
    public String saleRole() {
        String b= "@Secured注解拥有sale角色才能访问的页面！";
        return b;
    }
    @GetMapping("updateRole")
    @Secured({"ROLE_update"})
    public String updateRole() {
        String b= "@Secured注解拥有update角色才能访问的页面！";
        return b;
    }

    /**
     * 权限控制，有权限才可以访问（也可以通过注解里面的方法进行角色控制）
     * PreAuthorize("hasAnyAuthority('admin','manager')")，需要在MyUserDetailsService中配置权限
     * @return
     */
    @GetMapping("adminAuthorize")
    @PreAuthorize("hasAnyAuthority('admins','manager')")
    public String adminAuthorize() {
        System.out.println("preAuthorize");
        String b= "@PreAuthorize注解拥有admin，manager权限才能访问的页面！";
        return b;
    }
    @GetMapping("generalAuthorize")
    @PreAuthorize("hasAnyAuthority('general')")
    public String generalAuthorize() {
        String b= "@PreAuthorize注解拥有general权限才能访问的页面！";
        return b;
    }

    /**
     * 方法执行完成后的权限控制，有权限才可以访问（也可以通过注解里面的方法进行角色控制）
     * PreAuthorize("hasAnyAuthority('admin','manager')")，需要在MyUserDetailsService中配置权限
     * @return
     */
    @GetMapping("adminPostAuthorize")
    @PostAuthorize("hasAnyAuthority('admins','manager')")
    public String adminPostAuthorize() {
        log.info("\n方法执行了！");
        String b= "@PreAuthorize注解拥有admin，manager权限才能访问的页面！";
        return b;
    }
    @GetMapping("generalPostAuthorize")
    @PostAuthorize("hasAnyAuthority('general')")
    public String generalPostAuthorize() {
        log.info("\n方法执行了！");
        String b= "@PreAuthorize注解拥有general权限才能访问的页面！";
        return b;
    }

    /**
     * PostFilter,通过注解里面的过滤方式过滤部分内容
     * 这里 留下用户名是 admin1 的数据
     * 权限校验 （也可以通过注解里面的方法进行角色控制）
     * @return
     */
    @GetMapping("resultFilter")
    @PreAuthorize("hasAnyAuthority('admins','manager')")
    @PostFilter("filterObject.username == 'admin1'")
    public List<Users> resultFilter() {
        List<Users> list = new ArrayList<>();
        list.add(new Users("11","admin1","6666"));
        list.add(new Users("22","admin2","8888"));
        list.add(new Users("33","admin3","2222"));
        list.add(new Users("44","admin4","4444"));
        System.out.println(list);
        return list;
    }

    /**
     * 传入方法的参数进行过滤
     * 角色控制
     * @param list
     * @return
     */
    @RequestMapping("requestFilter")
    @PreAuthorize("hasRole('ROLE_role')")
    @PreFilter(value = "filterObject.id%2==0")
    public List<Users> requestFilter(@RequestBody List<Users> list){
        list.forEach(t-> {
            System.out.println(t.getId()+"\t"+t.getUsername());
        });
        return list;
    }
}
