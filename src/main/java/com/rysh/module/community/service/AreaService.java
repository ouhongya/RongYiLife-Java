package com.rysh.module.community.service;

import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.ClientArea;

import java.util.List;

public interface AreaService {

    /**
     * 添加区域
     * @param cityName
	 * @param areaName
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/19 13:32
     */
     void addArea(String cityName,String areaName);

     /**
      * 检查城市下面是否已经有该区存在
      * @param cityName
	  * @param areaName
      * @return boolean
      * @author Hsiang Sun
      * @date 2019/10/19 13:04
      */
     boolean checkHasExist(String cityName, String areaName);
     String getAreaId(String areaName);
     List<Area> getAllAreaByCityId(String cityId);

    /**
     * 更新区域信息
     * @param area
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/18 18:12
     */
    public int updateAreaName(Area area);

    /**
     * 根据城市id查询下面所有区域
     * @param id
     * @return java.util.List<com.rysh.module.community.beans.ClientArea>
     * @author Hsiang Sun
     * @date 2019/10/16 18:18
     */
    List<ClientArea> getAreaByCityId(String id);
}
