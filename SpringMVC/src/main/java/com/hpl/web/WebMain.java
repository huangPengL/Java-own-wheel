package com.hpl.web;

import com.hpl.web.context.AnnotationConfigWebApplicationContext;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/6 11:53
 */
public class WebMain {

    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        context.register(WebConfig.class);

        context.refresh();

        WebService bean = context.getBean(WebService.class);
        System.out.println(bean);
    }
}
