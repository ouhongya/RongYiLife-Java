package com.rysh.module.farm.beans;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 用于给农场主查看所有信息的一个展示类
 * @return
 * @author Hsiang Sun
 * @date 2019/9/23 15:17
 */
@Data
public class FarmItemView {
    private String id;
    private String img;
    private String name;
    private BigDecimal price;
    private String unit;
    private String category;
    private int state;
    private String description;
    private Date createdTime;
    private int pass;
    private String passComment;
    private String passOperator;
    private Date passTime;
}
