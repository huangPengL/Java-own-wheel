package com.hpl.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.PropertyValue;
import com.hpl.springframework.beans.factory.PropertyValues;
import com.hpl.springframework.beans.factory.config.BeanDefinition;
import com.hpl.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 * 实现 Bean 的实例化操作，放入DefaultSingletonBeanRegistry这个容器中
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:30
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws BeansException {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);

            // 给 Bean 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 给创建的bean填充属性（普通类型 和 对象）
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) throws BeansException {

        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                if (value instanceof BeanReference) {
                    // A 依赖 B，获取 B 的实例化
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                // 属性填充
                BeanUtil.setFieldValue(bean, name, value);
            }
        }
        catch (Exception e) {
            throw new BeansException("Error setting property values：" + beanName);
        }
    }

    /**
     * 通过构造函数创建Bean
     * @param beanDefinition
     * @param name
     * @param args
     * @return
     * @throws BeansException
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String name, Object[] args) throws BeansException {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();

        for(Constructor<?> ctor: declaredConstructors){
            if(ctor != null && args != null && ctor.getParameterTypes().length == args.length){
                return getInstantiationStrategy().instantiate(beanDefinition, name, ctor, args);
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, name, null, args);
    }

    private InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
