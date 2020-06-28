package com.rysh.module.activity.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TimeParam {
    @ApiModelProperty(name = "page",value = "当前页数")
    private int page;   //当前页
    @ApiModelProperty(name = "size",value = "每页显示条数")
    private int size;    //每页显示条数
    @ApiModelProperty(name = "sortByFiled",value = "需要排序的数据库字段")
    private String sortByFiled;    //数据库字段
    @ApiModelProperty(name = "sortByOrder",value = "排序方式  asc 升序  desc 倒序")
    private String sortByOrder;   //排序
    @ApiModelProperty(name = "startTime",value = "查询的开始时间")
    private String startTime;    //开始时间
    @ApiModelProperty(name = "endTime",value = "查询的结束时间")
    private String endTime;    //开始时间
}
