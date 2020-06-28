package com.rysh.module.farm.mapper;

import com.rysh.module.farm.beans.FarmImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FarmImgMapper {
    int addImg(FarmImg img);

    int updateById(FarmImg img);

    //根据item_id 删除数据
    void dropByItemId(String farmItemId);

    List<String> findFarmImgUrlByItemId(String goodsId);

    List<String> findFarmBannerImgUrlByItemId(String goodsId);

    String findFarmDetailImgUrl(String goodsId);


}
