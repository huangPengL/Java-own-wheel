package com.hpl.web.context;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/6 11:25
 */
public interface ConfigurableWebApplicationContext extends WebApplicationContext{

    void setServletContext(ServletContext servletContext);

    void setServletConfig(ServletConfig servletConfig);

    ServletContext getServletContext();

    ServletConfig getServletConfig();
}
