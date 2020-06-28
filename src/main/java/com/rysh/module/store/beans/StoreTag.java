package com.rysh.module.store.beans;

import lombok.Data;

import java.util.Date;

@Data
public class StoreTag {
    private String id;
    //标签名
    private String name;
    private int status;
    private Date createdTime;
    private String operator;
}
