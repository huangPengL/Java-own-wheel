package com.hpl.springframework.beans.factory.support;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *  FactoryBeanRegistrySupport 类主要处理的就是关于 FactoryBean 此类对象的注册操作
 * @Author: huangpenglong
 * @Date: 2024/2/28 11:45
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{

    /**
     * Cache of singleton objects created by FactoryBeans: FactoryBean name --> object
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();


    /**
     * 从缓存中获取单例FactoryBean
     * @param beanName
     * @return
     */
    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJECT ? object : null);
    }

    /**
     * 根据是否单例来决定对象获取的逻辑
     * @param factory
     * @param beanName
     * @return
     */
    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        if(!factory.isSingleton()){
            return doGetObjectFromFactoryBean(factory,beanName);
        }

        Object objectForFactoryBean = getCachedObjectForFactoryBean(beanName);
        if(objectForFactoryBean == null){
            objectForFactoryBean = doGetObjectFromFactoryBean(factory, beanName);
            this.factoryBeanObjectCache.put(beanName, (objectForFactoryBean != null ? objectForFactoryBean : NULL_OBJECT));
        }

        return objectForFactoryBean != NULL_OBJECT ? objectForFactoryBean : null;
    }

    /**
     * 直接通过FactoryBean获取对象
     * @param factory
     * @param beanName
     * @return
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, final String beanName){

        try{
            return factory.getObject();
        }
        catch (Exception e){
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}
