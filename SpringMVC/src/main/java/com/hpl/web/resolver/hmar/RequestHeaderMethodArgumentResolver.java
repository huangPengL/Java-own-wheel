package com.hpl.web.resolver.hmar;

import com.hpl.web.annotation.RequestHeader;
import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.exception.NotFoundException;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.support.WebServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description: 获取请求头中的指定内容
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:40
 */
public class RequestHeaderMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestHeader.class)
                && parameter.getParameterType() != Map.class;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConvertComposite convertComposite) throws Exception {
        final RequestHeader parameterAnnotation = parameter.getParameterAnnotation(RequestHeader.class);
        if(parameterAnnotation == null){
            return null;
        }
        String name = "".equals(parameterAnnotation.value())
                ? parameter.getParameterName()
                : parameterAnnotation.value();

        final HttpServletRequest request = webServletRequest.getRequest();
        if (parameterAnnotation.require() && ObjectUtils.isEmpty(request.getHeader(name))){
            throw new NotFoundException(handlerMethod.getPath() + "请求头没有携带: " + name);
        }

        return convertComposite.convert(handlerMethod,parameter.getParameterType(),request.getHeader(name));
    }
}
