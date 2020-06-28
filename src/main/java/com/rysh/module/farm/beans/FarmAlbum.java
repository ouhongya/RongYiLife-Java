package com.rysh.module.farm.beans;

import lombok.Data;

import java.util.Date;

@Data
public class FarmAlbum {
    private String id;
    private String url;
    private int isCover;
    private String farmId;
    private Date createdTime;
    private Date lastedUpdateTime;
    private int status;
}
