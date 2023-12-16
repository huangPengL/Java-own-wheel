package com.hpl.web.adapter;

import com.hpl.web.handler.HandlerMethod;
import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理器适配器（参数解析，类型转换，返回值处理、异常处理）
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:07
 */
public interface HandlerMethodAdapter extends Ordered {

    /**
     * 若支持适配，则调用handler进行适配操作
     * @param handlerMethod
     * @return
     */
    boolean support(HandlerMethod handlerMethod);

    /**
     * 适配器核心方法
     * @param request
     * @param response
     * @param handlerMethod
     * @throws Exception
     */
    void handler(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception;
}
