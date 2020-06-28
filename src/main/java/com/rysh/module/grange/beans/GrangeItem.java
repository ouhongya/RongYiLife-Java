package com.rysh.module.grange.beans;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GrangeItem {
    private String id;

    private String name;

    private BigDecimal price;

    private String categoryId;

    private int status;

    private String oprerator;

    private Date createdTime;

    private Date lastedUpdateTime;

    private String description;

    private int state;

    private int pass;
}