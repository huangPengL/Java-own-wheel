package com.hpl.springframework.beans.factory.support;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/8 17:47
 */
public class SimpleInstantiationStrategy implements  InstantiationStrategy{

    /**
     * 判断 ctor 是否为空，如果为空则是无构造函数实例化，否则就是需要有构造函数的实例化。
     * @param beanDefinition
     * @param beanName
     * @param ctor
     * @param args
     * @return
     * @throws BeansException
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {

        Class beanClass = beanDefinition.getBeanClass();
        try {
            if (ctor == null) {
                return beanClass.getDeclaredConstructor().newInstance();
            }
            return beanClass.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);

        }
        catch (NoSuchMethodException | InstantiationException |
               IllegalAccessException | InvocationTargetException e) {
            throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
        }
    }
}
