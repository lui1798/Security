package com.gszcn.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    public String getUserInfo() {
        String b= "你好！";
        return b;
    }

    @GetMapping("index")
    @ResponseBody
    public String helloPost() {
        String b= "成功登录之后跳转来到这个页面！";
        return b;
    }


}
