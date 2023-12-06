package com.hpl.web.context;

import org.springframework.beans.factory.Aware;

import javax.servlet.ServletContext;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/6 11:40
 */
public interface ServletContextAware extends Aware {
    void setServletContext(ServletContext servletContext);
}
