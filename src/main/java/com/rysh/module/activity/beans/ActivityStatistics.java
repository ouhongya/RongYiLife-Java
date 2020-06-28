package com.rysh.module.activity.beans;

import lombok.Data;

import java.util.Date;

@Data
public class ActivityStatistics {
    private Date endTime;
    private String name;
    private String phone;
    private int joinTime;
    private int applyTime;
}
