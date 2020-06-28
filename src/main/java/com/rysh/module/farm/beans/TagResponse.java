package com.rysh.module.farm.beans;

import lombok.Data;

import java.util.Date;

@Data
public class TagResponse {
    private String id;
    private String name;
    private String operator;
    private Date createdTime;
    private int count;
}
