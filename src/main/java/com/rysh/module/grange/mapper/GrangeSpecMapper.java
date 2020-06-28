package com.rysh.module.grange.mapper;

import com.rysh.module.grange.beans.GrangeSpec;

import java.util.List;

public interface GrangeSpecMapper {
    int addSpec(GrangeSpec spec);

    int updateSpec(GrangeSpec farmSpec);

    GrangeSpec findFarmSpecByItemId(String goodsId);

    List<GrangeSpec> findFarmSpecsByItemId(String id);

    GrangeSpec findById(String productSpecId);
}
