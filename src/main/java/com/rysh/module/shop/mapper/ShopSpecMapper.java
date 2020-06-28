package com.rysh.module.shop.mapper;

import com.rysh.module.shop.beans.ShopSpec;

import java.util.List;

public interface ShopSpecMapper {

    /**
     * 新增store spec
     * @param storeSpec
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/8 17:55
     */
    int addSpec(ShopSpec storeSpec);

    /**
     * 更新storeSpec
     * @param storeSpec
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 9:59
     */
    int updateSpec(ShopSpec storeSpec);

    ShopSpec findShopSpecByItemId(String productItemId);

    List<ShopSpec> findShopSpecsByItemId(String id);

    ShopSpec findById(String productSpecId);
}