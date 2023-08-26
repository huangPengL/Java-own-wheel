package com.hpl.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: huangpenglong
 * @Date: 2023/7/1 22:44
 */

// 该注解只能应用于类（或接口、枚举或注解）
@Target(ElementType.TYPE)

// @Retention(RetentionPolicy.RUNTIME) 这个注解用于指定被标注的注解在运行时可被保留，可以通过反射获取到。
// RetentionPolicy.RUNTIME 枚举常量表示注解在运行时可用。这意味着在运行时，程序可以使用反射机制来获取注解的信息。
// 如果没有指定@Retention注解，默认使用RetentionPolicy.CLASS，表示注解在编译时被保留，但在运行时不可获取。
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
}
