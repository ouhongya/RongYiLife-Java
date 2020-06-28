package com.rysh.module.grange.beans;

import lombok.Data;

import java.util.List;

@Data
public class GrangeParam {
    private String id;
    private String name;
    private String description;
    private String CategoryId;
    private List<GrangeImg> imgUrls;
    private GrangeSpec spec;
}
