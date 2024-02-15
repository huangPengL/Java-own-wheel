package com.hpl.springframework.context.support;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.hpl.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * Bean工厂和加载资源
 *
 * @Author: huangpenglong
 * @Date: 2024/2/15 20:34
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);
}
