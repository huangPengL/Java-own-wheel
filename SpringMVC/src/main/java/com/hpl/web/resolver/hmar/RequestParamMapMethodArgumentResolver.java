package com.hpl.web.resolver.hmar;

import com.hpl.web.annotation.RequestBody;
import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.support.WebServletRequest;
import com.hpl.web.utils.MultipartUtil;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 获取所有普通数据
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:40
 */
public class RequestParamMapMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        if(parameterType == HttpServletRequest.class || parameterType == HttpServletResponse.class){
            return false;
        }

        if(MultipartUtil.isMultipartFile(parameter)){
            return false;
        }

        if(parameter.hasParameterAnnotation(RequestBody.class)){
            return false;
        }
        return parameterType == Map.class;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConvertComposite convertComposite) throws Exception {

        final Map<String, String[]> parameterMap = webServletRequest.getRequest().getParameterMap();
        Map<String, Object> resultMap = new HashMap<>();
        parameterMap.forEach((k, v) -> {
            resultMap.put(k, v[0]);
        });
        return resultMap;
    }
}
