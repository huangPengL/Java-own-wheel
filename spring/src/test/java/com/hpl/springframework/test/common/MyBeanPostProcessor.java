package com.hpl.springframework.test.common;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.config.BeanPostProcessor;
import com.hpl.springframework.test.service.UserService;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/15 21:51
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("location 改为：深圳");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("location 改为：长沙");
        }
        return bean;
    }
}
