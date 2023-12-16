package com.hpl.web.adapter.impl;

import com.hpl.web.adapter.HandlerMethodAdapter;
import com.hpl.web.annotation.RequestMapping;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.resolver.hmar.*;
import com.hpl.web.support.WebServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotatedElementUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:09
 */
public class RequestMappingHandlerMethodAdapter implements HandlerMethodAdapter, InitializingBean {

    private int order = 0;
    private HandlerMethodArgumentResolverComposite resolverComposite = new HandlerMethodArgumentResolverComposite();

    @Override
    public boolean support(HandlerMethod handlerMethod) {
        return AnnotatedElementUtils.hasAnnotation(handlerMethod.getMethod(), RequestMapping.class);
    }


    /**
     * 参数解析，类型转换，返回值处理、异常处理
     * @param request
     * @param response
     * @param handlerMethod
     * @throws Exception
     */
    @Override
    public void handler(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        final WebServletRequest webServletRequest = new WebServletRequest(request, response);

        // 参数解析

    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final List<HandlerMethodArgumentResolver> defaultArgumentResolver = getDefaultArgumentResolver();
        resolverComposite.addResolvers(defaultArgumentResolver);
    }

    /**
     * 获取所有默认的参数解析器
     * @return
     */
    private List<HandlerMethodArgumentResolver> getDefaultArgumentResolver(){
        final List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new PathVariableMethodArgumentResolver());
        resolvers.add(new PathVariableMapMethodArgumentResolver());
        resolvers.add(new RequestCookieMethodArgumentResolver());
        resolvers.add(new RequestHeaderMethodArgumentResolver());
        resolvers.add(new RequestHeaderMapMethodArgumentResolver());
        resolvers.add(new RequestPartMethodArgumentResolver());
        resolvers.add(new RequestParamMapMethodArgumentResolver());
        resolvers.add(new RequestParamMethodArgumentResolver());
        resolvers.add(new RequestRequestBodyMethodArgumentResolver());
        resolvers.add(new ServletResponseMethodArgumentResolver());
        resolvers.add(new ServletRequestMethodArgumentResolver());
        return resolvers;
    }
}
