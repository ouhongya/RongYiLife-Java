package com.rysh.module.pay.controller.serverController;

import com.rysh.module.clientOrders.beans.Orders;
import com.rysh.module.clientOrders.mapper.OrdersMapper;
import com.rysh.module.clientOrders.service.OrdersService;
import com.rysh.module.commonService.config.WxpayConfig;
import com.rysh.module.pay.beans.PayOrderError;
import com.rysh.module.pay.beans.PaySerial;
import com.rysh.module.pay.mapper.PaySerialMapper;
import com.rysh.module.sdk.wxpay.WXPayUtil;
import com.rysh.module.utils.GenerateUUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Api(description = "微信支付接口回调接口")
@RestController
@RequestMapping("/pay/wxpay")
@Log4j2
public class ServerWxpayController {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private WxpayConfig wxpayConfig;

    @Autowired
    private PaySerialMapper paySerialMapper;

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/callback110")
    @ApiOperation("微信支付回调接口")
    public String  callBack(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String result = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[FALSE]]></return_msg></xml>";//返回给微信的处理结果
        String inputLine;
        String notityXml = "";
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        //微信给返回的东西
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            request.getReader().close();
        } catch (Exception e) {
            e.printStackTrace();
            result = setXml("FAIL","xml获取失败");
        }
        if (StringUtils.isEmpty(notityXml)) {
            result = setXml("FAIL","xml为空");
        }

        log.info("微信回调内容 \n{}",notityXml);

        Map<String, String> callBackMap = null;
        try {
            callBackMap = WXPayUtil.xmlToMap(notityXml);
        } catch (Exception e) {
            log.error("将微信回调xml->map错误" +e);
            e.printStackTrace();
        }

        String transaction_id = null;
        try {

            if (!"SUCCESS".equals(callBackMap.get("return_code"))){
                log.error("微信支付回调失败！回调内容：\n {}",notityXml);
                result = setXml("FAIL","接口返回的结果失败");
                return result;
            }

            //验证签名
            boolean signatureValid = WXPayUtil.isSignatureValid(callBackMap, wxpayConfig.getKey());
            if (!signatureValid){
                //签名未通过
                log.error("微信支付回调签名验证失败! ！回调内容：\n {}",notityXml);
                result = setXml("FAIL","微信支付回调签名验证失败");
                return result;
            }

            //验证金额是否一致
            String total_fee = callBackMap.get("total_fee");
            BigDecimal bigDecimalFree = new BigDecimal(total_fee);
            BigDecimal decimalRMB = bigDecimalFree.divide(new BigDecimal(100));//将单位由分变成元
            String orderId = callBackMap.get("out_trade_no");
            String time_end = callBackMap.get("time_end");
            transaction_id = callBackMap.get("transaction_id");

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            Date payTime = format.parse(time_end);
            Orders order = ordersMapper.findOrdersByOrderId(orderId);
            BigDecimal realPay = order.getRealPay();

            //记录支付日志
            PaySerial paySerial = new PaySerial();
            paySerial.setId(GenerateUUID.create());
            paySerial.setOrdersId(orderId);
            paySerial.setPayTime(payTime);
            paySerial.setOrdresFee(realPay);
            paySerial.setPayWay(0);//微信支付
            paySerial.setPayFee(decimalRMB);
            paySerial.setCreatedTime(new Date());
            paySerial.setTransactionId(transaction_id);
            paySerial.setState(1);//金额匹配
            paySerial.setCause(null);
            if ( decimalRMB.compareTo(realPay) != 0){
                log.error("微信支付金额{}和数据库金额{}有差异",decimalRMB,realPay);
                paySerial.setState(0);//金额不匹配
                paySerial.setCause("于北京时间"+new Date()+"发生了 微信支付金额和数据库金额不一致的情况" );
                result  = setXml("FAIL", "微信支付金额于数据库金额不一致");
                return result;
            }
            paySerialMapper.addPaySerial(paySerial);
            result = setXml("SUCCESS","OK");
            //修改订单
            try {
                ordersService.updateState(orderId,0);
            } catch (Exception e) {
                log.error("微信支付完成但是修改订单出现错误 请查看 [pay_orders_errors] "+e);
                PayOrderError payOrderError = new PayOrderError();
                payOrderError.setId(GenerateUUID.create());
                payOrderError.setOrdersId(orderId);
                payOrderError.setTransactionId(transaction_id);
                payOrderError.setTradeTime(payTime);
                payOrderError.setPayWay(0);//微信支付
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }finally {
            System.err.println("----返回给微信的xml：" + result);
            log.info("[交易订单{}]进入回调 返回给微信的结果是{}",transaction_id,result);
            return result;
        }
    }

    //通过xml 发给微信消息
    public static String setXml(String return_code, String return_msg) {
        SortedMap<String, String> parameters = new TreeMap<String, String>();
        parameters.put("return_code", return_code);
        parameters.put("return_msg", return_msg);
        return "<xml><return_code><![CDATA[" + return_code + "]]>" +
                "</return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }


}
