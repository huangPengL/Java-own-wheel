package com.hpl.web.resolver.her;

import com.hpl.web.annotation.ControllerAdvice;
import com.hpl.web.annotation.ExceptionHandler;
import com.hpl.web.handler.ExceptionHandlerMethod;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.handler.ServletInvocableMethod;
import com.hpl.web.resolver.HandlerExceptionResolver;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.resolver.HandlerMethodReturnValueHandler;
import com.hpl.web.resolver.hmar.HandlerMethodArgumentResolverComposite;
import com.hpl.web.resolver.hmar.ServletRequestMethodArgumentResolver;
import com.hpl.web.resolver.hmar.ServletResponseMethodArgumentResolver;
import com.hpl.web.resolver.hmrvh.HandlerMethodReturnValueHandlerComposite;
import com.hpl.web.resolver.hmrvh.RequestResponseBodyMethodReturnValueHandler;
import com.hpl.web.support.WebServletRequest;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  自定义异常处理器
 * @Author: huangpenglong
 * @Date: 2023/12/27 16:15
 */
public class ExceptionHandlerExceptionResolver extends ApplicationObjectSupport implements HandlerExceptionResolver, InitializingBean {

    private int order;

    private Map<Class, ExceptionHandlerMethod> exceptionHandlerMethodMap = new HashMap<>();

    private HandlerMethodArgumentResolverComposite methodArgumentResolverComposite = new HandlerMethodArgumentResolverComposite();

    private HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite = new HandlerMethodReturnValueHandlerComposite();

    @Override
    public Boolean resolveException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) throws Exception {

        // 找到可以处理该异常的处理器方法
        final ExceptionHandlerMethod exceptionHandlerMethod = getExceptionHandlerMethod(handler, ex);

        // 封装成ServletInvocableMethod，使其作为一个HandlerMethod来处理该方法
        if(!ObjectUtils.isEmpty(exceptionHandlerMethod)){
            final WebServletRequest webServletRequest = new WebServletRequest(request, response);
            final ServletInvocableMethod servletInvocableMethod = new ServletInvocableMethod();

            servletInvocableMethod.setExceptionHandlerMethodMap(exceptionHandlerMethodMap);
            servletInvocableMethod.setReturnValueHandlerComposite(returnValueHandlerComposite);
            servletInvocableMethod.setMethodArgumentResolverComposite(methodArgumentResolverComposite);
            servletInvocableMethod.setHandlerMethod(exceptionHandlerMethod);
            Object[] args = {ex,exceptionHandlerMethod};
            servletInvocableMethod.invokeAndHandle(webServletRequest,exceptionHandlerMethod,args);
            return true;
        }

        return false;
    }

    private ExceptionHandlerMethod getExceptionHandlerMethod(HandlerMethod handler, Exception ex) {
        Class aClass = ex.getClass();
        ExceptionHandlerMethod exceptionHandlerMethod = null;

        // 先找局部异常处理器方法
        Map<Class, ExceptionHandlerMethod> localExceptionHandlerMap = handler.getExceptionHandlerMethodMap();
        if(handler != null && !CollectionUtils.isEmpty(localExceptionHandlerMap)){
            while(exceptionHandlerMethod == null){
                exceptionHandlerMethod = localExceptionHandlerMap.get(aClass);
                aClass = aClass.getSuperclass();

                // 找到顶了还没找到，那就break
                if(aClass == Throwable.class && exceptionHandlerMethod == null){
                    break;
                }
            }
        }

        aClass = ex.getClass();
        // 再找全局异常处理器方法
        while(exceptionHandlerMethod == null){
            exceptionHandlerMethod = this.exceptionHandlerMethodMap.get(aClass);
            aClass = aClass.getSuperclass();

            // 找到顶了还没找到，那就break
            if(aClass == Throwable.class && exceptionHandlerMethod == null){
                break;
            }
        }

        return exceptionHandlerMethod;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return 0;
    }


    /**
     * 初始化基础组件
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.exceptionHandlerMethodMap.putAll(initExceptionHandlerMethod());
        this.methodArgumentResolverComposite.addResolvers(getDefaultArgumentResolver());
        this.returnValueHandlerComposite.addMethodReturnValueHandlers(getDefaultMethodReturnValueHandler());
    }


    /**
     * 初始化[全局]异常处理器方法（从容器中带有@ControllerAdvice注解上的类中作为处理器）
     * @return
     */
    private Map<Class, ExceptionHandlerMethod> initExceptionHandlerMethod() {
        // 从容器当中拿带有@ControllerAdvice 类名
        Map<Class, ExceptionHandlerMethod> result = new HashMap<>();
        ApplicationContext applicationContext = getApplicationContext();

        String[] names = BeanFactoryUtils.beanNamesForAnnotationIncludingAncestors(
                applicationContext, ControllerAdvice.class);

        // 遍历候选类所有的方法，将带有@ExceptionHandler的方法作为异常处理方法放入Map中
        for (String name : names) {
            Class<?> type = applicationContext.getType(name);
            Method[] declaredMethods = type.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if(AnnotatedElementUtils.hasAnnotation(method, ExceptionHandler.class)){
                    final ExceptionHandler exceptionHandler = AnnotatedElementUtils.findMergedAnnotation(method, ExceptionHandler.class);

                    result.put(exceptionHandler.value(),
                            new ExceptionHandlerMethod(applicationContext.getBean(name), method));
                }
            }
        }
        return result;
    }

    private List<HandlerMethodArgumentResolver> getDefaultArgumentResolver() {
        final ArrayList<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers = new ArrayList<>();
        handlerMethodArgumentResolvers.add(new ServletRequestMethodArgumentResolver());
        handlerMethodArgumentResolvers.add(new ServletResponseMethodArgumentResolver());

        return handlerMethodArgumentResolvers;
    }



    private List<HandlerMethodReturnValueHandler> getDefaultMethodReturnValueHandler() {
        final ArrayList<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers = new ArrayList<>();
        handlerMethodReturnValueHandlers.add(new RequestResponseBodyMethodReturnValueHandler());

        return handlerMethodReturnValueHandlers;
    }


}
