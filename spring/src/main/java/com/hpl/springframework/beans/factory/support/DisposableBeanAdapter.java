package com.hpl.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.DisposableBean;
import com.hpl.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/18 18:43
 */
public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        // 1. 实现接口 DisposableBean
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        // 2. 配置信息 destroy-method
        if (StrUtil.isNotEmpty(destroyMethodName)) {
            if(!(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
                Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
                destroyMethod.invoke(bean);
            }
        }

    }
}
