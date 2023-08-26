package com.hpl.springboot.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Author: huangpenglong
 * @Date: 2023/8/1 11:24
 */
public class JettyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try{
            context.getClassLoader().loadClass("org.eclipse.jetty.server.Server");
            return true;
        }
        catch(ClassNotFoundException e){
            return false;
        }
    }
}
