package com.hpl.springboot.config;

import com.hpl.springboot.condition.JettyCondition;
import com.hpl.springboot.condition.TomcatCondition;
import com.hpl.springboot.webserver.JettyServer;
import com.hpl.springboot.webserver.TomcatServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: huangpenglong
 * @Date: 2023/8/1 11:23
 */

@Configuration
public class WebServiceAutoConfiguration {

    /**
     * 注解@Conditional(TomcatCondition.class)表示，若客户端有tomcat的依赖则导入该bean否则不导入
     * @return
     */
    @Bean
    @Conditional(TomcatCondition.class)
    public TomcatServer tomcatServer(){
        return new TomcatServer();
    }

    @Bean
    @Conditional(JettyCondition.class)
    public JettyServer jettyServer(){
        return new JettyServer();
    }
}
