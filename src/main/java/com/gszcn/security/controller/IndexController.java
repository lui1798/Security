package com.gszcn.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/12/9 下午6:14
 */
@Controller
@RequestMapping
public class IndexController {
    @GetMapping({"/"})
    public String getUserInfo() {
        return "redirect:/index.html";
    }
}
