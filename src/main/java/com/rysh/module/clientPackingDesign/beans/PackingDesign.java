package com.rysh.module.clientPackingDesign.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("社群定制")
@Data
public class PackingDesign {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "cover",value = "封面图")
    private String cover;
    @ApiModelProperty(name = "title",value = "标题")
    private String title;
}
