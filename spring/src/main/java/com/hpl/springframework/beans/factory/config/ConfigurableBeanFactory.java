package com.hpl.springframework.beans.factory.config;

import com.hpl.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/15 16:57
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry{

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
