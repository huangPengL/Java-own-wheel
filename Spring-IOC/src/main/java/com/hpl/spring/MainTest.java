package com.hpl.spring;

import com.hpl.spring.core.ApplicationContext;
import com.hpl.spring.core.impl.AnnotationApplicationContext;
import com.hpl.spring.service.TestService;
import org.junit.jupiter.api.Test;

/**
 * @Author: huangpenglong
 * @Date: 2023/7/1 23:37
 */
public class MainTest {

    @Test
    public void testIoc(){
        ApplicationContext context = new AnnotationApplicationContext("com.hpl.spring");

        TestService testService = (TestService)context.getBean(TestService.class);

        testService.insert();
    }
}
