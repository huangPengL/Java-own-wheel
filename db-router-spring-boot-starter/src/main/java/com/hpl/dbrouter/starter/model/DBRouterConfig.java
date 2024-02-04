package com.hpl.dbrouter.starter.model;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/4 14:50
 */
public class DBRouterConfig {

    /**
     * 分库数
     */
    private int dbCount;

    /**
     * 分表数
     */
    private int tbCount;

    public DBRouterConfig() {
    }

    public DBRouterConfig(int dbCount, int tbCount) {
        this.dbCount = dbCount;
        this.tbCount = tbCount;
    }

    public int getDbCount() {
        return dbCount;
    }

    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }

    public int getTbCount() {
        return tbCount;
    }

    public void setTbCount(int tbCount) {
        this.tbCount = tbCount;
    }

}
