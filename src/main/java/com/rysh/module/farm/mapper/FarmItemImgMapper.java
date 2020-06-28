package com.rysh.module.farm.mapper;

import org.apache.ibatis.annotations.Param;

public interface FarmItemImgMapper {
    /*
     * 向联合主键表里面添加信息
     * @param farmItemId
     * @param farmImgId
     * @return void
     * @author Hsiang Sun
     * @date 2019/9/6 11:38
     */
    void addDate(@Param("farmItemId") String farmItemId , @Param("farmImgId") String farmImgId);
}
