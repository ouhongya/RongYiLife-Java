package com.rysh.module.statistics.beans;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyCount {
    private String time;
    private BigDecimal price = new BigDecimal("0");
}
