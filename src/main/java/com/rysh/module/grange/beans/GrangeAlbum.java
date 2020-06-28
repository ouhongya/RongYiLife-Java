package com.rysh.module.grange.beans;

import lombok.Data;

import java.util.Date;

@Data
public class GrangeAlbum {
    private String id;
    private String url;
    private int isCover;
    private String grangeId;
    private Date createdTime;
    private Date lastedUpdateTime;
    private int status;
}
