package com.hpl.web.resolver.hmar;

import com.hpl.web.annotation.RequestHeader;
import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.support.WebServletRequest;
import org.springframework.core.MethodParameter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * @description: 获取所有请求头中的内容
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:40
 */
public class RequestHeaderMapMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestHeader.class)
                && parameter.getParameterType() == Map.class;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConvertComposite convertComposite) throws Exception {

        Enumeration<String> headerNames = webServletRequest.getRequest().getHeaderNames();
        Map<String, String> resultMap = new HashMap<>();

        while (headerNames.hasMoreElements()){
            String key = headerNames.nextElement();
            String value = webServletRequest.getRequest().getHeader(key);
            resultMap.put(key, value);
        }
        return resultMap;
    }
}
