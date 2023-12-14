package com.hpl.web.support;

import javax.servlet.ServletContext;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 10:58
 */
public interface WebApplicationInitializer {

    void onStartUp(ServletContext servletContext);
}
