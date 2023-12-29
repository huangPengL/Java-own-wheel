package com.hpl.web.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/28 21:59
 */
public class InterceptorRegistry {
    private final List<InterceptorRegistration> registrations = new ArrayList<>();

    public InterceptorRegistration addInterceptor(HandlerInterceptor interceptor){
        InterceptorRegistration registration = new InterceptorRegistration(interceptor);
        this.registrations.add(registration);
        return registration;
    }

    /**
     * 转换成路径映射匹配的拦截器
     * @return
     */
    public List<MappedInterceptor> getInterceptors() {
        return this.registrations.stream().map(MappedInterceptor::new).collect(Collectors.toList());
    }

}
