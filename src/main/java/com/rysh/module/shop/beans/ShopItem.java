package com.rysh.module.shop.beans;

import lombok.Data;

import java.util.Date;

@Data
public class ShopItem {
    private String id;

    private String name;

    private String categoryId;

    private int state;

    private int status;

    private String oprerator;

    private Date createdTime;

    private Date lastedUpdateTime;

    private int pass;

    private String passOperator;

    private Date passTime;

    private String passComment;

    private String description;
}