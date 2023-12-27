package com.hpl.web.handler;

import java.lang.reflect.Method;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/27 16:17
 */
public class ExceptionHandlerMethod extends HandlerMethod{
    public ExceptionHandlerMethod(Object bean, Method method) {
        super(bean, method);
    }
}
