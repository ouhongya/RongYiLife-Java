package com.rysh.module.community.service.impl;

import com.rysh.module.community.beans.ClientArea;
import com.rysh.module.community.beans.ClientCity;
import com.rysh.module.community.mapper.FarmCityAreaMapper;
import com.rysh.module.community.mapper.GrangeCityAreaMapper;
import com.rysh.module.community.service.ClientRYSHCityAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientRYSHCityAreaServiceImpl implements ClientRYSHCityAreaService {

    @Autowired
    private FarmCityAreaMapper farmCityAreaMapper;

    @Autowired
    private GrangeCityAreaMapper grangeCityAreaMapper;


    /**
     * 查询所有绑定了农场的城市
     * @return
     * @throws Exception
     */
    @Override
    public List<ClientCity> findFarmCity() throws Exception {
        //查询所有绑定了农场的城市
        List<ClientCity> clientCities=farmCityAreaMapper.findFarmCity();
        return clientCities;
    }


    /**
     * 通过城市id查询该城市下绑定了农场的区域
     * @param cityId
     * @return
     * @throws Exception
     */
    @Override
    public List<ClientArea> findFarmAreaByCityId(String cityId) throws Exception {
        //通过城市id查询 该城市绑定了农场的区域
        List<ClientArea> clientAreas=farmCityAreaMapper.findFarmAreaByCityId(cityId);
        return clientAreas;
    }


    /**
     * 查询所有绑定了农庄的城市
     * @return
     * @throws Exception
     */
    @Override
    public List<ClientCity> findGrangeCity() throws Exception {
        //查询所有绑定了农庄的城市
        List<ClientCity> clientCities=grangeCityAreaMapper.findGrangeCity();
        return clientCities;
    }


    /**
     * 通过城市id查询 该城市下绑定了农庄的区域
     * @param cityId
     * @return
     * @throws Exception
     */
    @Override
    public List<ClientArea> findGrangeAreaByCityId(String cityId) throws Exception {
        //通过城市id查询  该城市绑定了农庄的区域
        List<ClientArea> clientAreas = grangeCityAreaMapper.findGrangeAreaByCityId(cityId);
        return clientAreas;
    }
}
