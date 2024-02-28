package com.hpl.springframework.context.event;

import com.hpl.springframework.context.ApplicationEvent;
import com.hpl.springframework.context.ApplicationListener;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/28 16:46
 */
public interface ApplicationEventMulticaster {

    /**
     * Add a listener to be notified of all events.
     * @param listener the listener to add
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * Remove a listener from the notification list.
     * @param listener the listener to remove
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * Multicast the given application event to appropriate listeners.
     * @param event the event to multicast
     */
    void multicastEvent(ApplicationEvent event);
}
