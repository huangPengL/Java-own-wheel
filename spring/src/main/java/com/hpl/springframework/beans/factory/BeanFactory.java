package com.hpl.springframework.beans.factory;

import com.hpl.springframework.beans.ex.BeansException;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:08
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    Object getBean(String beanName, Object... args) throws BeansException;
}
