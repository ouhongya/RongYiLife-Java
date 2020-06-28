package com.rysh.module.commonService.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.request.KoubeiTradeItemorderQueryRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.alipay.api.response.KoubeiTradeItemorderQueryResponse;
import com.rysh.module.commonService.beans.AlipayRequest;
import com.rysh.module.commonService.config.AlipayConfig;
import com.rysh.module.commonService.service.AlipayService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@Log4j2
public class AlipayServiceImpl implements AlipayService {

   AlipayClient client = new DefaultAlipayClient(
                                               AlipayConfig.URL,
                                               AlipayConfig.APPID,
                                               AlipayConfig.RSA_PRIVATE_KEY,
                                               AlipayConfig.FORMAT,
                                               AlipayConfig.CHARSET,
                                               AlipayConfig.ALIPAY_PUBLIC_KEY,
                                               AlipayConfig.SIGNTYPE
   );

    public AlipayTradeWapPayResponse pay(AlipayRequest request){

        try {
            AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
            // 封装请求支付信息
            AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
            model.setOutTradeNo(request.getOut_trade_no());
            model.setSubject(request.getSubject());
            model.setTotalAmount(request.getTotal_amount());
            model.setBody(request.getBody());
            model.setTimeoutExpress(request.getTimeout_express());
            model.setProductCode(request.getProduct_code());

            //设置请求内容
            alipay_request.setBizModel(model);
            // 设置异步通知地址
            alipay_request.setNotifyUrl(AlipayConfig.NOTIFY_URL);

            AlipayTradeWapPayResponse payResponse = client.sdkExecute(alipay_request);
            Field[] fields = payResponse.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                System.err.println("字段："+field +" 值:"+field.get(payResponse));
            }
            AlipayTradeWapPayResponse result = payResponse.isSuccess() ? payResponse : null;
            return result;
        } catch (AlipayApiException e) {
            //支付失败
            log.error("==调用支付宝支付接口失败==",e);
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String pay2(AlipayTradePagePayRequest request) {
        try {
            String body = client.pageExecute(request).getBody();
            return body;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public KoubeiTradeItemorderQueryResponse pay3(KoubeiTradeItemorderQueryRequest request) {
        try {
            KoubeiTradeItemorderQueryResponse body = client.execute(request);
            return body;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
