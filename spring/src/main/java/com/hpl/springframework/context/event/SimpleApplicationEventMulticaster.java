package com.hpl.springframework.context.event;

import com.hpl.springframework.beans.factory.BeanFactory;
import com.hpl.springframework.context.ApplicationEvent;
import com.hpl.springframework.context.ApplicationListener;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/28 20:42
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
