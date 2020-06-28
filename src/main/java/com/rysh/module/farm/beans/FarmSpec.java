package com.rysh.module.farm.beans;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FarmSpec {
    private String id;
    private BigDecimal price;
    private String unit;
    private Date createdTime;
    private Date lastedUpdateTime;
    private String itemId;
}
