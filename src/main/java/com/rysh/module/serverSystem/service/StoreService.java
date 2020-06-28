package com.rysh.module.serverSystem.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.Store;

public interface StoreService {
//添加商铺
    boolean addStore(Store store) throws Exception;
//查询所有商铺
    PageInfo<Store> findAllStore(ParamBean paramBean) throws Exception;
//通过id查询商铺   （编辑商铺回显）
    Store findStoreById(String storeId) throws Exception;
//编辑商铺基本信息
    boolean updateStore(Store store) throws Exception;
//删除商铺  并断开和用户的外键关系
    boolean deleteStore(String storeId) throws Exception;
//按条件搜索商铺
    PageInfo<Store> searchAllStore(ParamBean paramBean);


}
