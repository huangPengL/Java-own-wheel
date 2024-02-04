package com.hpl.dbrouter;

import com.hpl.dbrouter.model.User;
import com.hpl.dbrouter.repository.IUserDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/3 23:22
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private IUserDao userDao;

    @Test
    public void testDb(){

        User user = new User();
        user.setUserId("1");

        User userInfoByUserId = userDao.queryUserInfoByUserId(user);
        System.out.println(userInfoByUserId);
    }
}
