package com.rysh.module.serverCompanyRanking.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@ApiModel("公益之星子")
@Data
public class CompanyRankS {
    @ApiModelProperty(name = "id",value = "主键")
    private String id;
    @ApiModelProperty(name = "sequence",value = "排名")
    private Integer sequence;
    @ApiModelProperty(name = "name",value = "名字")
    private String name;
    @ApiModelProperty(name = "url",value = "头像")
    private String url;
    @ApiModelProperty(name = "createdTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @ApiModelProperty(name = "intro",value = "规则")
    private String intro;
    @ApiModelProperty(name = "imgUrl",value = "封面图")
    private String imgUrl;

    private String rankingCompanyId;
}
