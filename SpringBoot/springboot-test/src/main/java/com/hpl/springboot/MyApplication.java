package com.hpl.springboot;

import com.hpl.springboot.annotation.HplSpringBootApplication;

/**
 * @Author: huangpenglong
 * @Date: 2023/7/31 17:53
 */

@HplSpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        HplSpringApplication.run(MyApplication.class);
    }
}
