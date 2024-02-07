package com.hpl.springframework.beans.factory.support;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.BeanFactory;
import com.hpl.springframework.beans.factory.config.BeanDefinition;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:23
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    /**
     * 模板方法：
     *  单例容器中有则返回，否则获取bean定义（子类实现）。然后，通过bean定义创建bean实例（子类实现）
     * @param name
     * @return
     */
    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }



    protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;

    protected abstract Object createBean(String name, BeanDefinition beanDefinition) throws BeansException;
}
