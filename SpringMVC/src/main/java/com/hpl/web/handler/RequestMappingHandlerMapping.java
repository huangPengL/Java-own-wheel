package com.hpl.web.handler;

import com.hpl.web.annotation.RequestMapping;
import com.hpl.web.annotation.RestController;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 15:49
 */
public class RequestMappingHandlerMapping extends AbstractHandlerMapping{


    /**
     * 根据请求路径，返回HandlerMethod
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    protected HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception {
        return lookUpPath(request);
    }

    @Override
    protected void detectHandlerMethod(String name) throws Exception {
        // 获取当前类
        final ApplicationContext applicationContext = obtainApplicationContext();
        final Class<?> type = applicationContext.getType(name);

        if(type == null){
            return;
        }

        // 获取当前类下的所有方法
        final Method[] declaredMethods = type.getDeclaredMethods();
        if(declaredMethods.length == 0){
            return;
        }

        List<HandlerMethod> handlerMethods = new ArrayList<>();

        // 获得类上的路径
        String rootPath = "";
        if(AnnotatedElementUtils.hasAnnotation(type, RequestMapping.class)){
            final RequestMapping rm = AnnotatedElementUtils.findMergedAnnotation(type, RequestMapping.class);
            rootPath = (rm == null || "".equals(rm.value())) ? "" : rm.value();
        }

        // 方法上是否有RequestMapping注解
        final Object bean = applicationContext.getBean(name);
        for(Method method: declaredMethods){
            if(AnnotatedElementUtils.hasAnnotation(method, RequestMapping.class)){
                // 收集
                final HandlerMethod handlerMethod = new HandlerMethod(bean, method);

                // 获取方法上的路径
                final RequestMapping methodRM = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);

                String methodPath = (methodRM == null || "".equals(methodRM.value()))
                        ? "" : methodRM.value();

                // 设置参数
                if(methodRM != null) {
                    handlerMethod.setRequestMethods(methodRM.methods());
                }
                handlerMethod.setPath(rootPath + methodPath);

                // 加入列表
                handlerMethods.add(handlerMethod);
            }
        }

        // 注册HandlerMethod
        if (!ObjectUtils.isEmpty(handlerMethods)) {
            for (HandlerMethod handlerMethod : handlerMethods) {
                registerMapper(handlerMethod);
            }
        }
    }


    /**
     * 标注了Controller的就是Handler
     * @param type
     * @return
     */
    @Override
    protected boolean isHandler(Class<?> type) {
        return AnnotatedElementUtils.hasAnnotation(type, Controller.class)
         || AnnotatedElementUtils.hasAnnotation(type, RestController.class);
    }
}
