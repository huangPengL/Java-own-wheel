package com.hpl.web.convert;

import com.hpl.web.handler.HandlerMethod;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/16 22:00
 */
public class ConvertHandler extends HandlerMethod{
    public ConvertHandler(Object bean, Method method) {
        super(bean,method);
    }

    public Object convert(Object arg) throws Exception {
        if (ObjectUtils.isEmpty(arg)){
            return null;
        }
        return this.getMethod().invoke(this.getBean(),arg);
    }
}
