package com.rysh.module.community.beans;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommunityDetail {
    //主键
    private String id;
    //栋
    private String building;
    //单元
    private String unit;
    //室
    private String room;
    //创建时间
    private Timestamp createdTime;
    //最后更新时间
    private Timestamp lastedUpdateTime;
    //关联的社区名称
    private String communityId;
}
