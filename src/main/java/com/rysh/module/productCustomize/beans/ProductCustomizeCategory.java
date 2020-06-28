package com.rysh.module.productCustomize.beans;

import lombok.Data;

import java.util.Date;

@Data
public class ProductCustomizeCategory {
    private String  id;
    private String name;
    private Date createdTime;
    private int status;
    private int count;//绑定数
}
