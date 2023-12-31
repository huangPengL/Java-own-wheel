package com.hpl.web.handler;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 15:54
 */
public class BeanNameUrlHandlerMapping extends AbstractHandlerMapping{

    @Override
    protected HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception {
        return lookUpPath(request);
    }

    @Override
    protected void detectHandlerMethod(String name) throws Exception {

    }

    @Override
    protected boolean isHandler(Class<?> type) {
        return false;
    }
}
