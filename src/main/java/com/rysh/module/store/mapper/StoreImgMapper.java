package com.rysh.module.store.mapper;

import com.rysh.module.store.beans.StoreImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreImgMapper {

    /**
     * 新增商铺图片
     * @param imgobj
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/8 17:58
     */
    int addImg(StoreImg imgobj);

    /**
     * 通过itemID删除图片
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/9 9:53
     */
    void deleteByItemId(@Param("id") String id);

    List<String> findStoreImgUrlByItemId(String productItemId);

    List<String> findStoreBannerImgUrlByItemId(String goodsId);

    String findStoreDetailImgUrl(String goodsId);
}