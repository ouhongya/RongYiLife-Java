package com.rysh.module.farm.beans;

import lombok.Data;

import java.util.Date;

@Data
public class TagEntity {
    private String id;

    private String tagId;

    private String entityId;

    private int belongType;

    private Date createdTime;

    private Date lastedUpdateTime;
}