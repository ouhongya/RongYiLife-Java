package com.rysh.module.grange.beans;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GrangeItemAndImgs {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String CategoryId;
    private List<String> imgUrls;
}
