package com.hpl.web.support;

import com.hpl.web.interceptor.InterceptorRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/28 21:46
 */
public class WebMvcComposite implements WebMvcConfigurer{
    private List<WebMvcConfigurer> webMvcConfigurers = new ArrayList<>();

    public void addWebMvcConfigurers(List<WebMvcConfigurer> webMvcConfigurers) {
        this.webMvcConfigurers.addAll(webMvcConfigurers);
    }


    /**
     * 将所有收集到的WebMvcConfigurer，主动调用addIntercept方法，实现拦截器和映射规则的注册
     * @param registry
     */
    @Override
    public void addIntercept(InterceptorRegistry registry) {
        for (WebMvcConfigurer webMvcConfigurer : webMvcConfigurers) {
            webMvcConfigurer.addIntercept(registry);
        }
    }
}
