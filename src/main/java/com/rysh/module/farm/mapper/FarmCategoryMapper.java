package com.rysh.module.farm.mapper;

import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.farm.beans.FarmCategory;
import com.rysh.module.farm.beans.OperatorAndIds;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmCategoryMapper {

    int insertCategory(FarmCategory category);

    List<FarmCategory> findAllAvailableCategory(@Param("farmId") String farmId);

    int deleteCategoryById(@Param("itemId") String itemId,@Param("categoryId") String categoryId);

    int updateNameById(@Param("id") String  id, @Param("name") String name);

    /**
     * 查询当前用户下面的所有分类信息
     * @param login
     * @return java.util.List<com.rysh.module.farm.beans.CategoryInfo>
     * @author Hsiang Sun
     * @date 2019/9/25 16:12
     */
    List<CategoryInfo> findCategoryByLogin(@Param("login") String login,@Param("mayNull") String mayNull);

    FarmCategory findCategoryById(@Param("categoryId") String id);

    List<FarmCategory> findFarmCategory(String marketId);

    FarmCategory findCategoryByIdToOrders(String categoryId);
}
