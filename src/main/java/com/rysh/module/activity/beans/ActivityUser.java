package com.rysh.module.activity.beans;

import lombok.Data;

import java.util.Date;

@Data
public class ActivityUser {
    private String id;
    private String userId;
    private String ActivityId;
    private String name;
    private String phone;
    private Date createdTime;
    private Date lastedUpdateTime;
    private int status;
    private String token;
}
