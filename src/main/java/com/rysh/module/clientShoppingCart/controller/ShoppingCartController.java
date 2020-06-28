package com.rysh.module.clientShoppingCart.controller;

import com.rysh.module.clientShoppingCart.beans.ShoppingCart;
import com.rysh.module.clientShoppingCart.service.ShoppingCartService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.utils.CheckRedisTokenUtils;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RequestMapping(value = "/client/ShoppingCart")
@RestController
@Api(description = "移动端购物车接口")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private CheckRedisTokenUtils checkRedisTokenUtils;
    /**
     * 往购物车中添加商品
     * @param
     * @return
     */
    @ApiOperation(value = "添加购物车",httpMethod = "POST",notes = "需要的参数：goodsId(商品id) goodsNum(商品数量) isInsider(商品类型（1=商铺，2=农场，3=农庄，4=自营）) token(token字符串)" +
           "如果是减少一个商品数量：  这里的goodsNum(商品数量) 就传-1")
    @PostMapping (value = "/addShoppingCart")
    public QueryResponseResult addShoppingCart(ShoppingCart shoppingCart){
                    try {
                        String uid = checkRedisTokenUtils.checkRedisToken(shoppingCart.getToken());
                        if(uid!=null){
                            shoppingCart.setUserId(uid);
                            String id = shoppingCartService.addShoppingCart(shoppingCart);
                            QueryResult<Object> result = new QueryResult<>();
                            result.setData(id);
                            return new QueryResponseResult(CommonCode.SUCCESS,result);
                        }else {
                            return new QueryResponseResult(CommonCode.TOKEN_ERROR);
                        }
                    }catch (Exception e){
                        log.error("添加购物车异常");
                        log.error(e);
                        return new QueryResponseResult(CommonCode.FAIL);
                    }
    }

    /**
     * 清空购物车
     * @param token
     * @return
     */
    @ApiOperation(value = "清空购物车（删除所有购物车信息）",httpMethod = "POST")
    @ApiImplicitParam(name = "token",value = "token")
    @PostMapping (value = "/emptyShoppingCart")
    public QueryResponseResult emptyShoppingCart(String token){

        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                shoppingCartService.emptyShoppingCartByUserId(uid);
                return new QueryResponseResult(CommonCode.SUCCESS);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }

        } catch (Exception e) {
           log.error("清空购物车异常");
           log.error(e);
           return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 删除购物车中的单个商品
     * @param id
     * @return
     */
    @ApiOperation(value = "删除购物车中的单个商品",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "购物车id"),
            @ApiImplicitParam(name = "token",value = "token")
    })
    @PostMapping(value = "/deleteShoppingCart")
    public QueryResponseResult deleteShoppingCart(String id,String token){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(shoppingCartService.deleteShoppingCartById(id, uid)){
                return new QueryResponseResult(CommonCode.SUCCESS);
            }else {
                return new QueryResponseResult(CommonCode.GET_OUT);
            }

        } catch (Exception e) {
            log.error("删除购物车异常");
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    /**
     * 查询所有购物车
     * @return
     */
    @ApiOperation(value = "查询该用户所有购物车",httpMethod = "POST",response = ShoppingCart.class)
    @ApiImplicitParam(name = "token",value = "token")
    @PostMapping(value = "/findAllShoppingCart")
    public QueryResponseResult findAllShoppingCart(String token, ParamBean paramBean){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                QueryResult<Object> result = new QueryResult<>();
                result.setData( shoppingCartService.findAllShoppingCart(uid,paramBean));
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }

        }catch (Exception e){
            log.error("查询所有购物车异常");
            log.error(e);
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }





}
