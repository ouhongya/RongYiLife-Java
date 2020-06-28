package com.rysh.module.pay.controller.serverController;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.KoubeiTradeItemorderQueryRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.alipay.api.response.KoubeiTradeItemorderQueryResponse;
import com.rysh.module.commonService.beans.AlipayRequest;
import com.rysh.module.commonService.config.AlipayConfig;
import com.rysh.module.commonService.service.AlipayService;
import com.rysh.module.commonService.service.impl.AlipayServiceImpl;
import com.rysh.module.pay.beans.AlipayVo;
import com.rysh.module.utils.GenerateUUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Api(description = "支付宝支付接口回调接口")
@RestController
@RequestMapping("/server/alipay")
@Log4j2
public class ServerAlipayController {

    @Autowired
    private AlipayService service;

    @GetMapping("/pay")
    public String alipay(){
        AlipayVo vo = new AlipayVo();
        vo.setOut_trade_no(GenerateUUID.create());
        vo.setSubject("支付宝支付测试标题");
        vo.setTotal_amount("0.01");
        vo.setBody("支付宝支付测试内容");
        vo.setTimeout_express("90m");
        vo.setProduct_code("FAST_INSTANT_TRADE_PAY");
        String json = JSON.toJSONString(vo);
        System.err.println(json);

        
        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        alipayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);
        //alipayRequest.setBizContent(json);
        alipayRequest.setBizContent(json);

        String result = service.pay2(alipayRequest);
        System.err.println("响应结果===>"+result);
        return result; //这里生成一个表单，会自动提交


        //不能删除！！！！！！
        /*AlipayTradeWapPayResponse pay = service.pay(alipayRequest);
        String body = pay.getBody();
        log.warn("这里是支付宝回调内容{}",body);
        return body;*/


    }


    @GetMapping("/return")
    @ApiOperation("支付宝支付同步回调接口")
    public void callBack(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                log.error("出现乱码");
            }
            params.put(name, valueStr);
        }
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY,"utf-8", "RSA2");
            if (!signVerified){
                log.error("支付宝验证签名未通过");
                System.err.println("签名验证失败！！");
                return;//终止程序
            }else {
                System.err.println("成功完成支付");

                Enumeration<String> parameterNames = request.getParameterNames();
                for(Enumeration e= parameterNames;e.hasMoreElements();){

                    String thisName=e.nextElement().toString();
                    String thisValue=request.getParameter(thisName);
                    System.out.println(thisName+"--------------"+thisValue);

                }

                String out_trade_no = request.getParameter("out_trade_no");
                String total_amount = request.getParameter("total_amount");
                String trade_no = request.getParameter("trade_no");



                //签名验证通过 继续执行代码

                /*//商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //交易状态
                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");*/


            }
        } catch (AlipayApiException e) {
            log.error("支付宝回调签名验证失败！"+e);
        }

        //修改订单
        log.warn("======{}成功执行了支付宝支付回调方法",new Date()+"======");
    }

    @GetMapping("/query")
    public String queryOrder(){
        String trade_no = "2019110122001414771000077397";
        String out_trade_no = "35ECD1354EC34077BCA074A5E2CD0796";
        // 设置请求参数
        KoubeiTradeItemorderQueryRequest alipayRequest = new KoubeiTradeItemorderQueryRequest();
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","+"\"trade_no\":\""+ trade_no +"\"}");

        KoubeiTradeItemorderQueryResponse koubeiTradeItemorderQueryResponse = service.pay3(alipayRequest);
        boolean b = koubeiTradeItemorderQueryResponse.isSuccess();
        if (b){
            System.err.println("成功调用"+koubeiTradeItemorderQueryResponse.toString());
            return "success";
        }else {
            System.err.println("调用失败"+koubeiTradeItemorderQueryResponse.toString());
            return "false";
        }
    }
}
