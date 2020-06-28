package com.rysh.module.serverSystem.beans;


import java.util.Date;
import java.util.List;

public class Role {
    private String id;    //主键id
    private String roleName;  //角色名称
    private Integer status;    //状态   0=启用   1=禁用
    private Date   createdTime;    //创建时间
    private String createTimeStr;   //创建时间字符串
    private Date   lastedUpdateTime;   //更新时间
    private String lastedUpdateTimeStr;  //更新时间字符串
    private List<Function> functions;   //角色对应的功能集合
    private List<ParentFandSonF> ps;   //一级菜单  二级菜单集合

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public List<ParentFandSonF> getPs() {
        return ps;
    }

    public void setPs(List<ParentFandSonF> ps) {
        this.ps = ps;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getLastedUpdateTimeStr() {
        return lastedUpdateTimeStr;
    }

    public void setLastedUpdateTimeStr(String lastedUpdateTimeStr) {
        this.lastedUpdateTimeStr = lastedUpdateTimeStr;
    }
}
