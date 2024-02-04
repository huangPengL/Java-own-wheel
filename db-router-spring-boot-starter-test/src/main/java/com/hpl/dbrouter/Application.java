package com.hpl.dbrouter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/3 23:04
 */

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"com.hpl.dbrouter.starter"})
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }
}
