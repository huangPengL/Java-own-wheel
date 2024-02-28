package com.hpl.springframework.context.event;

import com.hpl.springframework.context.ApplicationContext;
import com.hpl.springframework.context.ApplicationEvent;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/28 16:43
 */
public class ApplicationContextEvent extends ApplicationEvent {
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * Get the <code>ApplicationContext</code> that the event was raised for.
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
