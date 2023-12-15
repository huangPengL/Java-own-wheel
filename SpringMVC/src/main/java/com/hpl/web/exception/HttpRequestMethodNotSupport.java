package com.hpl.web.exception;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/16 0:16
 */
public class HttpRequestMethodNotSupport extends Exception{
    public HttpRequestMethodNotSupport(String message) {
        super(message);
    }
}