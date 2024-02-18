package com.hpl.springframework.beans.factory;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/18 18:22
 */
public interface DisposableBean{

    /**
     *  销毁方法
     * @throws Exception
     */
    void destroy() throws Exception;
}
