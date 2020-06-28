package com.rysh.module.statistics.beans;

import lombok.Data;

import java.util.Date;

@Data
public class ActivityDetail {

    private String id;
    private Date createdTime;
    private String cover;
    private String name;
    private String description;
    private String publishPerson;
    private String publishUnit;
    private String phone;
    private int joinNum;
}
