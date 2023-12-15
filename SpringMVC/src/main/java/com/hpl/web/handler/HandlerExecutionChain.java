package com.hpl.web.handler;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 15:37
 */
public class HandlerExecutionChain {
    private final HandlerMethod handlerMethod;


    public HandlerExecutionChain(HandlerMethod handlerMethod) {
        this.handlerMethod = handlerMethod;
    }
}
