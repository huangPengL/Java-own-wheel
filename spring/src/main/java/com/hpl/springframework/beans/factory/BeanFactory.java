package com.hpl.springframework.beans.factory;

import com.hpl.springframework.beans.ex.BeansException;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:08
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    Object getBean(String beanName, Object... args) throws BeansException;

    /**
     * 指定bean的类型
     * @param name
     * @param requiredType
     * @return
     * @param <T>
     * @throws BeansException
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
}
