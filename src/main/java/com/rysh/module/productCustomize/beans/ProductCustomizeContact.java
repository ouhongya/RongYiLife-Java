package com.rysh.module.productCustomize.beans;

import lombok.Data;

import java.util.Date;

/*
* 联系方式
* */
@Data
public class ProductCustomizeContact {
    private String id;
    private String name;
    private String tel;
    private String productCustomizedId;
    private Date createdTime;
}
