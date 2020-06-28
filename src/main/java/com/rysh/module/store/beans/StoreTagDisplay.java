package com.rysh.module.store.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class StoreTagDisplay {
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    private String tagName;
    private int BindingCount;//绑定数量
    private String operator;
}
