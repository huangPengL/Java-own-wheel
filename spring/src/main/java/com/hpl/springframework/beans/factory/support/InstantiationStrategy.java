package com.hpl.springframework.beans.factory.support;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 *  实例化对象接口（策略模式）
 * @Author: huangpenglong
 * @Date: 2024/2/8 17:46
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;
}
