package com.rysh.module.clientSearch.mapper;

import com.rysh.module.clientSearch.beans.Banner;
import com.rysh.module.clientSearch.beans.ProductItem;
import com.rysh.module.clientSearch.beans.Type;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.commonService.beans.ShopBean;
import com.rysh.module.farm.beans.FarmItem;
import com.rysh.module.grange.beans.GrangeItem;
import com.rysh.module.serverSystem.beans.Store;
import com.rysh.module.shop.beans.ShopItem;
import com.rysh.module.store.beans.StoreItem;
import com.rysh.module.store.beans.StoreTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchMapper {
    List<ShopBean> freeSearch(@Param("search") String search, @Param("cityId") String cityId, @Param("areaId") String areaId, @Param("state") Integer state, @Param("tagId")String tagId, @Param("tableName") String tableName);

    List<FarmItem> searchFarmItem(@Param("search") String search, @Param("marketId") String marketId,@Param("state") Integer state,@Param("categoryId") String categoryId);

    List<GrangeItem> searchGrangeItem(@Param("search") String search,@Param("marketId") String marketId,@Param("state") Integer state,@Param("categoryId") String categoryId);

    List<ShopItem> searchShopItem(@Param("paramBean") ParamBean paramBean,@Param("state") Integer state,@Param("categoryId") String categoryId);

    List<StoreItem> searchStoreItem(@Param("search") String search,@Param("marketId") String marketId,@Param("state") Integer state,@Param("categoryId") String categoryId);

    List<Store> searchStore(@Param("communityId") String communityId, @Param("search") String search, @Param("state") Integer state,@Param("tagId") String tagId);

    List<Banner> findAllShopBanner();

    List<Type> findAllShopCategory(String shopId);

    Type findConvenientCommunityType();

    List<StoreTag> findStoreTagByCommunityId(String communityId);

    List<ProductItem> searchAllGoods(@Param("search") String search, @Param("communityId") String communityId);
}
