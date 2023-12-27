package com.hpl.web.resolver.her;

import com.hpl.web.exception.ConvertCastException;
import com.hpl.web.exception.HttpRequestMethodNotSupport;
import com.hpl.web.exception.NotFoundException;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.resolver.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *  全局异常处理器
 * @Author: huangpenglong
 * @Date: 2023/12/26 17:26
 */
public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {

    private int order;

    @Override
    public Boolean resolveException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) throws ServletException, IOException {

        final Class<? extends Exception> type = ex.getClass();
        if (type == ConvertCastException.class || type == NotFoundException.class) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            return true;
        }else if (type == HttpRequestMethodNotSupport.class){
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,ex.getMessage());
            return true;
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
