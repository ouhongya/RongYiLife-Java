package com.rysh.module.clientHome.beans;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SearchResponse {
    private String id;
    private String name;
    private String url;
    private String unit;
    private BigDecimal price;
}
