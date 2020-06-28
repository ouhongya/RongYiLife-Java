package com.rysh.module.shop.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.DisplayInfo;
import com.rysh.module.shop.beans.Shop;

public interface ShopService {

    /**
     * 查询当前用户的商铺信息
     * @param login
     * @return com.rysh.module.farm.beans.FarmInfo
     * @author Hsiang Sun
     * @date 2019/10/8 16:49
     */
    DisplayInfo grangeInfo(String login);

    /**
     * 商铺的搜索
     * @param param
     * @return com.github.pagehelper.PageInfo<com.rysh.module.store.beans.Store>
     * @author Hsiang Sun
     * @date 2019/10/8 17:14
     */
    PageInfo<Shop> search(ParamBean param);

    /**
     * 查询当前用户的商品信息
     * @param login
     * @return com.rysh.module.store.beans.Store
     * @author Hsiang Sun
     * @date 2019/10/9 10:53
     */
    Shop getStoreByLogin(String login);

    /**
     * 新增自营商城，如果已经存在就不添加
     * @param grange
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 16:50
     */
    int addNewShop(Shop grange);

    /**
     * 查询当前商城的数量该结果恒等于1
     * @param
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 16:51
     */
    int shopCount();

    /**
     * 数据回显
     * @param id
     * @return com.rysh.module.shop.beans.Shop
     * @author Hsiang Sun
     * @date 2019/10/9 16:54
     */
    Shop shopById(String id);

    /**
     * 更新自营商城信息
     * @param grange
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 16:55
     */
    int updateShop(Shop grange);

    /**
     * 删除自营商城(可能不会实现)
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 16:57
     */
    int deleteShop(String id);
}
