package com.rysh.module.clientShoppingCart.service;

import com.rysh.module.clientShoppingCart.beans.ShoppingCart;
import com.rysh.module.commonService.beans.ParamBean;

import java.util.List;

public interface ShoppingCartService {
//添加购物车
    String addShoppingCart(ShoppingCart shoppingCart) throws Exception;
//清空购物车
    void emptyShoppingCartByUserId(String userId) throws Exception;
//删除购物车中的单个商品
    Boolean deleteShoppingCartById(String id,String uid) throws Exception;
//查询所有购物车
List<ShoppingCart> findAllShoppingCart(String userId, ParamBean paramBean) throws Exception;
}
