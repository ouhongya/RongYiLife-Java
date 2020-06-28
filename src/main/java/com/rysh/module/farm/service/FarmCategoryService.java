package com.rysh.module.farm.service;

import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.farm.beans.FarmCategory;

import java.util.List;

public interface FarmCategoryService {
    /*
     * 添加农场商品分类
     * @param category
     * @return int
     * @author HsiangSun
     * @date 2019/9/3 11:27
     */
    public int addCategory(FarmCategory category, String login,String itemId);

    /**
     * 所有可用的农场商品分类
     * @param
     * @return java.util.List<com.rysh.module.farm.beans.FarmCategory>
     * @author HsiangSun
     * @date 2019/9/4 10:21
     */
    public List<FarmCategory> AvailableCategory(String login,String id);

    /**
     * 删除农场分类
     * @param itemId
	 * @param categoryId
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/29 12:05
     */
    public int deleteCategory(String itemId,String categoryId);

    /**
     * 更新分类名
     * @param id
     * @return int
     * @author HsiangSun
     * @date 2019/9/4 11:00
     */
    public int updateName(String id,String name);

    /**
     * 根据当前登录用户查询他的分类信息
     * @param login
     * @return com.rysh.module.farm.beans.CategoryInfo
     * @author Hsiang Sun
     * @date 2019/9/25 15:59
     */
    public List<CategoryInfo> getCategoryInfo(String login,String itemId);

    /**
     * 通过id查询这个分类的信息
     * @param categoryId
     * @return com.rysh.module.farm.beans.FarmCategory
     * @author Hsiang Sun
     * @date 2019/9/29 12:06
     */
    public FarmCategory getCategoryById(String categoryId);
}
