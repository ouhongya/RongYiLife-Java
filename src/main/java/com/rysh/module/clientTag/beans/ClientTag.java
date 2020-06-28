package com.rysh.module.clientTag.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("移动端标签对象")
@Data
public class ClientTag {
    @ApiModelProperty(name = "id",value = "标签id")
    private String id;
    @ApiModelProperty(name = "name",value = "标签名称")
    private String name;
}
