package com.rysh.module.serverSystem.beans;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class respUserFunc {
    private String name;
    private String component;
    private String redirect;
    private String path;
    private Boolean alwaysShow;
    private Meta meta;
    private List<Children> children=new ArrayList<>();
}
