package com.hpl.web.resolver.hmar;

import com.hpl.web.annotation.Cookie;
import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.support.WebServletRequest;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @description: 解析cookie当中的参数
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:40
 */
public class RequestCookieMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Cookie.class);
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConvertComposite convertComposite) throws Exception {

        Cookie parameterAnnotation = parameter.getParameterAnnotation(Cookie.class);
        if(parameterAnnotation == null){
            return null;
        }

        String name = "".equals(parameterAnnotation.value())
                ? parameter.getParameterName()
                : parameterAnnotation.value();

        javax.servlet.http.Cookie[] cookies = webServletRequest.getRequest().getCookies();
        for (javax.servlet.http.Cookie cookie : cookies) {
            if(cookie.getName().equals(name)){
                return convertComposite.convert(handlerMethod, parameter.getParameterType(), cookie.getValue());
            }
        }
        return null;
    }
}
