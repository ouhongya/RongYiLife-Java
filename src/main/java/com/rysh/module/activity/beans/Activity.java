package com.rysh.module.activity.beans;

import lombok.Data;

import java.util.Date;

@Data
public class Activity {
    private String id;
    private String name;
    private Date startTime;
    private Date endTime;
    private int member;
    private int freeMember;
    private String location;
    private String publisher;
    private String publisherUnit;//活动发起单位
    private String contactNum;//联系方式
    private int pass;
    private String passOperator;
    private Date passTime;
    private String passComment;
    private Date createdTime;
    private Date lastedUpdateTime;
    private Integer status;
    private Date applyStartTime;
    private Date applyEndTime;
    private String areaId;//活动区域
    private String url; //活动图片
}
