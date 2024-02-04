package com.hpl.dbrouter.model;


import com.hpl.dbrouter.starter.model.DBRouterBase;

import java.util.Date;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/3 23:12
 */

public class User extends DBRouterBase {
    private Long id;

    private String userId;

    private String userNickName;
    private String userHead;
    private String userPassword;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userHead='" + userHead + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public User() {
    }

    public User(Long id, String userId, String userNickName, String userHead, String userPassword, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.userNickName = userNickName;
        this.userHead = userHead;
        this.userPassword = userPassword;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
