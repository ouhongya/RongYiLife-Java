package com.rysh.module.community.service;

import com.rysh.module.community.beans.ClientArea;
import com.rysh.module.community.beans.ClientCity;

import java.util.List;

public interface ClientRYSHCityAreaService {
    List<ClientCity> findFarmCity() throws Exception;

    List<ClientArea> findFarmAreaByCityId(String cityId) throws Exception;

    List<ClientCity> findGrangeCity() throws Exception;

    List<ClientArea> findGrangeAreaByCityId(String cityId) throws Exception;
}
