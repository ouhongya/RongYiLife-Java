package com.rysh.module.productCustomize.beans;

import lombok.Data;

import java.util.Date;

@Data
public class CheckPass {
    private CheckParam checkParam;
    private Date passTime;
    private String passOperator;
}
