package com.hpl.springframework.beans.factory.config;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.BeanFactory;

/**
 * 自动化处理Bean工厂配置的接口
 * @Author: huangpenglong
 * @Date: 2024/2/15 17:00
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessBeforeInitialization 方法
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
