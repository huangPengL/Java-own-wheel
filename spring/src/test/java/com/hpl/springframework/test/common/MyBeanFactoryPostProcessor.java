package com.hpl.springframework.test.common;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.hpl.springframework.beans.factory.PropertyValue;
import com.hpl.springframework.beans.factory.PropertyValues;
import com.hpl.springframework.beans.factory.config.BeanDefinition;
import com.hpl.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/15 21:51
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "company 改为：深信服"));
    }
}
