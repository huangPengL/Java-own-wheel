package com.hpl.dbrouter.starter.config;

import com.hpl.dbrouter.starter.dynamic.DynamicDataSource;
import com.hpl.dbrouter.starter.model.DBRouterConfig;
import com.hpl.dbrouter.starter.util.PropertyUtil;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/4 14:39
 */


@Configuration
public class DataSourceAutoConfig implements EnvironmentAware {

    /**
     * 配置文件上配置的多个数据源
     */
    private final Map<String, Map<String, Object>> dataSourceMap = new HashMap<>();

    /**
     * 分库数
     */
    private int dbCount;

    /**
     * 分表数
     */
    private int tbCount;


    /**
     * 凡注册到Spring容器内的bean，实现了EnvironmentAware接口重写setEnvironment方法后，
     * 在工程启动时可以获得application.properties的配置文件配置的属性值。
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        String prefix = "router.jdbc.datasource.";

        dbCount = Integer.parseInt(environment.getProperty(prefix + "dbCount"));
        tbCount = Integer.parseInt(environment.getProperty(prefix + "tbCount"));

        String dataSources = environment.getProperty(prefix + "list");
        assert dataSources != null;

        // 遍历配置的多个数据源，放入dataSourceMap中
        for (String dbInfo : dataSources.split(",")) {
            Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, prefix + dbInfo, Map.class);
            dataSourceMap.put(dbInfo, dataSourceProps);
        }
    }

    /**
     * 将 库数和表数 作为bean注入IOC中 便有后续计算路由时候获取
     * @return
     */
    @Bean
    public DBRouterConfig dbRouterConfig() {
        return new DBRouterConfig(dbCount, tbCount);
    }

    @Bean
    public DataSource dataSource() {
        // 创建数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        for (String dbInfo : dataSourceMap.keySet()) {
            Map<String, Object> objMap = dataSourceMap.get(dbInfo);
            targetDataSources.put(dbInfo,
                    new DriverManagerDataSource(
                            objMap.get("url").toString(),
                            objMap.get("username").toString(),
                            objMap.get("password").toString())
            );
        }
        // 设置数据源（DynamicDataSource 重写了选择db数据源的方法，只需把所有待定db数据源以Map的方式设置进来即可）
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }
}
