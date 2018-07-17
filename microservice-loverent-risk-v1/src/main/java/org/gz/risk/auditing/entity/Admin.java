package org.gz.risk.auditing.entity;

import java.io.Serializable;

/**
 * @author JarkimZhu
 *         Created on 2016/11/28.
 * @since jdk1.8
 */
public class Admin implements Serializable{
	
    private String userId;
    private String password;
    private String groupId;
    private String ip;
    /**
     * 用户姓名
     */
    private String userFullname;
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    
    public String getUserFullname() {
        return userFullname;
    }

    
    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }
    
}
