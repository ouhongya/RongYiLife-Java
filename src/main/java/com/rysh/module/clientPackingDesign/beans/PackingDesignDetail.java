package com.rysh.module.clientPackingDesign.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("社群定制详情")
@Data
public class PackingDesignDetail {
    @ApiModelProperty(name = "content",value = "详情内容")
    private String content;
    @ApiModelProperty(name = "内容类型",value = "0图片url   1文字")
    private Integer type;
}
