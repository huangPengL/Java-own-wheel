package com.hpl.dbrouter.starter.model;

import com.hpl.dbrouter.starter.util.DBContextHolder;

/**
 *
 * @Author: huangpenglong
 * @Date: 2024/2/4 15:02
 */
public class DBRouterBase {

    private String tbIdx;

    public String getTbIdx() {
        return DBContextHolder.getTBKey();
    }
}
