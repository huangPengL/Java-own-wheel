package com.hpl.web.resolver.hmar;

import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.support.WebServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/16 22:08
 */
public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {

    final List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();

    Map<MethodParameter,HandlerMethodArgumentResolver> argumentResolverCache = new HashMap<>();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        for(HandlerMethodArgumentResolver resolver: resolvers){
            if(resolver.supportsParameter(parameter)){
                argumentResolverCache.put(parameter,resolver);
                return true;
            }
        }

        return false;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConvertComposite convertComposite) throws Exception {
        final HandlerMethodArgumentResolver resolver = argumentResolverCache.get(parameter);
        return resolver.resolveArgument(parameter, handlerMethod, webServletRequest, convertComposite);
    }

    public void addResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        this.resolvers.addAll(resolvers);
    }
}
