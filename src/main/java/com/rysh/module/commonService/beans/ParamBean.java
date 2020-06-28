package com.rysh.module.commonService.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("辅助参数")
@Data
public class ParamBean {
    @ApiModelProperty(name = "page",value = "当前页数")
    private int page;   //当前页
    @ApiModelProperty(name = "size",value = "每页显示条数")
    private int size;    //每页显示条数
    @ApiModelProperty(name = "search",value = "搜索条件")
    private String search;    //搜索条件
    @ApiModelProperty(name = "sortByFiled",value = "需要排序的数据库字段")
    private String sortByFiled;    //数据库字段
    @ApiModelProperty(name = "sortByOrder",value = "排序方式  asc 升序  desc 倒序")
    private String sortByOrder;   //排序
}
