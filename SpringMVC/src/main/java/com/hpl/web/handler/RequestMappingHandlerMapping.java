package com.hpl.web.handler;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 15:49
 */
public class RequestMappingHandlerMapping extends AbstractHandlerMapping{


    /**
     * 根据请求路径，返回HandlerMethod
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    protected HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception {
        return null;
    }
}
