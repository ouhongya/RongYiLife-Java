package com.rysh.module.classroom.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoVo {
    @ApiModelProperty(name = "id",value = "主键",required = false)
    private String id;

    @ApiModelProperty(name = "title",value = "视频标题",required = true)
    private String title;

    @ApiModelProperty(name = "intros",value = "视频简介",required = true)
    private String intros;

    @ApiModelProperty(name = "url",value = "视频url",required = true)
    private String url;

    @ApiModelProperty(name = "defaultSort",value = "默认排序大小")
    private int defaultSort;
}
