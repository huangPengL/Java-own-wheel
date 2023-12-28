package com.hpl.web.support;

import com.hpl.web.interceptor.InterceptorRegistry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 * @Author: huangpenglong
 * @Date: 2023/12/28 21:47
 */
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
    private final WebMvcComposite webMvcComposite = new WebMvcComposite();

    @Autowired(required = false)
    public void setWebMvcComposite(List<WebMvcConfigurer> webMvcConfigurers){
        webMvcComposite.addWebMvcConfigurers(webMvcConfigurers);

    }

    @Override
    protected void getIntercept(InterceptorRegistry registry) {
        webMvcComposite.addIntercept(registry);
    }
}
