package com.hpl.springframework.beans.factory.config;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/15 20:01
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
     *
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
