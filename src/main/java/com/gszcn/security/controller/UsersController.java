package com.gszcn.security.controller;

import com.gszcn.security.entity.Users;
import com.gszcn.security.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/12/9 上午11:11
 */
@RestController
@RequestMapping("users")
@Slf4j
public class UsersController {
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @PostMapping("register")
    public String register(@RequestBody Users users){
        Integer register = myUserDetailsService.register(users);
        if (register>0){
            return "注册成功！";
        }
        return "注册失败！";
    }

    @PostMapping("changePassword")
    public String changePassword(String oldPassword, String password){
        try{
            myUserDetailsService.changePassword(oldPassword,password);
            return "修改成功";
        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
