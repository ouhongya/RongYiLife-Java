package com.rysh.module.farm.mapper;


import com.rysh.module.farm.beans.FarmSpec;

import java.util.List;

public interface FarmSpecMapper {

    int addSpec(FarmSpec spec);

    int updateSpec(FarmSpec farmSpec);

    FarmSpec findFarmSpecByItemId(String goodsId);

    List<FarmSpec> findFarmSpecsByItemId(String id);

    FarmSpec findById(String productSpecId);
}
