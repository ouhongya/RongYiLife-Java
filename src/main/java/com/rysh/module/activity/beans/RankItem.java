package com.rysh.module.activity.beans;

import lombok.Data;

import java.util.Date;

@Data
public class RankItem {
    private String id;
    private int sequence;
    private String name;
    private String url;
    private String rankingUserId;
    private Date createdTime;
    private Date lastedUpdateTime;
    private int status;
}
