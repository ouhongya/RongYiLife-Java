package com.rysh.module.farm.beans;

import lombok.Data;

import java.util.List;

@Data
public class FarmParam {
    private String id;
    private String name;
    private String description;
    private String CategoryId;
    private List<FarmImg> imgUrls;
    private FarmSpec spec;
}
