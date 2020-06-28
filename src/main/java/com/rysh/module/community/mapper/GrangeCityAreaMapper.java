package com.rysh.module.community.mapper;

import com.rysh.module.community.beans.ClientArea;
import com.rysh.module.community.beans.ClientCity;

import java.util.List;

public interface GrangeCityAreaMapper {
    List<ClientCity> findGrangeCity();

    List<ClientArea> findGrangeAreaByCityId(String cityId);
}
