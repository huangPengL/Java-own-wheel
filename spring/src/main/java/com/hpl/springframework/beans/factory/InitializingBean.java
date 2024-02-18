package com.hpl.springframework.beans.factory;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/18 18:22
 */
public interface InitializingBean {
    /**
     * Bean 处理了属性填充后初始化
     *
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;
}
