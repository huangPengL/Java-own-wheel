package com.hpl.web.handler;

import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.exception.NotFoundException;
import com.hpl.web.resolver.HandlerMethodReturnValueHandlerComposite;
import com.hpl.web.resolver.hmar.HandlerMethodArgumentResolverComposite;
import com.hpl.web.support.WebServletRequest;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/25 17:02
 */
public class ServletInvocableMethod extends HandlerMethod{

    private HandlerMethod handlerMethod;

    private HandlerMethodArgumentResolverComposite methodArgumentResolverComposite;

    private ConvertComposite convertComposite;

    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite;

    public ServletInvocableMethod() {
        super();
    }

    public HandlerMethod getHandlerMethod() {
        return handlerMethod;
    }

    public void setHandlerMethod(HandlerMethod handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    public HandlerMethodArgumentResolverComposite getMethodArgumentResolverComposite() {
        return methodArgumentResolverComposite;
    }

    public void setMethodArgumentResolverComposite(HandlerMethodArgumentResolverComposite methodArgumentResolverComposite) {
        this.methodArgumentResolverComposite = methodArgumentResolverComposite;
    }

    public ConvertComposite getConvertComposite() {
        return convertComposite;
    }

    public void setConvertComposite(ConvertComposite convertComposite) {
        this.convertComposite = convertComposite;
    }

    public ParameterNameDiscoverer getParameterNameDiscoverer() {
        return parameterNameDiscoverer;
    }

    public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }

    public HandlerMethodReturnValueHandlerComposite getReturnValueHandlerComposite() {
        return returnValueHandlerComposite;
    }

    public void setReturnValueHandlerComposite(HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite) {
        this.returnValueHandlerComposite = returnValueHandlerComposite;
    }

    public void invokeAndHandle(WebServletRequest webServletRequest, HandlerMethod handlerMethod, Object... providerArgs) throws Exception {

        // 获取参数
        final Object[] methodArguments = getMethodArguments(webServletRequest, handlerMethod, providerArgs);

        // 反射执行方法
        final Object result = doInvoke(methodArguments);

        // 选择返回值处理器，处理返回值
        System.out.println(result);
    }

    private Object[] getMethodArguments(WebServletRequest webServletRequest, HandlerMethod handlerMethod, Object[] providerArgs) throws Exception {

        final MethodParameter[] parameters = handlerMethod.getParameters();
        Object args[] = new Object[parameters.length];

        for(int i=0;i<parameters.length;i++){
            MethodParameter parameter = parameters[i];

            parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);

            if(!this.methodArgumentResolverComposite.supportsParameter(parameter)){
                throw new NotFoundException("没有参数解析器解析参数:" +parameter.getParameterType());
            }

            args[i] = this.methodArgumentResolverComposite.resolveArgument(
                    parameter, handlerMethod, webServletRequest, this.convertComposite);
        }

        return args;
    }

    private Object doInvoke(Object[] methodArguments) throws Exception {
        return this.handlerMethod.getMethod().invoke(this.handlerMethod.getBean(), methodArguments);
    }
}
