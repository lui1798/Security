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
    public String getUserInfo(String a) throws Exception {
        String b= a+"你好！";
        log.info(b);
        return b;
    }

    @PostMapping("helloPost")
    @ResponseBody
    public String helloPost(String name) throws Exception {
        String b= name+"你好！这是post请求！";
        log.info(b);
        return b;
    }


}
