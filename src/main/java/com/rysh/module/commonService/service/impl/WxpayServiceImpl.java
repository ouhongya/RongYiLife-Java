package com.rysh.module.commonService.service.impl;


import com.alibaba.fastjson.JSON;
import com.rysh.module.clientOrders.beans.Orders;
import com.rysh.module.clientOrders.mapper.OrdersMapper;
import com.rysh.module.commonService.beans.WxpayRequest;
import com.rysh.module.commonService.config.WxpayConfig;
import com.rysh.module.commonService.service.WxpayService;
import com.rysh.module.sdk.wxpay.WXPayRequest;
import com.rysh.module.sdk.wxpay.WXPayUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Log4j2
@Service
public class WxpayServiceImpl implements WxpayService {

    @Autowired
    private WxpayConfig config;

    @Autowired
    private OrdersMapper ordersMapper;

    @Value("${result.ordersExpirationTime}")
    private Integer ordersExpirationTime;

    //随机字符串
    private String randomStr = WXPayUtil.generateNonceStr();

    public String makePreOrder(WxpayRequest request){

        //先去判断金额是否
        Orders order = ordersMapper.findOrdersByOrderId(request.getOrderId());
        if (order == null){
            throw new RuntimeException("错误的订单id");
        }
        //检查订单是否已经过期
        if(order.getState()!= 1 || order.getCreatedTime().getTime()+(ordersExpirationTime*1000) < new Date().getTime()){
            log.warn("当前订单{}已经过期不能继续支付",order.getId());
            throw new RuntimeException("订单已过期 不能继续支付");
        }



        BigDecimal realPay = order.getRealPay();
        BigDecimal decimalAmount = new BigDecimal(request.getAmount());
        if (realPay.compareTo(decimalAmount) != 0){
            log.error("金额发生错误啦！ 订单金额 = {} 请求金额 = {}",realPay,decimalAmount);
            throw new RuntimeException("调用微信支付时 金额出现偏差");
        }
        //将元角分 转换成分
        BigDecimal totalMoney = decimalAmount.multiply(new BigDecimal(100));
        //舍弃小数位 取整并转为String
        String Money = totalMoney.setScale(0, BigDecimal.ROUND_UNNECESSARY).toString();
        //订单信息
        String orderId = request.getOrderId();
        //响应结果
        String jsonResult = null;
        try {
            //1.封装请求参数
            Map<String,String> map = new HashMap<>();
            map.put("appid", config.getAppID());//公众账号ID
            map.put("mch_id",config.getMchID());//商户号
            map.put("nonce_str", randomStr);//随机字符串
            map.put("body",config.getBody());//商品描述
            map.put("out_trade_no",orderId);//订单号
            map.put("total_fee",Money);//金额
            map.put("spbill_create_ip",config.getIp());//终端IP
            map.put("notify_url",config.notifyUrl());//回调地址
            map.put("trade_type","APP");//交易类型
            //2.构建请求
            String paramXml = WXPayUtil.generateSignedXml(map, config.getKey());
            //创建请求发起体并发送请求
            WXPayRequest wxPayRequest = new WXPayRequest(config);
            //设置编码格式防止乱码
            String wxRequestXml = new String(paramXml.getBytes(), StandardCharsets.UTF_8);
            String responseXml = wxPayRequest.requestWithCert("/pay/unifiedorder", null, wxRequestXml, false);
            //3.解析结果
            Map<String, String> responseMap = WXPayUtil.xmlToMap(responseXml);
            //4.封装给APP的响应
            SortedMap resultMap = new TreeMap();
            resultMap.put("appid",config.getAppID());
            resultMap.put("prepayid",responseMap.get("prepay_id"));
            resultMap.put("noncestr",randomStr);
            resultMap.put("timestamp",Long.toString(WXPayUtil.getCurrentTimestamp()));
            resultMap.put("partnerid",config.getMchID());
            resultMap.put("package","Sign=WXPay");
            //5.结果重新签名
            String signedXml = WXPayUtil.generateSignedXml(resultMap, config.getKey());
            //6.将结果转换为json
            Map<String, String> xmlMap = WXPayUtil.xmlToMap(signedXml);
            jsonResult = JSON.toJSONString(xmlMap);
            return jsonResult;
        } catch (Exception e) {
            log.error("==微信支付调用失败==",e);
        }
        return jsonResult;
    }
}
