package com.hpl.web.resolver.hmar;

import com.hpl.web.annotation.PathVariable;
import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.support.WebServletRequest;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description: 解析路径参数
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:40
 */
public class PathVariableMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PathVariable.class) && parameter.getParameterType() != Map.class;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConvertComposite convertComposite) throws Exception {
        String name ;
        Object result = null;
        // 1.获取 PathVariable 中的变量
        final PathVariable parameterAnnotation = parameter.getParameterAnnotation(PathVariable.class);
        if(parameterAnnotation == null){
            return null;
        }
        name = "".equals(parameterAnnotation.value())
                ? parameter.getParameterName()
                : parameterAnnotation.value();
        if(name == null){
            return null;
        }
        // 1.以/ 分割源path，找到变量 保存下标以及对应的变量
        final String path = handlerMethod.getPath();
        int index = -1;
        String[] split = path.split("/");
        for (int i = 0; i < split.length; i++) {
            final String s = split[i];

            if (s.contains("{") && s.contains("}") && s.contains(name)){
                index = i;
                break;
            }
        }
        final HttpServletRequest request = webServletRequest.getRequest();
        // 2.以/ 分割请求path，根据上一步找到的下标， 找到对应的值，放入resultMap
        if (index != -1){
            split = request.getRequestURI().split("/");
            result = split[index];
        }
        return convertComposite.convert(handlerMethod, parameter.getParameterType(), result);
    }
}
