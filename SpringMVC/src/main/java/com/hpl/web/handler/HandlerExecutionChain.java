package com.hpl.web.handler;

import com.hpl.web.interceptor.HandlerInterceptor;
import com.hpl.web.interceptor.MappedInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 15:37
 */
public class HandlerExecutionChain {
    private final HandlerMethod handlerMethod;

    private final List<HandlerInterceptor> interceptors = new ArrayList<>();


    public HandlerExecutionChain(HandlerMethod handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    public HandlerMethod getHandlerMethod() {
        return handlerMethod;
    }

    public void setInterceptors(List<HandlerInterceptor> interceptors) {
        for (HandlerInterceptor interceptor : interceptors) {
            if(interceptor instanceof MappedInterceptor){
                if( ((MappedInterceptor) interceptor).match(handlerMethod.getPath())){
                    this.interceptors.add(interceptor);
                }
            }
            else{
                this.interceptors.add(interceptor);
            }
        }
    }

    public boolean applyPreInterceptor(HttpServletRequest req, HttpServletResponse resp) {
        for (HandlerInterceptor interceptor : this.interceptors) {
            if (!interceptor.preHandle(req,resp)) {
                return false;
            }
        }
        return true;
    }

    public void applyPostInterceptor(HttpServletRequest req, HttpServletResponse resp) {

        for (HandlerInterceptor interceptor : this.interceptors) {
            interceptor.postHandle(req,resp);
        }
    }

    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, HandlerMethod handlerMethod, Exception ex) {
        for (HandlerInterceptor interceptor : this.interceptors) {
            interceptor.afterCompletion(req,resp,handlerMethod,ex);
        }
    }
}
