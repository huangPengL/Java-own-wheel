package com.hpl.springframework.beans.factory;

import com.hpl.springframework.beans.ex.BeansException;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/26 20:28
 */
public interface BeanFactoryAware extends Aware{

    /**
     * 实现此接口，既能感知到所属的 BeanFactory
     * @param beanFactory
     * @throws BeansException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
