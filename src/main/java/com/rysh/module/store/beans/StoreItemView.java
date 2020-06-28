package com.rysh.module.store.beans;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StoreItemView {
    private String id;
    private String img;
    private String name;
    private BigDecimal price;
    private String unit;
    private String category;
    private int state;
    private String description;
    private Date createdTime;
    private int pass;
    private String passComment;
    private String passOperator;
    private Date passTime;
}

