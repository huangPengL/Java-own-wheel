package com.hpl.springframework.beans.factory;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/26 20:29
 */
public interface BeanClassLoaderAware extends Aware{

    /**
     * 实现此接口，既能感知到所属的 ClassLoader
     * @param classLoader
     */
    void setBeanClassLoader(ClassLoader classLoader);
}
