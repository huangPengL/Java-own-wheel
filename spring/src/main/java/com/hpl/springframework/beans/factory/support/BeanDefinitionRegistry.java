package com.hpl.springframework.beans.factory.support;

import com.hpl.springframework.beans.factory.config.BeanDefinition;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:40
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注册 BeanDefinition
     *
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
