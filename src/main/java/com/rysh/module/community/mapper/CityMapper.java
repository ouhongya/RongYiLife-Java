package com.rysh.module.community.mapper;

import com.rysh.module.community.beans.City;
import com.rysh.module.community.beans.ClientCity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CityMapper {

    //新增城市
     int addCity(City city);

     //更新城市名称
    void updateCity(@Param("id") String  id,@Param("newName") String newName);

    City queryByCityName(String cityName);

    List<City> findAllCity();
    //根据用户所在地地址信息查询到所在城市
    String  getUserCityByUserAddress(String addressId);

    //查询所有的城市以及区域的树形结构
    List<City> findAllCityAndArea();

    //查询城市->区域->小区
    List<City> findCityAndChild();

    /**
     * 查询所有手机端城市
     * @param
     * @return java.util.List<com.rysh.module.community.beans.ClientCity>
     * @author Hsiang Sun
     * @date 2019/10/16 18:06
     */
    List<ClientCity> findAllClientCity();

    City findCityById(String cityId);

    /**
     * 通过城市名字获取id
     * @param cityName
     * @return java.lang.String
     * @author Hsiang Sun
     * @date 2019/10/19 13:38
     */
    String findIdByCityName(String cityName);
}
