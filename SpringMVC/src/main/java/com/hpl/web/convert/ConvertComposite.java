package com.hpl.web.convert;

import com.hpl.web.exception.NotFoundException;
import com.hpl.web.handler.HandlerMethod;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:58
 */
public class ConvertComposite {

    private Map<Class,ConvertHandler> convertMap = new HashMap<>();


    public void addConvertMap(Map<Class, ConvertHandler> convertMap) {
        this.convertMap.putAll(convertMap);
    }

    public Object convert(HandlerMethod handlerMethod, Class<?> parameterType, Object result) throws Exception{
        // 先执行局部的
        final Map<Class, ConvertHandler> convertHandlerMap = handlerMethod.getConvertHandlerMap();
        if (!ObjectUtils.isEmpty(convertHandlerMap)){
            final ConvertHandler convertHandler = convertHandlerMap.get(parameterType);
            if (convertHandler!=null){
                return convertHandler.convert(result);
            }
        }
        // 全局的
        if (convertMap.containsKey(parameterType)) {
            final ConvertHandler convertHandler = convertMap.get(parameterType);
            try {
                return convertHandler.convert(result);
            } catch (Exception e) {
                // 类型转换异常
                e.printStackTrace();
            }
        }
        // 没找到
        throw new NotFoundException(parameterType.getName() + "没有该参数类型的类型转换器");
    }
}
