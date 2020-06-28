package com.rysh.module.serverSystem.beans;


import java.util.Date;
import java.util.List;

public class Function {
    private String id;            //主键id
    private String functionName;     //权限的中文名称
    private String functionUrl;     //权限名称   url路径
    private String status;        //状态  1为有效，0为禁用，-1为删除
    private Date   createdTime;   //创建时间
    private Date   lastedUpdateTime;  //更新时间
    private List<User> users;    //绑定了此功能的用户集合
    private List<Role> roles;    //绑定了此功能的角色集合
    private String   parentFunctionName; //一级菜单名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastedUpdateTime() {
        return lastedUpdateTime;
    }

    public void setLastedUpdateTime(Date lastedUpdateTime) {
        this.lastedUpdateTime = lastedUpdateTime;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getParentFunctionName() {
        return parentFunctionName;
    }

    public void setParentFunctionName(String parentFunctionName) {
        this.parentFunctionName = parentFunctionName;
    }
}
