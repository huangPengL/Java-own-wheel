package com.hpl.web.interceptor;

import com.hpl.web.handler.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/28 21:57
 */
public interface HandlerInterceptor {
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response){
        return true;
    }

    default void  postHandle(HttpServletRequest request, HttpServletResponse response){}

    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler,
                                 Exception ex){
    }
}
