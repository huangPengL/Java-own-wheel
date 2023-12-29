package com.hpl.web.support;

import com.hpl.web.adapter.HandlerMethodAdapter;
import com.hpl.web.adapter.impl.RequestMappingHandlerMethodAdapter;
import com.hpl.web.handler.HandlerMapping;
import com.hpl.web.handler.RequestMappingHandlerMapping;
import com.hpl.web.interceptor.InterceptorRegistry;
import com.hpl.web.interceptor.MappedInterceptor;
import com.hpl.web.resolver.HandlerExceptionResolver;
import com.hpl.web.resolver.her.DefaultHandlerExceptionResolver;
import com.hpl.web.resolver.her.ExceptionHandlerExceptionResolver;
import org.springframework.context.annotation.Bean;

import java.util.List;


/**
 * @Author: huangpenglong
 * @Date: 2023/12/28 21:44
 */
public abstract class WebMvcConfigurationSupport {

    protected abstract void getIntercept(InterceptorRegistry registry);

    @Bean
    public HandlerMapping handlerMapping(){

        final RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.setOrder(0);

        // 拦截器注册中心组件初始化
        final InterceptorRegistry registry = new InterceptorRegistry();
        getIntercept(registry);

        // 获取拦截器 添加到请求映射器中
        final List<MappedInterceptor> interceptors = registry.getInterceptors();
        requestMappingHandlerMapping.addHandlerInterceptors(interceptors);

        return requestMappingHandlerMapping;
    }

    @Bean
    public HandlerMethodAdapter handlerMethodAdapter(){
        final RequestMappingHandlerMethodAdapter requestMappingHandlerMethodAdapter = new RequestMappingHandlerMethodAdapter();
        requestMappingHandlerMethodAdapter.setOrder(0);
        return requestMappingHandlerMethodAdapter;
    }

    @Bean
    public HandlerExceptionResolver defaultHandlerExceptionResolver(){

        final DefaultHandlerExceptionResolver defaultHandlerExceptionResolver = new DefaultHandlerExceptionResolver();
        defaultHandlerExceptionResolver.setOrder(1);
        return defaultHandlerExceptionResolver;
    }

    @Bean
    public HandlerExceptionResolver exceptionHandlerExceptionResolver(){

        final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver = new ExceptionHandlerExceptionResolver();
        exceptionHandlerExceptionResolver.setOrder(0);
        return exceptionHandlerExceptionResolver;
    }
}
