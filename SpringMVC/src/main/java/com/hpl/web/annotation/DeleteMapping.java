package com.hpl.web.annotation;

import com.hpl.web.enums.RequestMethod;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/16 17:36
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(methods = RequestMethod.DELETE)
public @interface DeleteMapping {

    @AliasFor(annotation = RequestMapping.class)
    String value() default "";
}