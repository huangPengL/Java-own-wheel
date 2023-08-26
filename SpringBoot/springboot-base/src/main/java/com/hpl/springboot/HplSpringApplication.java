package com.hpl.springboot;

import com.hpl.springboot.webserver.WebServer;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Map;


/**
 * @Author: huangpenglong
 * @Date: 2023/7/31 17:40
 */

public class HplSpringApplication {

    /**
     * 所以在run方法中，我们目前要实现的逻辑如下：
     * 1 创建一个Spring容器
     * 2 注册配置类
     * 3 生成DispatcherServlet对象，并且和前面创建出来的Spring容器进行绑定
     * 4 将DispatcherServlet添加到Tomcat中
     * 5 启动Tomcat
     * @param clazz
     */
    public static void run(Class clazz){
        // 1 创建一个空Spring容器
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();

        // 2 注册配置类，真正创建Spring容器（后续会对这个类所在的包进行解析，把带有Spring bean注解的对象注入进容器中）
        // 通过这样的方式，配置类中定义的Bean将会被Spring实例化，并可以在应用程序中被使用。
        applicationContext.register(clazz);
        applicationContext.refresh();

        // 5 启动web服务器
        startWebServer(applicationContext);

    }

    private static void startWebServer(WebApplicationContext applicationContext) {
        WebServer webServer = getWebServer(applicationContext);
        webServer.start();
    }

    /**
     * 我们希望根据项目中的依赖情况，来决定到底用哪个WebServer
     * @param applicationContext
     * @return
     */
    private static WebServer getWebServer(WebApplicationContext applicationContext) {
        // key为beanName, value为Bean对象
        Map<String, WebServer> webServers = applicationContext.getBeansOfType(WebServer.class);

        if (webServers.isEmpty()) {
            throw new NullPointerException();
        }
        if (webServers.size() > 1) {
            throw new IllegalStateException();
        }

        // 返回唯一的一个
        return webServers.values().stream().findFirst().get();
    }

}
