package com.rysh.module.garbage.beans;

import lombok.Data;

import java.util.Date;

@Data
public class Garbage {
    private String id;

    private String categoryId;

    private String cityId;

    private String name;

    private Date createdTime;

    private Date lastedUpdateTime;

    private int status;
}