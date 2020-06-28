package com.rysh.module.farm.beans;

import lombok.Data;

import java.util.List;

@Data
public class FarmCheckParam {
    private List<String> ids;
    private String comment;
}
