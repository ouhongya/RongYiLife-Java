package com.rysh.module.store.beans;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Store {
    private String id;

    private String name;

    private String address;

    private String intro;

    private String contactNum;
    //运费  （预留）
    private BigDecimal freight;

    private Date createdTime;

    private Date lastedUpdateTime;

    private int status;
}