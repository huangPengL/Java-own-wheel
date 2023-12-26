package com.hpl.web.resolver.hmrvh;

import com.hpl.web.exception.NotFoundException;
import com.hpl.web.resolver.HandlerMethodReturnValueHandler;
import com.hpl.web.support.WebServletRequest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/25 17:08
 */
public class HandlerMethodReturnValueHandlerComposite {
    private List<HandlerMethodReturnValueHandler> methodReturnValueHandlers = new ArrayList<>();

    public void addMethodReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers){
        this.methodReturnValueHandlers.addAll(handlerMethodReturnValueHandlers);
    }

    public void doInvoke(Object returnValue, Method method, WebServletRequest request) throws Exception {
        selectHandler(method).handleReturnValue(returnValue, request);
    }

    private HandlerMethodReturnValueHandler selectHandler(Method method) throws Exception {
        for (HandlerMethodReturnValueHandler methodReturnValueHandler : this.methodReturnValueHandlers) {
            if(methodReturnValueHandler.supportsReturnType(method)){
                return methodReturnValueHandler;
            }
        }
        throw new NotFoundException(method.toString() + "找不到返回值处理器");
    }

}
