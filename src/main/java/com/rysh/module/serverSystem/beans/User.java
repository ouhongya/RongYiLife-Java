package com.rysh.module.serverSystem.beans;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class User {

    private String id;     //主键id
    private String username;    //用户名
    private String password;    //密码
    private Date createdTime;   //创建时间
    private String createdTimeStr;  //创建时间字符串
    private Date lastedUpdateTime;   //更新时间
    private String lastedUpdateTimeStr; // 更新时间字符串
    private Integer isInsider;    //是否是公司内部人员
    private String CompanyId;
    private Integer status;   //状态  1为有效，0为禁用，-1为删除
    private String trueName;    //用户真实姓名
    private String phoneNum;    //电话号码
    private List<Role> roles;   //用户对应的角色集合
    private List<Function> functions;  //用户对应的功能集合
    private String farmId;     //农场id
    public String getId() {
        return id;
    }


}
