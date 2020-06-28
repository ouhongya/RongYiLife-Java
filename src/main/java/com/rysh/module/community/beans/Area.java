package com.rysh.module.community.beans;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
public class Area {
    //区域Id
    private String id;
    //区域名称
    private String areaName;
    //创建时间
    private Date createdTime;
    //最后更新时间
    private Timestamp lastedUpdateTime;
    //关联的城市名称
    private String cityId;

    private List<Community> areas;

    private int areaCode;
}
