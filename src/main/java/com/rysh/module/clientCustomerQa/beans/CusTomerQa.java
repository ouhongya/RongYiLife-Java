package com.rysh.module.clientCustomerQa.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("问题答案对象")
@Data
public class CusTomerQa {
    @ApiModelProperty(name = "id",value = "问题id")
    private String id;
    @ApiModelProperty(name = "question",value = "问题")
    private String question;
    @ApiModelProperty(name = "answer",value = "答案")
    private String answer;
}
