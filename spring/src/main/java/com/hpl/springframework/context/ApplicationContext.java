package com.hpl.springframework.context;

import com.hpl.springframework.beans.factory.HierarchicalBeanFactory;
import com.hpl.springframework.beans.factory.ListableBeanFactory;
import com.hpl.springframework.core.io.ResourceLoader;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/15 20:04
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher{
}
