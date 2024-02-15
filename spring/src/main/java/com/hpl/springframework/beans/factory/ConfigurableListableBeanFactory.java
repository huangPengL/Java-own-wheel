package com.hpl.springframework.beans.factory;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.hpl.springframework.beans.factory.config.BeanDefinition;
import com.hpl.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * 提供分析和修改Bean以及预先实例化的操作接口
 * @Author: huangpenglong
 * @Date: 2024/2/15 16:56
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 获取Bean的定义信息
     * @param beanName
     * @return
     * @throws BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;


    void preInstantiateSingletons() throws BeansException;
}
