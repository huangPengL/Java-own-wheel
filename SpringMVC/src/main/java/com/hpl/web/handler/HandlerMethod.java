package com.hpl.web.handler;

import com.hpl.web.annotation.RequestMethod;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 15:45
 */
public class HandlerMethod {

    protected Object bean;

    protected Class type;

    protected Method method;

    protected MethodParameter[] parameters = new MethodParameter[0];

    protected String path;

    protected RequestMethod[] requestMethods = new RequestMethod[0];


    public HandlerMethod(Object bean, Method method) {
        this.bean = bean;
        this.type = bean.getClass();
        this.method = method;
        final Parameter[] parameters = method.getParameters();
        MethodParameter[] methodParameters = new MethodParameter[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            methodParameters[i] = new MethodParameter(method, i);
        }
        this.parameters = methodParameters;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public MethodParameter[] getParameters() {
        return parameters;
    }

    public void setParameters(MethodParameter[] parameters) {
        this.parameters = parameters;
    }

    public RequestMethod[] getRequestMethods() {
        return requestMethods;
    }

    public void setRequestMethods(RequestMethod[] requestMethods) {
        this.requestMethods = requestMethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerMethod that = (HandlerMethod) o;
        return Objects.equals(path, that.path) && Arrays.equals(requestMethods, that.requestMethods);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(path);
        result = 31 * result + Arrays.hashCode(requestMethods);
        return result;
    }

    @Override
    public String toString() {
        return "HandlerMethod{" +
                "bean=" + bean +
                ", type=" + type +
                ", method=" + method +
                ", parameters=" + Arrays.toString(parameters) +
                ", path='" + path + '\'' +
                ", requestMethods=" + Arrays.toString(requestMethods) +
                '}';
    }
}