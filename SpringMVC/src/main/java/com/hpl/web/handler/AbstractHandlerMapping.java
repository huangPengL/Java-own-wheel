package com.hpl.web.handler;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 15:48
 */
public abstract class AbstractHandlerMapping implements HandlerMapping{

    protected int order;

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {

        final HandlerMethod handlerInternal = getHandlerInternal(request);

        final HandlerExecutionChain chain = new HandlerExecutionChain(handlerInternal);

        // 拦截器 TODO

        return null;
    }

    protected abstract HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception;

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
