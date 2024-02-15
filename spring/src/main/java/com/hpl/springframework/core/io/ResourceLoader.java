package com.hpl.springframework.core.io;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/15 17:16
 */
public interface ResourceLoader {

    /**
     * Pseudo URL prefix for loading from the class path: "classpath:"
     */
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 策略模式获取资源解析器
     * @param location
     * @return
     */
    Resource getResource(String location);
}
