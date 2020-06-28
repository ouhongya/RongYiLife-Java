package com.rysh.module.store.beans;

import com.rysh.module.community.beans.Community;
import com.rysh.module.serverSystem.beans.Owner;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/*
*
* 周边商铺信息展示
*/
@Data
public class StoreInfo {
    private String id;
    private List<Owner> account;
    private String name;
    private String address;
    private List<Community> communities;
    private String contactNum;
    private String intro;
    private List<StoreTag> tags;
    private BigDecimal freight;
    private int status;
}
