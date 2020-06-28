package com.rysh.module.grange.beans;

import com.rysh.module.serverSystem.beans.Owner;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Grange {
    private String id;

    private String name;

    private String address;

    private Integer score;

    private Date createdTime;

    private String intro;

    private String contactNum;

    private int status;

    private String areaId;

    private Date lastedUpdateTime;

    //真名-账户名
    private List<Owner> owners = new ArrayList<>();

    //销量
    private int sales;

    //评分
    private double mark;

    //运费
    private BigDecimal freight;

    private int defaultSort;//排序
}