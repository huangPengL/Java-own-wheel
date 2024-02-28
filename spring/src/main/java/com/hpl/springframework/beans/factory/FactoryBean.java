package com.hpl.springframework.beans.factory;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/28 11:45
 */
public interface FactoryBean<T> {
    T getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();
}
