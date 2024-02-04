package com.hpl.dbrouter.starter.annotation;

import java.lang.annotation.*;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/4 14:35
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouter {

    String key() default "";
}
