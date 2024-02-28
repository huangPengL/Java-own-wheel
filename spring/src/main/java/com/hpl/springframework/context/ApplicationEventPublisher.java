package com.hpl.springframework.context;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/28 17:24
 */
public interface ApplicationEventPublisher {
    /**
     * 事件发布
     * @param event
     */
    void publishEvent(ApplicationEvent event);
}
