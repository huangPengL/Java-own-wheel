package com.hpl.springframework.beans.factory.support;

import com.hpl.springframework.core.io.DefaultResourceLoader;
import com.hpl.springframework.core.io.ResourceLoader;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/15 17:23
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader  {
    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
