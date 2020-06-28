package com.rysh.module.serverCustomerQa.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("问答对象")
@Data
public class CustomerQaPlus {
    @ApiModelProperty(name = "id",value = "主键")
    private String id;
    @ApiModelProperty(name = "question",value = "问题")
    private String question;
    @ApiModelProperty(name = "answer",value = "答案")
    private String answer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "createdTime",value = "问答创建时间")
    private Date createdTime;
    @ApiModelProperty(name = "defaultSort",value = "排序字段  越大 越签前面")
    private Integer defaultSort;
    @ApiModelProperty(name = "status",value = "状态值  0未发布  1发布")
    private Integer status;
}
