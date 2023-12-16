package com.hpl.web.resolver;

import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.support.WebServletRequest;
import org.springframework.core.MethodParameter;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:40
 */
public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(MethodParameter parameter);

    Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConvertComposite convertComposite) throws Exception;
}
