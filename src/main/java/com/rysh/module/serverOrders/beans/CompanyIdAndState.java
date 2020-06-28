package com.rysh.module.serverOrders.beans;

import lombok.Data;

@Data
public class CompanyIdAndState {
    private String companyId;
    private Integer state;
    private String search;    //搜索条件
    private String sortByFiled;    //数据库字段
    private String sortByOrder;   //排序
}
