package com.rysh.module.shop.mapper;

import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.shop.beans.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryMapper {

    /**
     * 新增商铺分类
     * @param category
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 10:56
     */
    int insertCategory(ShopCategory category);

    List<ShopCategory> findAllAvailableCategory(@Param("id") String id);

    /**
     * 删除分类
     * @param id
	 * @param shopId
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 11:04
     */
    int deleteCategoryById(@Param("id") String id, @Param("shopId") String shopId);

    /**
     * 通过id查询分类信息
     * @param id
     * @return com.rysh.module.store.beans.StoreCategory
     * @author Hsiang Sun
     * @date 2019/10/9 11:09
     */
    ShopCategory findCategoryById(String id);

    /**
     * 更新分类名字
     * @param id
	 * @param name
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 11:10
     */
    int updateNameById(@Param("id") String id, @Param("name") String name);

    List<CategoryInfo> findCategoryByLogin(@Param("login") String login);

    List<ShopCategory> findShopCategory(String marketId);

    ShopCategory findCategoryByIdToOrders(String categoryId);

    List<ShopCategory> findAllCategory(@Param("search") String search);
}