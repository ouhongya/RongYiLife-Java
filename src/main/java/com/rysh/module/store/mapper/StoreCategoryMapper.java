package com.rysh.module.store.mapper;

import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.store.beans.StoreCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreCategoryMapper {

    /**
     * 新增商铺分类
     * @param category
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 10:56
     */
    int insertCategory(StoreCategory category);

    List<StoreCategory> findAllAvailableCategory(@Param("id") String id);

    /**
     * 删除分类
     * @param id
	 * @param storeId
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 11:04
     */
    int deleteCategoryById(@Param("id") String id,@Param("storeId") String storeId);

    /**
     * 通过id查询分类信息
     * @param id
     * @return com.rysh.module.store.beans.StoreCategory
     * @author Hsiang Sun
     * @date 2019/10/9 11:09
     */
    StoreCategory findCategoryById(String id);
    
    /**
     * 更新分类名字
     * @param id
	 * @param name 
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 11:10
     */
    int updateNameById(@Param("id") String  id, @Param("name") String name);

    List<CategoryInfo> findCategoryByLogin(@Param("login") String login,@Param("id") String id);

    List<StoreCategory> findStoreCategory(String marketId);

    StoreCategory findCategoryByIdToOrders(String categoryId);
}