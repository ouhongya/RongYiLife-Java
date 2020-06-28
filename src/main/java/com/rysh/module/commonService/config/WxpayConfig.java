package com.rysh.module.commonService.config;

import com.rysh.module.sdk.wxpay.IWXPayDomain;
import com.rysh.module.sdk.wxpay.WXPayConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Log4j2
@Component
public class WxpayConfig extends WXPayConfig {

    @Value("${wxpay.appId}")
    private String appId;

    @Value("${wxpay.mchId}")
    private String mchId;

    @Value("${wxpay.key}")
    private String key;

    @Value("${wxpay.body}")
    private String body;

    @Value("${wxpay.notify}")
    private String notify;

    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String s, long l, Exception e) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig wxPayConfig) {
                return new DomainInfo("api.mch.weixin.qq.com",true);
            }
        };
    }
    //支付页面展示的标题信息
    public String getBody(){
        return body;
    }
    //线上服务器
    public String getIp(){
        try {
           String ip = InetAddress.getLocalHost().getHostAddress();
           log.info("****************当前服务器ip地址【："+ip+"】*********************");
           return ip;
        } catch (UnknownHostException e) {
            log.error("获取当前服务器ip错误！！"+e);
            return null;
        }
    }
    //请求回调地址
    public String notifyUrl(){
        return notify;
    }
}
