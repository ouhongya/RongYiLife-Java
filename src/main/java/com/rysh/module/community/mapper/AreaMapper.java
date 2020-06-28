package com.rysh.module.community.mapper;

import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.ClientArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaMapper {
    void addArea(Area area);

    List<Area> checkHasExist(@Param("cityName") String cityName,@Param("areaName") String areaName);

    String findIdByName(String areaName);

    List<Area> findAreaByCityId(String cityId);

    String findUserAreaByAddressId(String addressId);

    int updateAreaName(Area area);

    /**
     * 根据城市id 查询下面所有的区域
     * @param id
     * @return java.util.List<com.rysh.module.community.beans.ClientArea>
     * @author Hsiang Sun
     * @date 2019/10/16 18:19
     */
    List<ClientArea> findAllByCityId(String id);


    Area findAreaById(String areaId);
}
