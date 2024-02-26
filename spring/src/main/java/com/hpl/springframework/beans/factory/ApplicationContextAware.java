package com.hpl.springframework.beans.factory;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.context.ApplicationContext;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/26 20:31
 */
public interface ApplicationContextAware extends Aware{

    /**
     * 实现此接口，既能感知到所属的 ApplicationContext
     * @param applicationContext
     * @throws BeansException
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
