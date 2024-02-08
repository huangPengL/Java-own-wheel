package com.hpl.springframework.beans.factory.support;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实现 Bean 的实例化操作，放入DefaultSingletonBeanRegistry这个容器中
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:30
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition, Object... args) throws BeansException {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, name, args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        addSingleton(name, bean);
        return bean;
    }

    private Object createBeanInstance(BeanDefinition beanDefinition, String name, Object[] args) throws BeansException {
        Constructor construct = null;
        Class beanClass = beanDefinition.getBeanClass();
        Constructor[] declaredConstructors = beanClass.getDeclaredConstructors();

        for(Constructor ctor: declaredConstructors){
            if(ctor != null && ctor.getParameterTypes().length ==args.length){
                return getInstantiationStrategy().instantiate(beanDefinition, name, ctor, args);
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, name, construct, args);
    }

    private InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
