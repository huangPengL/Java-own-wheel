package com.hpl.web.resolver.hmar;

import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.support.WebServletRequest;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:40
 */
public class ServletRequestMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == HttpServletRequest.class;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConvertComposite convertComposite) throws Exception {
        return webServletRequest.getRequest();
    }
}
