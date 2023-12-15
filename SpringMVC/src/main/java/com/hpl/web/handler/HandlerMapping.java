package com.hpl.web.handler;

import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 15:37
 */
public interface HandlerMapping extends Ordered {

    HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;
}
