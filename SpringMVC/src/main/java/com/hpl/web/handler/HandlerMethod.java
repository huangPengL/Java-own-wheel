package com.hpl.web.handler;

import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 15:45
 */
public class HandlerMethod {

    private Object bean;

    private Class type;

    private Method method;

    private MethodParameter[] parameters = new MethodParameter[0];
}
