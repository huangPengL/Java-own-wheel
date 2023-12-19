package com.hpl.web.resolver.hmar;

import com.hpl.web.annotation.PathVariable;
import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.support.WebServletRequest;
import org.springframework.core.MethodParameter;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 解析路径参数转为map
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:40
 */
public class PathVariableMapMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PathVariable.class) && parameter.getParameterType() == Map.class;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConvertComposite convertComposite) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        Map<Integer, String> indexMap = new HashMap<>();

        String path = handlerMethod.getPath();
        String[] split = path.split("/");
        for(int i=0;i<split.length;i++){
            if(split[i].contains("{") && split[i].contains("}")){
                indexMap.put(i, split[i].substring(1, split.length-1));
            }
        }

        String[] urlSplit = webServletRequest.getRequest().getRequestURI().split("/");
        for(Map.Entry<Integer, String> entry: indexMap.entrySet()){
            resultMap.put(entry.getValue(), urlSplit[entry.getKey()]);
        }
        return resultMap;
    }

}
