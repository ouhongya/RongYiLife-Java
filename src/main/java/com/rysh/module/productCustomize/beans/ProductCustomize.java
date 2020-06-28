package com.rysh.module.productCustomize.beans;

import lombok.Data;

import java.util.Date;

/*
*
* 社区定制
* */
@Data
public class ProductCustomize {
    private String id;
    private String title;
    private String cover;
    private String productCustomizedCategoryId;
    private String operator;
    private int pass;
    private String passOperator;
    private Date passTime;
    private String passComment;
    private Date createdTime;
    private int status;
    private int defaultSort;
}
