package com.hpl.spring.service.impl;

import com.hpl.spring.annotation.Bean;
import com.hpl.spring.annotation.Di;

import com.hpl.spring.dao.TestDao;
import com.hpl.spring.service.TestService;

/**
 * @Author: huangpenglong
 * @Date: 2023/7/1 15:14
 */


@Bean
public class TestServiceImpl implements TestService {
    @Di
    private TestDao testDao;

    @Override
    public void insert() {
        testDao.insert();
    }
}
