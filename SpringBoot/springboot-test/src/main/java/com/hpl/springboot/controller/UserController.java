package com.hpl.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: huangpenglong
 * @Date: 2023/8/1 0:47
 */

@RestController
public class UserController {

    @GetMapping("/user/list")
    public String list(){
        return "[hpl, hhh, xxx]";
    }
}
