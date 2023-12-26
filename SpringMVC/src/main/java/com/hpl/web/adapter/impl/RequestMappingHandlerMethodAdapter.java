package com.hpl.web.adapter.impl;

import com.hpl.web.adapter.HandlerMethodAdapter;
import com.hpl.web.annotation.RequestMapping;
import com.hpl.web.convert.*;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.handler.ServletInvocableMethod;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.resolver.HandlerMethodReturnValueHandler;
import com.hpl.web.resolver.hmrvh.HandlerMethodReturnValueHandlerComposite;
import com.hpl.web.resolver.hmar.*;
import com.hpl.web.resolver.hmrvh.RequestResponseBodyMethodReturnValueHandler;
import com.hpl.web.support.WebServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotatedElementUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:09
 */
public class RequestMappingHandlerMethodAdapter implements HandlerMethodAdapter, InitializingBean {

    private int order = 0;
    private HandlerMethodArgumentResolverComposite resolverComposite = new HandlerMethodArgumentResolverComposite();

    private ConvertComposite convertComposite = new ConvertComposite();

    private HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite = new HandlerMethodReturnValueHandlerComposite();

    @Override
    public boolean support(HandlerMethod handlerMethod) {
        return AnnotatedElementUtils.hasAnnotation(handlerMethod.getMethod(), RequestMapping.class);
    }


    /**
     * 参数解析，类型转换，返回值处理、异常处理
     * @param request
     * @param response
     * @param handlerMethod
     * @throws Exception
     */
    @Override
    public void handler(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        final WebServletRequest webServletRequest = new WebServletRequest(request, response);
        final ServletInvocableMethod invocableMethod = new ServletInvocableMethod();

        invocableMethod.setHandlerMethod(handlerMethod);
        invocableMethod.setConvertComposite(this.convertComposite);
        invocableMethod.setMethodArgumentResolverComposite(this.resolverComposite);
        invocableMethod.setReturnValueHandlerComposite(this.returnValueHandlerComposite);

        invocableMethod.invokeAndHandle(webServletRequest,handlerMethod);
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        resolverComposite.addResolvers(getDefaultArgumentResolver());
        convertComposite.addConvertMap(getDefaultConverts());
        returnValueHandlerComposite.addMethodReturnValueHandlers(getDefalutMethodReturnValueHandlers());
    }

    private List<HandlerMethodReturnValueHandler> getDefalutMethodReturnValueHandlers() {
        final List<HandlerMethodReturnValueHandler> methodReturnValueHandlerList = new ArrayList<>();
        methodReturnValueHandlerList.add(new RequestResponseBodyMethodReturnValueHandler());

        return methodReturnValueHandlerList;
    }

    private Map<Class, ConvertHandler> getDefaultConverts() {
        final Map<Class, ConvertHandler> convertMap = new HashMap<>();
        convertMap.put(Integer.class,getConvertHandler(new IntegerConvert(Integer.class)));
        convertMap.put(int.class,getConvertHandler(new IntegerConvert(Integer.class)));
        convertMap.put(String.class,getConvertHandler(new StringConvert(String.class)));
        convertMap.put(Long.class,getConvertHandler(new LongConvert(Long.class)));
        convertMap.put(long.class,getConvertHandler(new LongConvert(Long.class)));
        convertMap.put(Float.class,getConvertHandler(new FloatConvert(Float.class)));
        convertMap.put(float.class,getConvertHandler(new FloatConvert(Float.class)));
        convertMap.put(Boolean.class,getConvertHandler(new BooleanConvert(Boolean.class)));
        convertMap.put(boolean.class,getConvertHandler(new BooleanConvert(Boolean.class)));
        convertMap.put(Byte.class,getConvertHandler(new ByteConvert(Byte.class)));
        convertMap.put(byte.class,getConvertHandler(new ByteConvert(Byte.class)));
        convertMap.put(Short.class,getConvertHandler(new ShortConvert(Short.class)));
        convertMap.put(short.class,getConvertHandler(new ShortConvert(Short.class)));
        convertMap.put(Date.class,getConvertHandler(new DateConvert(Date.class)));
        convertMap.put(Map.class,getConvertHandler(new MapConvert(HashMap.class)));
        convertMap.put(Collection.class,getConvertHandler(new CollectionConvert(Collection.class)));
        convertMap.put(List.class,getConvertHandler(new ListConvert(ArrayList.class)));
        convertMap.put(Set.class,getConvertHandler(new SetConvert(HashSet.class)));
        return convertMap;
    }

    private ConvertHandler getConvertHandler(Convert convert) {
        try {
            final Method method = convert.getClass().getDeclaredMethod("convert", Object.class);
            return new ConvertHandler(convert, method);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取所有默认的参数解析器
     * @return
     */
    private List<HandlerMethodArgumentResolver> getDefaultArgumentResolver(){
        final List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new PathVariableMethodArgumentResolver());
        resolvers.add(new PathVariableMapMethodArgumentResolver());
        resolvers.add(new RequestCookieMethodArgumentResolver());
        resolvers.add(new RequestHeaderMethodArgumentResolver());
        resolvers.add(new RequestHeaderMapMethodArgumentResolver());
        resolvers.add(new RequestPartMethodArgumentResolver());
        resolvers.add(new RequestParamMapMethodArgumentResolver());
        resolvers.add(new RequestParamMethodArgumentResolver());
        resolvers.add(new RequestRequestBodyMethodArgumentResolver());
        resolvers.add(new ServletResponseMethodArgumentResolver());
        resolvers.add(new ServletRequestMethodArgumentResolver());
        return resolvers;
    }
}
