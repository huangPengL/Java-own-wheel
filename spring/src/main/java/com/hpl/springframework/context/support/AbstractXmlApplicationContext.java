package com.hpl.springframework.context.support;

import com.hpl.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.hpl.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/15 20:49
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    /**
     * 使用 XmlBeanDefinitionReader 类，处理了关于 XML 文件配置信息的操作。
     * 同时这里又留下了一个抽象类方法，getConfigLocations()，此方法是为了从入口上下文类，拿到配置信息的地址描述。
     * @param beanFactory
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory){
        XmlBeanDefinitionReader beanDefinitionReader =
                new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations){
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
