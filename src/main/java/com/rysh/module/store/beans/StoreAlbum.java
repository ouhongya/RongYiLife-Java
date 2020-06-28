package com.rysh.module.store.beans;

import lombok.Data;

import java.util.Date;

@Data
public class StoreAlbum {
    private String id;
    private String url;
    private int isCover;
    private String storeId;
    private Date createdTime;
    private int status;
}
