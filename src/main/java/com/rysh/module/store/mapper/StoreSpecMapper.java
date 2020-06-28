package com.rysh.module.store.mapper;

import com.rysh.module.store.beans.StoreSpec;

import java.util.List;

public interface StoreSpecMapper {

    /**
     * 新增store spec
     * @param storeSpec
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/8 17:55
     */
    int addSpec(StoreSpec storeSpec);

    /**
     * 更新storeSpec
     * @param storeSpec
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 9:59
     */
    int updateSpec(StoreSpec storeSpec);

    StoreSpec findStoreSpecByItemId(String productItemId);

    List<StoreSpec> findStoreSpecsByItemId(String productItemId);

    StoreSpec findById(String productSpecId);
}