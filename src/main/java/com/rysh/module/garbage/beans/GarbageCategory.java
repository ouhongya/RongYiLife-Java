package com.rysh.module.garbage.beans;

import lombok.Data;

import java.util.Date;

@Data
public class GarbageCategory {
    private String id;

    private String cityId;

    private String name;

    private String color;

    private Date createdTime;

    private Date lastedUpdateTime;

    private int status;

    private String url;
}