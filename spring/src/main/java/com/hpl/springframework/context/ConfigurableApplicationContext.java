package com.hpl.springframework.context;

import com.hpl.springframework.beans.ex.BeansException;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/15 20:05
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 刷新容器 重新加载配置文件或者重新初始化Spring容器
     * @throws BeansException
     */
    void refresh() throws BeansException;
}
