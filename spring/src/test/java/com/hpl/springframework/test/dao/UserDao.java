package com.hpl.springframework.test.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/8 20:29
 */
public class UserDao {

    private static final Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "hhh");
        hashMap.put("10002", "ppp");
        hashMap.put("10003", "lll");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

}