package com.rysh.module.shop.beans;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShopSpec {
    private String id;

    private BigDecimal price;

    private String unit;

    private Date createdTime;

    private Date lastedUpdateTime;

    private String itemId;
}