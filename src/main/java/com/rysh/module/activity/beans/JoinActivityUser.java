package com.rysh.module.activity.beans;

import lombok.Data;

import java.util.Date;

@Data
public class JoinActivityUser {
    private String id;
    private String name;
    private String phone;
    private Date applyTime;
    private int joined;
}
