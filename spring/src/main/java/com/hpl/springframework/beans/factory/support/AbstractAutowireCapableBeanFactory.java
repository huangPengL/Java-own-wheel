package com.hpl.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.DisposableBean;
import com.hpl.springframework.beans.factory.InitializingBean;
import com.hpl.springframework.beans.factory.PropertyValue;
import com.hpl.springframework.beans.factory.PropertyValues;
import com.hpl.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.hpl.springframework.beans.factory.config.BeanDefinition;
import com.hpl.springframework.beans.factory.config.BeanPostProcessor;
import com.hpl.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 实现 Bean 的实例化操作，放入DefaultSingletonBeanRegistry这个容器中
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:30
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws BeansException {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);

            // 给 Bean 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);

            // 执行Bean的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);

        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 注册实现了 DisposableBean 接口的 Bean 对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

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

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current){
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current){
                return result;
            }
            result = current;
        }
        return result;
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        // 2. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Exception {
        // 1. 实现 InitializingBean 接口
        if (wrappedBean instanceof InitializingBean) {
            ((InitializingBean) wrappedBean).afterPropertiesSet();
        }

        // 2. 配置了 init-method
        String initMethodName = beanDefinition.getInitMethodName();
        if(StrUtil.isNotEmpty(initMethodName)){
            Method declaredMethod = beanDefinition.getBeanClass().getDeclaredMethod(initMethodName);
            declaredMethod.invoke(wrappedBean);
        }
    }

    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 如果该bean实现了DisposableBean接口 或 该bean配置了destroy-method，则注册
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }
}
