package com.rysh.module.shop.beans;

import com.rysh.module.serverSystem.beans.Owner;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Shop {
    private String id;

    private String name;

    private String address;

    private int status;

    private Date createdTime;

    private Date lastedUpdateTime;

    private BigDecimal freight;

    //真名-账户名
    private List<Owner> owners = new ArrayList<>();
}