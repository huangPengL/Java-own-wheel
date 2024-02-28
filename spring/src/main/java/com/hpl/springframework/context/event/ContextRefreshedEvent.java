package com.hpl.springframework.context.event;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/28 16:46
 */
public class ContextRefreshedEvent extends ApplicationContextEvent{

    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
