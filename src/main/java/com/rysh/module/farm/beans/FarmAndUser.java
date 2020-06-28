package com.rysh.module.farm.beans;

import com.rysh.module.serverSystem.beans.Owner;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class FarmAndUser {

    private String id;
    private Date createdTime;
    private String name;
    private String address;
    private int score;
    //真名-账户名
    private List<Owner> owners = new ArrayList<>();
    private String cityId;
    private String areaId;
    private String contactNum;
    private String intro;
    private List<Tag> tags;
    //运费
    private BigDecimal freight;
    private int status;
    private int defaultSort;//排序
}
