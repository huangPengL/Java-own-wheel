package com.hpl.spring.web.config;

import com.hpl.web.annotation.EnableWebMvc;
import com.hpl.web.dispatcher.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.util.ObjectUtils;

import javax.servlet.MultipartConfigElement;

/**
 * @Author: huangpenglong
 * @Date: 2024/1/3 14:30
 */

@EnableWebMvc
public class WebAutoConfiguration extends ApplicationObjectSupport {
    private static final int M = 1024 * 1024;

    @Bean
    public ServerProperties serverProperties(){
        return new ServerProperties();
    }

    @Bean
    public MultipartFileProperties multipartFileProperties(){
        return new MultipartFileProperties();
    }

    @Bean
    public Tomcat tomcat(ServerProperties serverProperties, MultipartFileProperties multipartFileProperties) throws LifecycleException {
        Integer port = serverProperties.getPort();
        final Tomcat tomcat = new Tomcat();
        port = ObjectUtils.isEmpty(port) ? 8080 : port;
        tomcat.setPort(port);
        tomcat.setHostname("localhost");
        tomcat.setBaseDir(".");
        final Context context = tomcat.addWebapp("/", System.getProperty("user.dir") + "/src/main");
        final ApplicationContext applicationContext = obtainApplicationContext();
        final Wrapper wrapper =
                tomcat.addServlet(context, "hpl-web", new DispatcherServlet((applicationContext)));
        Long fileSize = multipartFileProperties.getFileSize();
        fileSize = fileSize!=null ? fileSize : 5;
        wrapper.setMultipartConfigElement(new MultipartConfigElement(null,  fileSize* M, fileSize * M,5));
        wrapper.addMapping("/");
        tomcat.getConnector();
        tomcat.start();
        logger.info("Tomcat initialized with port: "+port);
        tomcat.getServer().await();
        return tomcat;
    }
}
