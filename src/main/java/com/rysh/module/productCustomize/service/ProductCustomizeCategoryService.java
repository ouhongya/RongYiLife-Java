package com.rysh.module.productCustomize.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.productCustomize.beans.ProductCustomizeCategory;

public interface ProductCustomizeCategoryService {
    /**
     * 添加分类
     * @param name
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/14 15:34
     */
    void addCategory(String name);

    /**
     * 删除分类名
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/14 15:46
     */
    void deleteCategory(String id);

    /**
     * 更新分类
     * @param id
     * @param name
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/14 15:56
     */
    void updateCategory(String id, String name);

    /**
     * 查询所有的分类信息
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.productCustomize.beans.ProductCustomizeCategory>
     * @author Hsiang Sun
     * @date 2019/11/14 16:02
     */
    PageInfo<ProductCustomizeCategory> getAll(ParamBean paramBean);
}
