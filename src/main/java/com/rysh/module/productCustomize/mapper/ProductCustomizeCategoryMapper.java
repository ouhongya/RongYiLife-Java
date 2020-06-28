package com.rysh.module.productCustomize.mapper;

import com.rysh.module.productCustomize.beans.ProductCustomizeCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCustomizeCategoryMapper {

    /**
     * 添加分类
     * @param category
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/14 15:50
     */
    void addCategory(ProductCustomizeCategory category);

    /**
     * 查询当前分类的使用数
     * @param id
     * @return java.lang.Integer
     * @author Hsiang Sun
     * @date 2019/11/14 15:49
     */
    Integer findCountByCategoryId(String id);

    /**
     * 删除分类
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/14 15:51
     */
    void deleteCategoryById(String id);

    /**
     * 更新分类
     * @param id
	 * @param name
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/14 15:57
     */
    void updateCategory(@Param("id") String id,@Param("name") String name);

    /**
     * 查询所有的分类信息
     * @param search
     * @return java.util.List<com.rysh.module.productCustomize.beans.ProductCustomizeCategory>
     * @author Hsiang Sun
     * @date 2019/11/14 16:04
     */
    List<ProductCustomizeCategory> findAll(@Param("search") String search);
}
