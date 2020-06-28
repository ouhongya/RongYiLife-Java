package com.rysh.module.community.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.community.beans.City;
import com.rysh.module.community.beans.ClientCity;

import java.util.List;

public interface CityService {
    /**
     * 新增城市
     * @param cityName
     * @return java.lang.String
     * @author HsiangSun
     * @date 2019/8/22 17:20
     */
    public int addCity(String cityName);

    /**
     * 更新城市名字
     * @param id
	 * @param newName
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/18 16:23
     */
    public void updateCity(String id,String newName);

    public City queryByCityName(String cityName);

    public List<City> findAll();

    public  List<City> getCityAndAreaTree();

    public List<City> getCityAndAreaAndCommunityTree();

    /**
     * 查询手机端所有城市
     * @param
     * @return java.util.List<com.rysh.module.community.beans.ClientCity>
     * @author Hsiang Sun
     * @date 2019/10/16 18:05
     */
    List<ClientCity> getClientCity();

    /**
     * 查询所有的城市列表
     * @param null
     * @return com.github.pagehelper.PageInfo<com.rysh.module.community.beans.City>
     * @author Hsiang Sun
     * @date 2019/10/18 16:32
     */
    List<City> getAllCity();

    /**
     * 通过城市名字获取id
     * @param cityName
     * @return java.lang.String
     * @author Hsiang Sun
     * @date 2019/10/19 13:37
     */
    String findIdByName(String cityName);
}
