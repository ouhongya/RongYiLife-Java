package com.rysh.module.serverCompanyRanking.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
@ApiModel("公益之星父")
@Data
public class CompanyRankF {
    @ApiModelProperty(name = "id",value = "主键")
    private String id;
    @ApiModelProperty(name = "url",value = "排行榜封面图")
    private String url;
    @ApiModelProperty(name = "intro",value = "排行榜介绍")
    private String intro;
    @ApiModelProperty(name = "createdTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    private String operator;
    @ApiModelProperty(name = "companyRankSs",value = "上了排行榜的人")
    private List<CompanyRankS> companyRankSs;
    @ApiModelProperty(name = "status",value = "状态  0过期   1展示")
    private Integer status;
}
