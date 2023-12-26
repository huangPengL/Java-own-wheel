package com.hpl.web.resolver;

import com.hpl.web.support.WebServletRequest;

import java.lang.reflect.Method;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/26 16:16
 */
public interface HandlerMethodReturnValueHandler {

    /**
     * 当前处理器是否支持处理该返回值
     * @param method
     * @return
     */
    boolean supportsReturnType(Method method);

    /**
     * 执行处理
     * @param returnValue
     * @param webServletRequest
     * @throws Exception
     */
    void handleReturnValue(Object returnValue, WebServletRequest webServletRequest) throws Exception;
}
