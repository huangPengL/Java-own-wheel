package com.hpl.springframework.beans.ex;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:29
 */
public class BeansException extends RuntimeException{

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
