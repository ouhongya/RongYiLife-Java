package com.rysh.module.statistics.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class VideoDetail {
    @JsonIgnore
    private String id;

    private Date createdTime;
    private String title;
    private String intros;
    private String url;
    private Integer liked;
    private Integer defaultSort;
}
