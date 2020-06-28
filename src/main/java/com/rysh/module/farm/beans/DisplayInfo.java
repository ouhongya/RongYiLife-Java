package com.rysh.module.farm.beans;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DisplayInfo<T> {
    private String id;
    private Long score;
    private List<T> account;
    private String name;
    private String address;
    private String trueName;
    private String intro;
    private String contactNum;
    private List<Tag> tags;
    private BigDecimal freight;
    private int status;
}
