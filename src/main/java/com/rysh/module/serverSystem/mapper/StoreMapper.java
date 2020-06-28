package com.rysh.module.serverSystem.mapper;

import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.City;
import com.rysh.module.serverSystem.beans.Store;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreMapper {
    void addStore(Store store);

    List<Store> findAllStore();

    Store findStoreById(String storeId);

    void updateStore(Store store);

    void deleteStore(String storeId);

    List<Store> searchAllRole(String searchCondition);

    void updateCompany(String storeId);

    Area findAreaByAreaId(String areaId);

    City findCityByCityId(String cityId);

    Store findStoreByIdToOrders(String storeId);

    void addCommunityStore(@Param("id") String id, @Param("communityId") String communityId, @Param("storeId") String storeId);

    List<String> findCommunityIdByStoreId(String id);

    void deleteCommunityStore(String id);

    List<Store> findAllStorePlus(@Param("search") String search);
}
