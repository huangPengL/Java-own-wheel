package com.hpl.web.resolver.hmrvh;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpl.web.annotation.ResponseBody;
import com.hpl.web.resolver.HandlerMethodReturnValueHandler;
import com.hpl.web.support.WebServletRequest;
import org.springframework.core.annotation.AnnotatedElementUtils;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/26 16:19
 */
public class RequestResponseBodyMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    /**
     * 避免对应实体类没有get方法
     */
    final ObjectMapper objectMapper = new ObjectMapper()
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);


    /**
     * 类或者方法上有@RequestBody
     * @param method
     * @return
     */
    @Override
    public boolean supportsReturnType(Method method) {
        return AnnotatedElementUtils.hasAnnotation(method.getDeclaringClass(), ResponseBody.class)
                || AnnotatedElementUtils.hasAnnotation(method, ResponseBody.class);
    }


    /**
     *
     * @param returnValue
     * @param webServletRequest
     * @throws Exception
     */
    @Override
    public void handleReturnValue(Object returnValue, WebServletRequest webServletRequest) throws Exception {
        final HttpServletResponse response = webServletRequest.getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(objectMapper.writeValueAsString(returnValue));
        response.getWriter().flush();
    }
}
