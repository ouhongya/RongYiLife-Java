package com.rysh.module.farm.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Farm {
    private String id;

    private String name;

    private String address;

    private String intro;

    private String contactNum;

    private String areaId;

    private Integer score;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createdTime;

    private int status;

    private Date lastedUpdateTime;

    //销量
    private int sales;

    //评分
    private double mark;

    //运费
    private BigDecimal freight;

    private int defaultSort;

}