package com.rysh.module.shop.beans;

import lombok.Data;

import java.util.Date;

@Data
public class ShopCategory {
    private String id;

    private String name;

    private String shopId;

    private int status;

    private Date createdTime;

    private Date lastedUpdateTime;
}