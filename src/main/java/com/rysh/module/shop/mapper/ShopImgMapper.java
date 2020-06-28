package com.rysh.module.shop.mapper;

import com.rysh.module.shop.beans.ShopImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopImgMapper {

    /**
     * 新增商铺图片
     * @param imgobj
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/8 17:58
     */
    int addImg(ShopImg imgobj);

    /**
     * 通过itemID删除图片
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/9 9:53
     */
    void deleteByItemId(@Param("id") String id);

    List<String> findShopImgUrlByItemId(String productItemId);

    List<String> findShopBannerImgUrlByItemId(String goodsId);

    String findShopDetailImgUrl(String goodsId);
}