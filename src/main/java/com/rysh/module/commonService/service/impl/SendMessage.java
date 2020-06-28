package com.rysh.module.commonService.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SendMessage {
    //云通信短信API产品   写死  不用更改
    private static final  String product="Dysmsapi";
    //云通信产品域名    写死 不用更改
    private static final  String domain="dysmsapi.aliyuncs.com";
    //申请的AKId
    private static String accessKeyId;
    //申请的AKSecret
    private static String accessKeySecret;
    //签名
    private static String signer;
    //验证码模板id
    private static String codeTemplateId;
    //提醒发货模板id
    private static String msgTemplateId;

    /**
     * 发送短信方法(验证码)
     * @param phoneNum  电话号码
     * @param code    验证码
     * @throws ClientException
     */
    public static String sendCode(String phoneNum,String code) throws ClientException {
        log.info("开始发送短信");
        //初始化ascClient  发送短信对象
        IClientProfile iClientProfile = DefaultProfile.getProfile("cn-chengdu", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-chengdu","cn-chengdu",product,domain);
        DefaultAcsClient acsClient = new DefaultAcsClient(iClientProfile);

        //创建短信信息对象
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        //设置需要发送的电话号码
        sendSmsRequest.setPhoneNumbers(phoneNum);
        //设置签名
        sendSmsRequest.setSignName(signer);
        //设置模板id
        sendSmsRequest.setTemplateCode(codeTemplateId);
        //设置模板参数
        sendSmsRequest.setTemplateParam("{\"code\":\""+code+"\"}");

        //发送短信  发送失败会抛异常
        SendSmsResponse acsResponse = null;
        try {
             acsResponse = acsClient.getAcsResponse(sendSmsRequest);
        }catch (Exception e){
            log.error("短信发送异常");
            log.error(e.getMessage());
            log.error(acsResponse.getCode());
        }
        return acsResponse.getCode();
    }



    /**
     * 发送短信方法(提醒发货)
     * @param phoneNum  电话号码
     * @param ordersNum    订单号
     * @throws ClientException
     */
    public static String sendMsg(String phoneNum,String ordersNum) throws ClientException {
        log.info("开始发送短信");
        //初始化ascClient  发送短信对象
        IClientProfile iClientProfile = DefaultProfile.getProfile("cn-chengdu", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-chengdu","cn-chengdu",product,domain);
        DefaultAcsClient acsClient = new DefaultAcsClient(iClientProfile);

        //创建短信信息对象
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        //设置需要发送的电话号码
        sendSmsRequest.setPhoneNumbers(phoneNum);
        //设置签名
        sendSmsRequest.setSignName(signer);
        //设置模板id
        sendSmsRequest.setTemplateCode(msgTemplateId);
        //设置模板参数
        sendSmsRequest.setTemplateParam("{\"code\":\""+ordersNum+"\"}");

        //发送短信  发送失败会抛异常
        SendSmsResponse acsResponse = null;
        try {
            acsResponse = acsClient.getAcsResponse(sendSmsRequest);
        }catch (Exception e){
            log.error("短信发送异常");
            log.error(e.getMessage());
            log.error(acsResponse.getCode());
        }
        return acsResponse.getCode();
    }

    @Value("${result.accessKeyId}")
    public  void setAccessKeyId(String accessKeyId) {
        SendMessage.accessKeyId = accessKeyId;
    }
    @Value("${result.accessKeySecret}")
    public  void setAccessKeySecret(String accessKeySecret) {
        SendMessage.accessKeySecret = accessKeySecret;
    }
    @Value("${result.signer}")
    public  void setSigner(String signer) {
        SendMessage.signer = signer;
    }
    @Value("${result.codeTemplateId}")
    public  void setTemplateId(String codeTemplateId) {
        SendMessage.codeTemplateId = codeTemplateId;
    }
    @Value("${result.msgTemplateId}")
    public  void setMsgTemplateId(String msgTemplateId) {
        SendMessage.msgTemplateId = msgTemplateId;
    }
}
