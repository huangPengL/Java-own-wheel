package com.hpl.web.resolver.hmar;

import com.hpl.web.annotation.RequestBody;
import com.hpl.web.annotation.RequestParam;
import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.support.WebServletRequest;
import com.hpl.web.utils.MultipartUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.Map;


/**
 * @description: 获取普通数据
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:40
 */
public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        final Class<?> parameterType = parameter.getParameterType();
        if (parameterType == HttpServletResponse.class || parameterType == HttpServletRequest.class){
            return false;
        }
        if (MultipartUtil.isMultipartFile(parameter)){
            return false;
        }
        if (parameterType == Map.class){
            return false;
        }
        if (parameter.hasParameterAnnotation(RequestBody.class)){
            return false;
        }
        return true;
    }


    /**
     * 分成两种情况解决，一是基础数据类型，二是对象
     * @param parameter
     * @param handlerMethod
     * @param webServletRequest
     * @param convertComposite
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConvertComposite convertComposite) throws Exception {
        Class<?> parameterType = parameter.getParameterType();
        HttpServletRequest request = webServletRequest.getRequest();

        // 基础数据类型
        if (BeanUtils.isSimpleProperty(parameterType)) {
            if(parameter.hasParameterAnnotation(RequestParam.class)){
                final RequestParam parameterAnnotation = parameter.getParameterAnnotation(RequestParam.class);
                String name = "".equals(parameterAnnotation.value())
                        ? parameter.getParameterName()
                        : parameterAnnotation.value();

                return convertComposite.convert(handlerMethod,parameterType,request.getParameter(name));
            }
        }
        // 对象
        else{
            // 如果当前标注了RequestParam则报错
            if (parameter.hasParameterAnnotation(RequestParam.class)) {
                throw new IllegalArgumentException(handlerMethod.getBean().getClass().getName() +" "+ handlerMethod.getMethod().getName() + "@RequestParam 不支持标注在对象上");
            }

            // 获取所有请求参数
            final Map<String, String[]> parameterMap = request.getParameterMap();

            // 创建对象
            Object o = ReflectionUtils.accessibleConstructor(parameterType).newInstance();

            // 遍历对象的成员，并赋值
            final Field[] fields = parameterType.getDeclaredFields();
            initObjFields(parameterMap, o, fields, handlerMethod, convertComposite);

            return o;
        }


        return null;
    }

    private void initObjFields(Map<String, String[]> parameterMap, Object o, Field[] fields, HandlerMethod handlerMethod, ConvertComposite convertComposite) throws Exception {

        for(Field field: fields){
            Class<?> type = field.getType();

            // 简单类型
            if(BeanUtils.isSimpleProperty(type)){
                if(parameterMap.containsKey(field.getName())){
                    field.setAccessible(true);
                    field.set(o,convertComposite.convert(handlerMethod, type,parameterMap.get(field.getName())[0]));
                    field.setAccessible(false);
                }
            }
            // 对象
            else{
                final Object subObj = ReflectionUtils.accessibleConstructor(type).newInstance();
                // 属性填充
                final Field[] subFields = type.getDeclaredFields();
                initObjFields(parameterMap,subObj,subFields,handlerMethod,convertComposite);
                field.setAccessible(true);
                field.set(o,subObj);
                field.setAccessible(false);
            }
        }
    }
}
