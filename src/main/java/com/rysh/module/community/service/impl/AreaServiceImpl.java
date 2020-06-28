package com.rysh.module.community.service.impl;

import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.ClientArea;
import com.rysh.module.community.mapper.AreaMapper;
import com.rysh.module.community.service.AreaService;
import com.rysh.module.community.service.CityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private CityService cityService;

    @Autowired
    private AreaMapper areaMapper;

    public void addArea(String cityName,String areaName){
        String cityId = cityService.findIdByName(cityName);
        if (cityId == null){
            log.error("添加区域失败 目前城市不存在");
        }
        Area area = new Area();
        area.setAreaName(areaName);
        area.setCityId(cityId);
        area.setCreatedTime(new Date());
        areaMapper.addArea(area);
    }

    public boolean checkHasExist(String cityName, String areaName) {
        List<Area> areaList = areaMapper.checkHasExist(cityName,areaName);
        return areaList.size() > 0 ? true : false;
    }

    public String getAreaId(String areaName){
        return areaMapper.findIdByName(areaName);
    }

    public List<Area> getAllAreaByCityId(String cityId) {
        return areaMapper.findAreaByCityId(cityId);
    }

    public int updateAreaName(Area area) {
        return areaMapper.updateAreaName(area);
    }

    @Override
    public List<ClientArea> getAreaByCityId(String id) {
        return areaMapper.findAllByCityId(id);
    }
}
