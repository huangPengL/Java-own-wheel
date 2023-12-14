package com.hpl.web.support;

import com.hpl.web.context.AnnotationConfigWebApplicationContext;
import com.hpl.web.context.WebApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ObjectUtils;

import javax.servlet.Filter;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 11:39
 */
public abstract class AbstractAnnotationConfigDispatcherServletInitializer extends AbstractDispatcherServletInitializer{

    /**
     * 创建root ioc
     * @return
     */
    @Override
    protected AnnotationConfigApplicationContext createRootApplicationContext() {
        // 用户实现getRootConfigClasses
        final Class<?>[] rootConfigClasses = getRootConfigClasses();
        if (!ObjectUtils.isEmpty(rootConfigClasses)){
            final AnnotationConfigApplicationContext rootContext = new AnnotationConfigApplicationContext();
            rootContext.register(rootConfigClasses);
            return rootContext;
        }
        return null;
    }


    /**
     * 创建web ioc
     * @return
     */
    @Override
    protected WebApplicationContext createWebApplicationContext() {
        // 用户实现getWebConfigClasses
        final Class<?>[] webConfigClasses = getWebConfigClasses();
        if (!ObjectUtils.isEmpty(webConfigClasses)){
            final AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
            webContext.register(webConfigClasses);
            return webContext;
        }
        return null;
    }

    @Override
    protected Filter[] getFilters() {
        return new Filter[0];
    }
}
