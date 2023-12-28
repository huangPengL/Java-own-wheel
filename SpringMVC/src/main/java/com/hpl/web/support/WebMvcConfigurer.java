package com.hpl.web.support;

import com.hpl.web.interceptor.InterceptorRegistry;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/28 21:43
 */
public interface WebMvcConfigurer {
    default void addIntercept(InterceptorRegistry registry){}
}
