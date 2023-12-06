package com.hpl.web.context;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/6 11:34
 */
public abstract class AbstractRefreshableWebApplicationContext extends AbstractRefreshableConfigApplicationContext implements ConfigurableWebApplicationContext {

    protected  ServletContext servletContext;
    protected  ServletConfig servletConfig;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }


    /**
     * 添加后置处理器。允许在标准初始化之后，但在任何 bean 实例化之前，对 bean 的定义（即配置元数据）进行读取和修改。
     *
     * 假设您正在开发一个应用程序，其中某些 bean 的属性需要根据不同的部署环境进行调整。您可以实现一个 BeanFactoryPostProcessor，
     * 在其 postProcessBeanFactory 方法中编写逻辑来检查和修改这些 bean 的定义，确保它们符合当前的部署环境。
     * @param beanFactory
     */
    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new ServletBeanPostProcess(this.servletContext,this.servletConfig));

        // 忽略
        beanFactory.ignoreDependencyInterface(ServletContextAware.class);
        beanFactory.ignoreDependencyInterface(ServletConfigAware.class);
    }
}
