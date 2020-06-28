package com.rysh.module.clientShoppingAddress.controller;

import com.rysh.module.clientShoppingAddress.beans.ShoppingAddress;
import com.rysh.module.clientShoppingAddress.service.ShoppingAddressService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.utils.CheckRedisTokenUtils;
import com.rysh.module.webSocket.WebSocket;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
@CrossOrigin
@Log4j2
@RequestMapping(value = "/client/shoppingAddress")
@RestController
@Api(description = "收货地址接口")
public class ShoppingAddressController {
    @Autowired
    private ShoppingAddressService shoppingAddressService;

    @Autowired
    private CheckRedisTokenUtils checkRedisTokenUtils;

    @Autowired
    private WebSocket webSocket;

    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 添加收货地址
     * @param shoppingAddress
     * @return
     */
    @ApiOperation(value = "添加收货地址",httpMethod = "POST",notes = "name(收货人姓名)，address（收货地址），phone（收货人电话），zipCode（邮政编码 可以没有），token  state(是否是默认收货地址   1默认   0不默认)\"")
    @PostMapping(value = "/addAddress")
    public QueryResponseResult addAddress(ShoppingAddress shoppingAddress){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(shoppingAddress.getToken());
            if(uid!=null){
                shoppingAddress.setUserId(uid);
                String god = shoppingAddressService.addAddress(shoppingAddress);
                if("滚".equals(god)){
                    return new QueryResponseResult(CommonCode.PHONE_ERROR);
                }else {
                    QueryResult<Object> result = new QueryResult<>();
                    result.setData(god);
                    return new QueryResponseResult(CommonCode.SUCCESS,result);
                }
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        } catch (Exception e) {
            log.error("添加收货地址异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 查询所有收货地址
     * @param token
     * @param paramBean
     * @return
     */
    @ApiOperation(value = "查询该用户所有收货地址",httpMethod = "POST",notes = "辅助参数 只需要给page和size")
    @ApiImplicitParam(name = "token",value = "token")
    @PostMapping(value = "/findAllShoppingAddress")
    public QueryResponseResult findAllShoppingAddress( String token, ParamBean paramBean){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                QueryResult<Object> result = new QueryResult<>();
                result.setData(shoppingAddressService.findAllShoppingAddress(uid,paramBean));
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("查询所有收货地址异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 通过id查询收货地址
     * @param shoppingAddressId
     * @return
     */
    @ApiOperation(value = "通过id查询收货地址（编辑回显）",httpMethod = "POST")
    @ApiImplicitParam(name = "shoppingAddressId",value = "收货地址Id")
    @PostMapping(value = "/findShoppingAddressById")
    public QueryResponseResult findShoppingAddressById(String shoppingAddressId){
        try {
            QueryResult<Object> result = new QueryResult<>();
            result.setData(shoppingAddressService.findShoppingAddressById(shoppingAddressId));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("通过id查询收货地址异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 删除收货地址
     * @param shoppingAddressId
     * @return
     */
    @ApiOperation(value = "删除收货地址",httpMethod = "POST")
    @ApiParam(name = "shoppingAddressId",value = "收货地址Id")
    @PostMapping(value = "/deleteAddress")
    public QueryResponseResult deleteAddress(String shoppingAddressId,String token){
            try {
                String uid = checkRedisTokenUtils.checkRedisToken(token);
                if(uid!=null){
                    if(shoppingAddressService.deleteAddress(shoppingAddressId, uid)){
                        return new QueryResponseResult(CommonCode.SUCCESS);
                    }else {
                        return new QueryResponseResult(CommonCode.GET_OUT);
                    }
                }else {
                    return new QueryResponseResult(CommonCode.TOKEN_ERROR);
                }
            }catch (Exception e){
                log.error("删除收货地址异常");
                log.error(e);
                log.error(sf.format(new Date()));
                return new QueryResponseResult(CommonCode.SERVER_ERROR);
            }
    }

    /**
     * 编辑收货地址
     * @param shoppingAddress
     * @return
     */
    @ApiOperation(value = "编辑收货地址",httpMethod = "POST",notes = "name(收货人名称)，address(收货地址)，phone(收货人电话)，zipCode(邮政编码  可以没有) ，id(收货地址id)  token  state(是否是默认收货地址   1默认   0不默认)")
    @PostMapping(value = "/updateAddress")
    public QueryResponseResult updateAddress(ShoppingAddress shoppingAddress){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(shoppingAddress.getToken());
            if(uid==null){
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }else {
                shoppingAddress.setUserId(uid);
                String god = shoppingAddressService.updateAddress(shoppingAddress);
                if("来".equals(god)){
                    return new QueryResponseResult(CommonCode.SUCCESS);
                }
                if("爬".equals(god)){
                    return new QueryResponseResult(CommonCode.GET_OUT);
                }
                if("滚".equals(god)){
                    return new QueryResponseResult(CommonCode.PHONE_ERROR);
                }
                return new QueryResponseResult(CommonCode.SERVER_ERROR);
            }
        }catch (Exception e){
            log.error("编辑收货地址异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    @GetMapping("/aaabbb")
    public String aaabbb(String message,String uid){
        webSocket.sendMsg(uid,message);
        return "我姓虾 虾悟净的虾";
    }
}
