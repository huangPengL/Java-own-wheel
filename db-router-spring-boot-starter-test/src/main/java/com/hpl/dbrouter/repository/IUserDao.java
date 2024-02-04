package com.hpl.dbrouter.repository;

import com.hpl.dbrouter.starter.annotation.DBRouter;
import com.hpl.dbrouter.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/3 23:12
 */

@Mapper
public interface IUserDao {
    @DBRouter(key = "userId")
    User queryUserInfoByUserId(User req);

    @DBRouter(key = "userId")
    void insertUser(User req);
}
