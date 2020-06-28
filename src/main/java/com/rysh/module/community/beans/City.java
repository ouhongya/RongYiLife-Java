package com.rysh.module.community.beans;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
public class City {
    //城市id
    private String id;
    //城市名称
    private String cityName;
    //创建时间
    private Date createdTime;
    //最后更新时间
    private Timestamp lastedUpdateTime;

    private List<Area> areas;

    private int cityCode;
}
