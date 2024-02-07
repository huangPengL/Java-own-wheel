package com.hpl.springframework.beans.factory.config;

/**
 * 注入IOC中Bean的描述信息
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:09
 */
public class BeanDefinition {

    private Class beanClass;

    public BeanDefinition(Class beanClass){
        this.beanClass = beanClass;
    }

    public Object getBean(){
        return null;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
