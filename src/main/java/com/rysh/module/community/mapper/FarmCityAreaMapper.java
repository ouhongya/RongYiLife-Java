package com.rysh.module.community.mapper;

import com.rysh.module.community.beans.ClientArea;
import com.rysh.module.community.beans.ClientCity;

import java.util.List;

public interface FarmCityAreaMapper {
    List<ClientCity> findFarmCity();

    List<ClientArea> findFarmAreaByCityId(String cityId);
}
