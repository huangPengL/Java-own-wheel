package com.hpl.web.context;

import org.springframework.beans.factory.Aware;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/6 11:41
 */
public interface ServletConfigAware extends Aware {
    void setServletConfig(ServletConfig servletContext);
}
