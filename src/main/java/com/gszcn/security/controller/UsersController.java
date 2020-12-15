package com.gszcn.security.controller;

import com.gszcn.security.entity.Users;
import com.gszcn.security.service.MyUserDetailsService;
import com.gszcn.security.until.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R register(@RequestBody Users users){
        Integer register = myUserDetailsService.register(users);
        if (register>0){
            return R.ok().msg("注册成功");
        }
        return R.ok().msg("注册失败");
    }

    @PostMapping("changePassword")
    @ResponseBody
    public R changePassword(String oldPassword, String password){
            myUserDetailsService.changePassword(oldPassword,password);
            return R.ok().msg("修改密码成功");
    }
}
