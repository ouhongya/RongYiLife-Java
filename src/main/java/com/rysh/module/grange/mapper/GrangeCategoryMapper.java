package com.rysh.module.grange.mapper;

import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.grange.beans.GrangeCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GrangeCategoryMapper {

    int insert(GrangeCategory category);

    List<GrangeCategory> findBySatus();

    List<GrangeCategory> findAllAvailableCategory(String grangeId);

    int deleteById(String id);

    int updateNameById(@Param("id") String id,@Param("name") String name);

    int checkPassMany(@Param("oprator") String name,@Param("ids") List<String> ids);

    String findNameById(@Param("id") String id);

    GrangeCategory findCategoryById(String id);

    List<CategoryInfo> findCategoryByLogin(@Param("login") String login,@Param("mayNull") String mayNull);

    List<GrangeCategory> findGrangeCategory(String marketId);

    GrangeCategory findCategoryByIdToOrders(String categoryId);
}