package com.hpl.springframework.beans.factory.config;

import com.hpl.springframework.beans.factory.PropertyValues;

/**
 * 注入IOC中Bean的描述信息
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:09
 */
public class BeanDefinition {

    private Class<?> beanClass;

    private PropertyValues propertyValues;


    /**
     * 在 BeanDefinition 新增加了两个属性：initMethodName、destroyMethodName，这两个属性是为了在 spring.xml 配置的 Bean 对象中，可以配置 init-method="initDataMethod" destroy-method="destroyDataMethod" 操作，最终实现接口的效果是一样的。只不过一个是接口方法的直接调用，另外是一个在配置文件中读取到方法反射调用
     */
    private String initMethodName;

    private String destroyMethodName;

    private String scope = ConfigurableBeanFactory.SCOPE_SINGLETON;

    private boolean singleton = true;

    private boolean prototype = false;

    /**
     * 在xml注册Bean定义时，通过scope字段来判断是单例还是原型
     */
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = ConfigurableBeanFactory.SCOPE_SINGLETON.equals(scope);
        this.prototype = ConfigurableBeanFactory.SCOPE_PROTOTYPE.equals(scope);
    }

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public String getScope() {
        return scope;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
