package com.hpl.spring.dao.impl;

import com.hpl.spring.annotation.Bean;
import com.hpl.spring.dao.TestDao;

/**
 * @Author: huangpenglong
 * @Date: 2023/7/1 15:13
 */

@Bean
public class TestDaoImpl implements TestDao {

    @Override
    public void insert() {
        System.out.println("Dao 插入记录完毕！");
    }
}
