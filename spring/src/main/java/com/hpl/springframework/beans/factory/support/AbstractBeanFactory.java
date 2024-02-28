package com.hpl.springframework.beans.factory.support;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.FactoryBean;
import com.hpl.springframework.beans.factory.config.BeanDefinition;
import com.hpl.springframework.beans.factory.config.BeanPostProcessor;
import com.hpl.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.hpl.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:23
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private final ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * 模板方法：
     *  单例容器中有则返回，否则获取bean定义（子类实现）。然后，通过bean定义创建bean实例（子类实现）
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName, (Object) null);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return doGetBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    protected <T> T doGetBean(String beanName, final Object... args) throws BeansException {
        Object bean = getSingleton(beanName);
        if (bean != null) {
            return (T) getObjectForBeanInstance(bean, beanName);
        }

        // 若bean的scope不为singleton则bean始终为null，需要创建bean
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        bean = createBean(beanName, beanDefinition, args);

        return (T) getObjectForBeanInstance(bean, beanName);
    }

    /**
     * 如果bean是FactoryBean类型，那么根据FactoryBean是否为单例模式从getObject中获取实例对象
     * 即 调用getObjectFromFactoryBean
     * @param bean
     * @param beanName
     * @return
     */
    private Object getObjectForBeanInstance(Object bean, String beanName) {
        if(!(bean instanceof FactoryBean)){
            return bean;
        }

        FactoryBean<?> factoryBean = (FactoryBean<?>) bean;
        return getObjectFromFactoryBean(factoryBean, beanName);
    }

    /**
     * 获取Bean的描述信息
     * @param beanName
     * @return
     * @throws BeansException
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 创建Bean
     * @param beanName
     * @param beanDefinition
     * @param args
     * @return
     * @throws BeansException
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws BeansException;


    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
}
