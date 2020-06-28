package com.rysh.module.statistics.beans;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RYSHPrice {
    private Integer belongType;
    private BigDecimal price;
}
