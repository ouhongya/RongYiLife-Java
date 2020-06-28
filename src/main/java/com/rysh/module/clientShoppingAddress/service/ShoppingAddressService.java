package com.rysh.module.clientShoppingAddress.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.clientShoppingAddress.beans.ShoppingAddress;
import com.rysh.module.commonService.beans.ParamBean;

import java.util.List;

public interface ShoppingAddressService {
    String addAddress(ShoppingAddress shoppingAddress) throws Exception;   //添加收货地址
    List<ShoppingAddress> findAllShoppingAddress(String userId, ParamBean paramBean) throws Exception;   //查询所有收货地址
    ShoppingAddress findShoppingAddressById(String shoppingAddressId) throws Exception;   //通过id查询收货地址    （编辑回显）
    Boolean deleteAddress(String shoppingAddressId,String uid) throws Exception;   //删除收货地址
    String updateAddress(ShoppingAddress shoppingAddress) throws Exception;   //编辑收货地址
}
