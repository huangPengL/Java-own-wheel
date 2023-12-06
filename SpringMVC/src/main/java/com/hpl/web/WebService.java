package com.hpl.web;

import com.hpl.web.context.ServletContextAware;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/6 11:53
 */

@Service
public class WebService implements ServletContextAware{

    @Override
    public void setServletContext(ServletContext servletContext) {
        System.out.println(servletContext);
        System.out.println("WebService get ServletContext");
    }
}
