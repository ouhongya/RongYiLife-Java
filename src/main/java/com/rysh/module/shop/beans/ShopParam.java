package com.rysh.module.shop.beans;

import lombok.Data;

import java.util.List;

@Data
public class ShopParam {
    private String id;
    private String name;
    private String description;
    private String CategoryId;
    private List<ShopImg> imgUrls;
    private ShopSpec spec;
}
