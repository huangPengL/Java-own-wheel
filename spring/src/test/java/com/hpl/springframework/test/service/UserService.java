package com.hpl.springframework.test.service;

import com.hpl.springframework.test.dao.UserDao;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:45
 */
public class UserService {

    private String uId;

    private String company;

    private String location;

    private UserDao userDao;

    public UserService() {
    }

    public UserService(String name) {
        this.uId = name;
    }

    public void queryUserInfo(){
        System.out.println("查询用户信息" + userDao.queryUserName(uId));
    }

    public String getUserInfo(){
       return "查询用户信息" + userDao.queryUserName(uId);
    }

    public String getName() {
        return uId;
    }

    public void setName(String name) {
        this.uId = name;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "uId='" + uId + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", userDao=" + userDao +
                '}';
    }
}

