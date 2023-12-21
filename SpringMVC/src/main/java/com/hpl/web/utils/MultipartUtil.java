package com.hpl.web.utils;

import com.hpl.web.multipart.MultipartFile;
import org.springframework.core.MethodParameter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/21 16:02
 */
public class MultipartUtil {
    /**
     * 判断方法上的参赛是否为MultipartFile
     * @param parameter
     * @return
     */
    public static boolean isMultipartFile(MethodParameter parameter){
        Class<?> parameterType = parameter.getParameterType();

        if(parameterType.isArray() && parameterType == MultipartFile.class){
            return true;
        }

        if(parameterType == MultipartFile.class){
            return true;
        }

        if(parameterType == List.class || parameterType == Collection.class){
            // 获取集合中的泛型是否是MultipartFile
            Type genericParameterType = parameter.getGenericParameterType();
            ParameterizedType type = (ParameterizedType)genericParameterType;

            return type.getActualTypeArguments()[0] == MultipartFile.class;
        }


        return false;
    }
}
