package com.hpl.springframework.beans.factory;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/26 20:30
 */
public interface BeanNameAware extends Aware{

    /**
     * 实现此接口，既能感知到所属的 BeanName
     * @param name
     */
    void setBeanName(String name);
}
