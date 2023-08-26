package com.hpl.springboot.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Author: huangpenglong
 * @Date: 2023/8/1 11:21
 */
public class TomcatCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try{
            context.getClassLoader().loadClass("org.apache.catalina.startup.Tomcat");
            return true;
        }
        catch(ClassNotFoundException e){
            return false;
        }
    }
}
