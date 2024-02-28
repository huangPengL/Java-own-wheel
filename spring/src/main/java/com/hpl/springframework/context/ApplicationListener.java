package com.hpl.springframework.context;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/28 16:47
 */
public interface ApplicationListener<E> {
    /**
     * Handle an application event.
     * @param event the event to respond to
     */
    void onApplicationEvent(E event);
}
