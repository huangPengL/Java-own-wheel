package com.hpl.web.resolver.hmar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpl.web.annotation.RequestBody;
import com.hpl.web.convert.ConvertComposite;
import com.hpl.web.handler.HandlerMethod;
import com.hpl.web.resolver.HandlerMethodArgumentResolver;
import com.hpl.web.support.WebServletRequest;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @description: 处理json
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:40
 */
public class RequestRequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBody.class);
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConvertComposite convertComposite) throws Exception {
        final String json = getJson(webServletRequest.getRequest());
        return objectMapper.readValue(json, parameter.getParameterType());
    }

    private String getJson(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder();
        String line;

        try(final BufferedReader reader = request.getReader()){
            line = reader.readLine();
            while(line != null){
                sb.append(line);
                line = reader.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
