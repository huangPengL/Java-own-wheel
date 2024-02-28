package com.hpl.springframework.context.event;

import com.hpl.springframework.context.ApplicationEvent;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/28 16:45
 */
public class ContextClosedEvent extends ApplicationContextEvent {

    public ContextClosedEvent(Object source) {
        super(source);
    }
}
