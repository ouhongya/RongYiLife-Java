package com.rysh.module.activity.beans;

import lombok.Data;

import java.util.Date;

@Data
public class Rank {
    private String id;
    private String url;
    private String intro;
    private Date createdTime;
    private Date lastedUpdateTime;
    private int status;
    private String operator;
}
