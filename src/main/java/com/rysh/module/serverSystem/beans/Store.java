package com.rysh.module.serverSystem.beans;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Store {
    private String id;   //商铺id
    private String name;  //商铺名称
    private String address; //商铺地址
    private String intro;  //商铺描述
    private List<Owner> owners=new ArrayList<>();  //第三方所属用户
    private List<StoreCommunity> storeCommunities=new ArrayList<>();  //商铺绑定的小区集合
    private String tel;  //商铺联系电话
    private BigDecimal freight;  //运费
    private Date createdTime;  //商铺创建时间
    private String createTimeStr; //商铺创建时间字符串
    private Date lastedUpdateTime;  //商铺更新时间
    private String lastedUpdateTimeStr;  //商铺更新时间字符串
    private List<String> communityIds;   //小区id集合
    private int status;   //1 启用  0 禁用  -1软删除
    private int state;
}
