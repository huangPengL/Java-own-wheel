package com.hpl.web.context;

import org.springframework.context.ApplicationContext;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/6 11:24
 */
public interface WebApplicationContext extends ApplicationContext {
    public static final String ROOT_NAME =  WebApplicationContext.class.getName() + "ROOT";
}
