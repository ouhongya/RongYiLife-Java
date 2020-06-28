package com.rysh.module.clientPackingDesign.mapper;

import com.rysh.module.clientPackingDesign.beans.PackingDesign;
import com.rysh.module.clientPackingDesign.beans.PackingDesignCategory;
import com.rysh.module.clientPackingDesign.beans.PackingDesignDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PackingDesignMapper {
    List<PackingDesign> findPackingDesign(@Param("categoryId") String categoryId);

    List<PackingDesignDetail> findPackingDesignDetailById(String id);

    List<PackingDesignCategory> findPackingDesignCategory();

    void addMessageToP(@Param("id") String id,@Param("tel") String tel,@Param("name") String name,@Param("customizedId") String customizedId,@Param("date") Date date);
}
