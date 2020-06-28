package com.rysh.module.statistics.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleDetail {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createdTime;

    private String title;

    private String cover;

    private Integer sort;

    private Integer liked;

    private String operator;
}
