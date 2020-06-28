package com.rysh.module.garbage.mapper;

import com.rysh.module.garbage.beans.GarbageCategory;
import com.rysh.module.garbage.beans.ResponseCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GarbageCategoryMapper {

    /**
     * 新增垃圾分类类别
     * @param category
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/10 17:11
     */
    void add(GarbageCategory category);

    /**
     * 删除类别
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/10 17:19
     */
    void deleteById(String id);

    /**
     * 查询当前类别下面存在的关联内容
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/10 17:36
     */
    int countCategory(String id);

    /**
     * 更新
     * @param category
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/10 17:48
     */
    void update(GarbageCategory category);

    /**
     * 根据城市查询类别
     * @param id
     * @return java.util.List<com.rysh.module.garbage.beans.ResponseCategory>
     * @author Hsiang Sun
     * @date 2019/10/11 13:54
     */
    List<ResponseCategory> findAll(String id);

    /**
     * 查询当前城市-分类是否已经存在了该分类名
     * @param cityId
	 * @param name
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/11 11:48
     */
    Integer isExist(@Param("cityId") String cityId,@Param("name") String name);

    List<GarbageCategory> findGarbageCategoryByCityId(String id);
}