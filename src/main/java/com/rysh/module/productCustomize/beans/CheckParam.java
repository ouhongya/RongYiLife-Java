package com.rysh.module.productCustomize.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CheckParam {

    @ApiModelProperty(name = "operation",value = "审核通过")
    private String operation;

    @ApiModelProperty(name = "ids",value = "需要审核的id",dataType = "list")
    private List<String> ids;

    @ApiModelProperty(name = "passComment",value = "审核备注")
    private String passComment;
}
