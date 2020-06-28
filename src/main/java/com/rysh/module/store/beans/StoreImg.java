package com.rysh.module.store.beans;

import lombok.Data;

import java.util.Date;

@Data
public class StoreImg {
    private String id;

    private String url;

    private int status;

    private Date createdTime;

    private Date lastedUpdateTime;

    private String itemId;

    private int location;//0：缩略图 1-n:banner -1：详情图
}