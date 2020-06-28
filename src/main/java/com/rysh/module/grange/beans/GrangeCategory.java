package com.rysh.module.grange.beans;

import lombok.Data;

import java.util.Date;

@Data
public class GrangeCategory {
    private String id;

    private String name;

    private String grangeId;

    private int status;

    private String oprerator;

    private Date createdTime;

    private Date lastedUpdateTime;

}