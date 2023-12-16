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
        return null;
    }
}
