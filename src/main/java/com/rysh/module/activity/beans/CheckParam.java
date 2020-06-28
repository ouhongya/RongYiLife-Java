package com.rysh.module.activity.beans;

import lombok.Data;

import java.util.List;

@Data
public class CheckParam {
    private List<String> ids;
    private String passComment;
}
