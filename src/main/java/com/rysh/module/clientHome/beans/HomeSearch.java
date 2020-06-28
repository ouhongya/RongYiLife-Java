package com.rysh.module.clientHome.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("搜索响应对象")
public class HomeSearch<T> {
    @ApiModelProperty("是否是店铺 0：否 1:是")
    private int isStore;
    @ApiModelProperty("农场或者农庄对象")
    private Object Store;
    @ApiModelProperty("商品列表")
    private List<T> itemList;
}
