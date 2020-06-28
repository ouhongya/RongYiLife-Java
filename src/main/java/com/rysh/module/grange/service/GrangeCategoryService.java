package com.rysh.module.grange.service;

import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.grange.beans.GrangeCategory;

import java.util.List;

public interface GrangeCategoryService {
    /**
     * 添加农庄分类
     * @param category
	 * @param login
	 * @param itemId
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/25 14:33
     */
     int addCategory(GrangeCategory category,String login,String itemId);

     List<GrangeCategory> allNeedCheck();

     /**
      * 查询所有的分类信息
      * @param login
      * @return java.util.List<com.rysh.module.grange.beans.GrangeCategory>
      * @author Hsiang Sun
      * @date 2019/10/25 16:13
      */
     List<GrangeCategory> AvailableCategory(String login,String id);
     int deleteCategory(String id);
     int updateName(String id, String name);
     int checkPassMany(String name, List<String> ids);
     String getNameById(String id);
    /**
     * 通过id查询这个分类的信息
     * @param id
     * @return com.rysh.module.farm.beans.FarmCategory
     * @author Hsiang Sun
     * @date 2019/9/29 12:06
     */
    GrangeCategory getCategoryById(String id);

    /**
     * 查询当前用户的农庄分类信息
     * @param login
     * @return java.util.List<com.rysh.module.farm.beans.CategoryInfo>
     * @author Hsiang Sun
     * @date 2019/10/8 10:25
     */
    List<CategoryInfo> getCategoryInfo(String login,String itemId);
}
