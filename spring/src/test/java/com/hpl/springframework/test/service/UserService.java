package com.hpl.springframework.test.service;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:45
 */
public class UserService {

    private String name;

    public UserService(String name) {
        this.name = name;
    }

    public void queryUserInfo(){
        System.out.println("查询用户信息" + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

