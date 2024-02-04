package com.hpl.dbrouter.starter.dynamic;

import com.hpl.dbrouter.starter.util.DBContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/4 14:51
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return "db" + DBContextHolder.getDBKey();
    }
}
