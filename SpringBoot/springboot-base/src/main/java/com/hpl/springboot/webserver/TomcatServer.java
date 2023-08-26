package com.hpl.springboot.webserver;

/**
 * @Author: huangpenglong
 * @Date: 2023/8/1 11:14
 */
public class TomcatServer implements WebServer{
    @Override
    public void start() {
        System.out.println("启动tomcat");
    }
}
