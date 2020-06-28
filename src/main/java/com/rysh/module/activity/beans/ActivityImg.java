package com.rysh.module.activity.beans;

import lombok.Data;

import java.util.Date;

@Data
public class ActivityImg {
    private String id;
    private String url;
    private int location;
    private String activityId;
    private Date createdTime;
    private int status;
}
