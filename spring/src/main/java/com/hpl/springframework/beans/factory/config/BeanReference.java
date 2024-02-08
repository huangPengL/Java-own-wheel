package com.hpl.springframework.beans.factory.config;

/**
 * Bean的引用
 * @Author: huangpenglong
 * @Date: 2024/2/8 20:22
 */
public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

}

