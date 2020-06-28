package com.rysh.module.serverSystem.beans;

import java.util.List;

public class newUser {
    private String trueName;
    private String phoneNum;
    private String username;
    private Integer isInsider;
    private String password;
    private List<String> roleNameArr;
    private List<String> functionArr;
    private String roleName;   //角色名称
    private String bossId;   //老板id

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoleNameArr() {
        return roleNameArr;
    }

    public void setRoleNameArr(List<String> roleNameArr) {
        this.roleNameArr = roleNameArr;
    }

    public List<String> getFunctionArr() {
        return functionArr;
    }

    public void setFunctionArr(List<String> functionArr) {
        this.functionArr = functionArr;
    }

    public Integer getIsInsider() {
        return isInsider;
    }

    public void setIsInsider(Integer isInsider) {
        this.isInsider = isInsider;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getBossId() {
        return bossId;
    }

    public void setBossId(String bossId) {
        this.bossId = bossId;
    }
}
