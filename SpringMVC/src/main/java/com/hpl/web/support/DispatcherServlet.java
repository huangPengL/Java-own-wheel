package com.hpl.web.support;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 10:56
 */
public class DispatcherServlet extends BaseHttpServlet{

    public DispatcherServlet(ApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    /**
     * 组件初始化
     * @param webApplicationContext
     */
    @Override
    protected void onRefresh(ApplicationContext webApplicationContext) {
        initHandlerMapping(webApplicationContext);
        initHandlerMethodAdapter(webApplicationContext);
        initHandlerException(webApplicationContext);
    }

    private void initHandlerException(ApplicationContext webApplicationContext) {
    }

    private void initHandlerMethodAdapter(ApplicationContext webApplicationContext) {
    }

    private void initHandlerMapping(ApplicationContext webApplicationContext) {
        
    }
}
