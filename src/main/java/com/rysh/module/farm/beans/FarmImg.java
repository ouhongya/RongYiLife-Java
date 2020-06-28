package com.rysh.module.farm.beans;

import lombok.Data;

import java.util.Date;

@Data
public class FarmImg {
    private String id;

    private String url;

    private int status;

    private String farmItemId;

    private Date createdTime;

    private Date lastedUpdateTime;

    private int location;
}