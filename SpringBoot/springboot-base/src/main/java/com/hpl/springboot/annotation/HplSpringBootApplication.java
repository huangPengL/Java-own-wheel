package com.hpl.springboot.annotation;

import com.hpl.springboot.config.WebServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: huangpenglong
 * @Date: 2023/7/31 17:41
 */

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)

// 包扫描器，扫描定义该注解的类中所在的包及其子包
@ComponentScan

// 该注解指定扫描本项目内的指定类
@Import(WebServiceAutoConfiguration.class)
public @interface HplSpringBootApplication {
}
