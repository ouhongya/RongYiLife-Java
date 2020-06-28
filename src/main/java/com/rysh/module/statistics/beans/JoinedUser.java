package com.rysh.module.statistics.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/*
* 参加活动的用户
* */
@Data
public class JoinedUser {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date joinedTime;

    private String name;

    private String phone;

    private String activityName;
}
