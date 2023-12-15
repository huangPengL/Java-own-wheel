package com.hpl.web.dispatcher;

import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Set;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 14:47
 *
 *  ServletContainerInitializer是Java EE规范中的接口，由具体的Servlet容器来实现，在Web容器启动时被回调。
 *  在实现该接口后，需要在SPI文件中注册，在Servlet容器启动时通过SPI从classpath下查找其实现类，实例化后进行回调。
 */

// HandlesTypes表示当前ServletContainerInitializer的实现类，能处理的类型。
@HandlesTypes(WebApplicationInitializer.class)

public class WebServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> webApplications, ServletContext servletContext) throws ServletException {

        if (!ObjectUtils.isEmpty(webApplications)){
            final ArrayList<WebApplicationInitializer> initializers = new ArrayList<>(webApplications.size());

            for (Class<?> webApplication : webApplications) {
                if (!webApplication.isInterface() && !Modifier.isAbstract(webApplication.getModifiers())
                        && WebApplicationInitializer.class.isAssignableFrom(webApplication)){
                    try {
                        initializers.add((WebApplicationInitializer) ReflectionUtils.accessibleConstructor(webApplication).newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // 调用onStartUp()方法
            if (!ObjectUtils.isEmpty(initializers)){
                for (WebApplicationInitializer initializer : initializers) {
                    initializer.onStartUp(servletContext);
                }
            }
        }
    }
}
