package com.rysh.module.commonService.beans;

import lombok.Data;

import java.util.Date;

@Data
public class GoodsItem {
    private String id;

    private String name;

    private String categoryId;

    private Integer status;

    private String oprerator;

    private Date createdTime;

    private Date lastedUpdateTime;

    private String description;
    //商品上下架状态
    private int state;
    //商品审核状态
    private int pass;
}
