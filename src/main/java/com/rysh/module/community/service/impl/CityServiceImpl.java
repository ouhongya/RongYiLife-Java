package com.rysh.module.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.community.beans.City;
import com.rysh.module.community.beans.ClientCity;
import com.rysh.module.community.mapper.CityMapper;
import com.rysh.module.community.service.CityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Log4j2
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    /**
     * 新增城市
     * @param CityName
     * @return java.lang.String
     * @author HsiangSun
     * @date 2019/8/22 17:20
     */
    @Transactional
    public int addCity(String CityName){
        City city = new City();
        city.setCityName(CityName);
        city.setCreatedTime(new Date());
        return cityMapper.addCity(city);
    }

    public void updateCity(String id,String newName){
        cityMapper.updateCity(id,newName);
    }

    //根据城市名查询详情
    public City queryByCityName(String cityName){
        return cityMapper.queryByCityName(cityName);
    }

    public List<City> findAll() {
        return cityMapper.findAllCity();
    }

    //查询所有城市以及区域的树形结构
    public  List<City> getCityAndAreaTree() {
        return cityMapper.findAllCityAndArea();
    }
    //查询所有城市以及区域和小区的树形结构
    public List<City> getCityAndAreaAndCommunityTree() {
        return cityMapper.findCityAndChild();
    }

    @Override
    public List<ClientCity> getClientCity() {
        return cityMapper.findAllClientCity();
    }

    @Override
    public List<City> getAllCity() {
        return cityMapper.findAllCity();
    }

    @Override
    public String findIdByName(String cityName) {
        return cityMapper.findIdByCityName(cityName);
    }
}
