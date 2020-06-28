package com.rysh.module.clientOrders.beans;

import lombok.Data;

@Data
public class UserIdAndState {
    private String userId;  //用户id
    private Integer state;  //状态码
}
