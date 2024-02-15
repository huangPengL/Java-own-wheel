package com.hpl.springframework.beans.factory.support;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.core.io.Resource;
import com.hpl.springframework.core.io.ResourceLoader;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/15 17:22
 */
public interface BeanDefinitionReader {

    /**
     * 获取BeanDefinition注册器
     * @return
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取资源加载器
     * @return
     */
    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;
}
