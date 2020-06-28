package com.rysh.module.statistics.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserDetail {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createdTime;

    private String loginName;

    private String name;

    private String city;

    private String area;

    private String community;
}
