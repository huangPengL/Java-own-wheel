package com.hpl.springframework.context;

import java.util.EventObject;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/28 16:42
 */
public abstract class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object source) {
        super(source);
    }

}
