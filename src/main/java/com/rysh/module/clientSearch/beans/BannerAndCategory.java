package com.rysh.module.clientSearch.beans;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel("")
@Data
public class BannerAndCategory {
    @ApiModelProperty(name = "banner",value = "自营商城banner图对象集合")
    List<Banner> banner;
    @ApiModelProperty(name = "typeList",value = "自营商城分类对象集合")
    List<Type> typeList;
}
