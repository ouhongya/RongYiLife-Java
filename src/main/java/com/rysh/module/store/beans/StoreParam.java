package com.rysh.module.store.beans;

import lombok.Data;

import java.util.List;

@Data
public class StoreParam {
    private String id;
    private String name;
    private String description;
    private String CategoryId;
    private List<StoreImg> imgUrls;
    private StoreSpec spec;
}
