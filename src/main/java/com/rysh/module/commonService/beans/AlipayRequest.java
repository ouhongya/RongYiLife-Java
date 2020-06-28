package com.rysh.module.commonService.beans;

import lombok.Data;

@Data
public class AlipayRequest {
    // 商户订单号，商户网站订单系统中唯一订单号，必填
    String out_trade_no;
    // 订单名称，必填
    String subject;
    // 付款金额，必填
    String total_amount;
    // 商品描述，可空
    String body;
    // 超时时间 可空
    String timeout_express="2m";
    // 销售产品码 必填
    String product_code="QUICK_WAP_WAY";
}
