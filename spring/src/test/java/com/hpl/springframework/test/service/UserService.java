package com.hpl.springframework.test.service;

import com.hpl.springframework.beans.ex.BeansException;
import com.hpl.springframework.beans.factory.*;
import com.hpl.springframework.context.ApplicationContext;
import com.hpl.springframework.test.dao.UserDao;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/7 22:45
 */
public class UserService implements InitializingBean, DisposableBean, BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;


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

    @Override
    public void destroy() throws Exception {
        System.out.println("执行：UserService.destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行：UserService.afterPropertiesSet");
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


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("UserService Aware感知BeanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("UserService Aware感知ApplicationContext");
    }

    @Override
    public void setBeanName(String name) {

        System.out.println("UserService Aware感知 Bean Name is：" + name);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("UserService Aware感知 ClassLoader：" + classLoader);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}

