package com.rysh.module.utils;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Log4j2
public class TokenUtils {

    /**
     * 密钥
     */

    private static final byte[] key="蓉意生活，天下第一，千秋万代，唯我独尊".getBytes();

    /**
     * token加密算法
     */
    private static final JWSHeader header=new JWSHeader(JWSAlgorithm.HS256);

    private static HttpUtils httpUtils=new HttpUtils();
    /**
     * 创建token
     * @param payload
     * @return
     */
    public static String createToken(Map<String, Object> payload) {
        // 创建一个 JWS object
        JWSObject jwsObject = new JWSObject(header, new Payload(new JSONObject(payload)));
        String tokenStr = "";
        try {
            // 将jwsObject 进行HMAC签名
            jwsObject.sign(new MACSigner(key));
            tokenStr = jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("token签名异常");
            log.error(e);
            log.error(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        return tokenStr;
    }
    public static Map<String, Object> checkToken(String token) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
              try {
                      JWSObject jwsObject = JWSObject.parse(token);
                      Payload payload = jwsObject.getPayload();
                      JWSVerifier verifier = new MACVerifier(key);
                  if (jwsObject.verify(verifier)) {
                             JSONObject jsonOBj = payload.toJSONObject();
                            // token校验成功（此时没有校验是否过期）
                             resultMap.put("state", "校验成功");
                           if (jsonOBj.containsKey("ext")) {
                                      long extTime = Long.valueOf(jsonOBj.get("ext").toString());
                                  long curTime = new Date().getTime();
                                    // 过期了
                                   if (curTime > extTime) {
                                          resultMap.clear();
                                            resultMap.put("state", "token过期");
                                        }
                                 }
                               resultMap.put("data", jsonOBj);
                            } else {
                              // 校验失败
                              resultMap.put("state", "校验失败");
                        }

                } catch (Exception e) {
                      //e.printStackTrace();
                    // token格式不合法导致的异常
                    resultMap.clear();
                     resultMap.put("state", "格式异常");
                   }
               return resultMap;
          }


    /**
     * 在token里面新添加一个itemId属性  并塞进响应头中
     * @param itemId
     */
    public static void updateToken(String itemId){
        //创建获取request response工具类
              HttpUtils httpUtils = new HttpUtils();
              //获取request
              HttpServletRequest request = httpUtils.getRequest();
              //获取response
              HttpServletResponse response = httpUtils.getResponse();
              //通过request获取cookie
              Cookie[] cookies = request.getCookies();
              String token = "";
              if (cookies == null || cookies.length <= 0) {

              } else {
                  for (Cookie cookie : cookies) {
                      //从cookie中拿到token
                      if ("Token".equals(cookie.getName())) {
                          token = cookie.getValue().trim();
                      }
                  }
              }
              Map<String, Object> tokenMap = checkToken(token);
              Map dataMap = (Map) tokenMap.get("data");
              dataMap.put("itemId",itemId);
                 //创建新的token
              String token1 = createToken(dataMap);
                //将token塞进响应头
              response.setHeader("token", token1);
          }


    /**
     * 获取token中的isInsider
      * @return
     */
    public static Integer getIsInsiderByToken(){
        //通过工具类获取request
           HttpServletRequest request = httpUtils.getRequest();
           //在request中获取cookie
           Cookie[] cookies = request.getCookies();
           //遍历cookie中的数据
           //tokenMap初始化
           Map<String,Object> tokenMap=new HashMap<>();
           for (Cookie cookie : cookies) {
               if("Token".equals(cookie.getName())){
                    tokenMap = checkToken(cookie.getValue());
               }
           }
           //获取tokenMap中的data
           Map data = (Map) tokenMap.get("data");
           return (Integer) data.get("isInsider");
       }

    /**
     * 校验加密token
     * @param token
     * @return
     */


}
