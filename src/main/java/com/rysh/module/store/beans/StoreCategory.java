package com.rysh.module.store.beans;

import lombok.Data;

import java.util.Date;

@Data
public class StoreCategory {
    private String id;

    private String name;

    private String storeId;

    private int status;

    private Date createdTime;

    private Date lastedUpdateTime;
}