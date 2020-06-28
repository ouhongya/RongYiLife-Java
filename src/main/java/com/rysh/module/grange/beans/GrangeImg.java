package com.rysh.module.grange.beans;

import lombok.Data;

import java.util.Date;

@Data
public class GrangeImg {
    private String id;

    private String url;

    private int status;

    private String grangeItemId;

    private Date createdTime;

    private Date lastedUpdateTime;

    private int location;
}