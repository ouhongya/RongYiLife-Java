package com.rysh.module.grange.mapper;

import com.rysh.module.grange.beans.GrangeImg;

import java.util.List;

public interface GrangeImgMapper {

    int addImg(GrangeImg img);

    int updateById(GrangeImg img);

    //根据item_id 删除数据
    void dropByItemId(String farmItemId);

    List<String> findFarmImgUrlByItemId(String goodsId);

    List<String> findGrangeBannerImgUrl(String goodsId);

    String findGrangeDetailImgUrl(String goodsId);
}