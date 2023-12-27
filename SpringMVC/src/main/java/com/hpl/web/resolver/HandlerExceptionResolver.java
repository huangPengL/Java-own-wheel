package com.hpl.web.resolver;

import com.hpl.web.handler.HandlerMethod;
import org.springframework.core.Ordered;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/26 17:25
 */
public interface HandlerExceptionResolver extends Ordered{

    /**
     * 解析异常
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     * @throws Exception
     */
    Boolean resolveException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) throws Exception;

    @Override
    int getOrder();
}
