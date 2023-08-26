package com.hpl.springboot.webserver;

/**
 * @Author: huangpenglong
 * @Date: 2023/8/1 11:15
 */
public class JettyServer implements WebServer{
    @Override
    public void start() {
        System.out.println("启动jetty");
    }
}
