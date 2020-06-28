package com.rysh.module.clientSearch.service;

import com.rysh.module.clientSearch.beans.*;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.commonService.beans.ShopBean;
import com.rysh.module.serverCompanyRanking.beans.CompanyRankF;
import com.rysh.module.store.beans.StoreTag;

import java.util.List;

public interface SearchService {
    List<ShopBean> freeSearch(ParamBean paramBean, String cityId , String areaId, String tagId, Integer state, Integer belongType) throws Exception;

    SuperProductItem goodsFreeSearch(ParamBean paramBean, String marketId, Integer belongType, Integer state, String categoryId) throws Exception;

    ProductItem findGoodsDetailByGoodsId(String goodsId, Integer belongType) throws Exception;

    List<ProductItem> searchShopGoods(ParamBean paramBean,Integer state,String categoryId) throws Exception;


    List<ShopBean> searchStore(String uid, ParamBean paramBean, Integer state,String tagId) throws Exception;

    BannerAndCategory getNfsjBaseInfos() throws Exception;

    List<Type> getConvenientCommunityType() throws Exception;


    List<StoreTag> findStoreTag(String uid) throws Exception;

    List<ProductItem> searchAllGoods(String search,String uid);

    CompanyRankF findCompanyRanking();
}
