package com.hpl.springframework.beans.factory.config;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:20
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    /**
     * 销毁单例对象
     */
    void destroySingletons();
}
