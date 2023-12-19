package com.hpl.web.resolver.hmar;

import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.multipart.MultipartFile;
import com.hpl.web.multipart.StandardMultipartFile;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.support.WebServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @description: 文件上传解析器
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:40
 */
public class RequestPartMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return isMultipartFile(parameter);
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConvertComposite convertComposite) throws Exception {
        // 拿到当前参数的名称
        final String parameterName = parameter.getParameterName();
        if(parameterName == null){
            return null;
        }

        // 获取文件
        final HttpServletRequest request = webServletRequest.getRequest();
        final Collection<Part> parts = request.getParts();
        final List<MultipartFile> files = new ArrayList<>();
        for(Part part: parts){
            if(parameterName.equals(part.getName()) && !ObjectUtils.isEmpty(part)){
                files.add(new StandardMultipartFile(part, parameterName));
            }
        }

        //
        if(CollectionUtils.isEmpty(files)){
            return null;
        }
        Class<?> parameterType = parameter.getParameterType();

        if(parameterType.isArray()){
            return files.toArray(new MultipartFile[files.size()]);
        }
        else if(parameterType == MultipartFile.class){
            return files.get(0);
        }
        else if(parameterType == List.class || parameterType == Collection.class){
            return files;
        }

        return null;
    }


    /**
     * 判断方法上的参赛是否为MultipartFile
     * @param parameter
     * @return
     */
    public static boolean isMultipartFile(MethodParameter parameter){
        Class<?> parameterType = parameter.getParameterType();

        if(parameterType == MultipartFile.class){
            return true;
        }

        if(parameterType == List.class || parameterType == Collection.class){
            // 获取集合中的泛型是否是MultipartFile
            Type genericParameterType = parameter.getGenericParameterType();
            ParameterizedType type = (ParameterizedType)genericParameterType;

            if(type.getActualTypeArguments()[0] == MultipartFile.class){
                return true;
            }
        }

        if(parameterType.isArray() && parameterType == MultipartFile.class){
            return true;
        }

        return false;
    }
}
