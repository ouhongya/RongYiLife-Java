package com.rysh.module.shop.beans;

import lombok.Data;

import java.util.Date;

@Data
public class ShopImg {
    private String id;

    private String url;

    private int status;

    private Date createdTime;

    private Date lastedUpdateTime;

    private String itemId;

    private int location;
}