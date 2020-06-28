package com.rysh.module.farm.beans;

import lombok.Data;

import java.util.Date;

@Data
public class FarmCategory {
    private String id;

    private String name;

    private String farmId;

    private Date createdTime;

    private int status;

    private Date lastedUpdateTime;
}