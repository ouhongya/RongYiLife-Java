package com.rysh.module.farm.beans;

import lombok.Data;

import java.util.List;

@Data
public class OperatorAndIds {
    private String operator;
    private List<String> ids;
}
