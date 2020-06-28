package com.rysh.module.farm.beans;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryInfo {
    //当前分类的ID
    private String id;
    //当前分类创建时间
    private Date createdTime;
    //当前分类的名称
    private String categoryName;
    //当前分类下面所挂载的商品数量
    private int itemNum;
}
