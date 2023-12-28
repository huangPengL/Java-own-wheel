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


    @Override
    public void addIntercept(InterceptorRegistry registry) {
        for (WebMvcConfigurer webMvcConfigurer : webMvcConfigurers) {
            webMvcConfigurer.addIntercept(registry);
        }
    }
}
