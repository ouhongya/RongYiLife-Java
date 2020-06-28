package com.rysh.module.garbage.mapper;

import com.rysh.module.clientLeSeClass.beans.SuperLeSe;
import com.rysh.module.garbage.beans.Garbage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GarbageMapper {

    /**
     * 添加
     * @param garbage
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/11 9:18
     */
    int add(Garbage garbage);

    /**
     * 根据ID删除当前垃圾
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/11 9:29
     */
    int deleteById(String id);

    /**
     * 更新垃圾
     * @param garbage
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/11 9:33
     */
    int update(Garbage garbage);

    /**
     * 查询当前城市-分类下的垃圾
     * @param category
	 * @param city
     * @return java.util.List<com.rysh.module.garbage.beans.Garbage>
     * @author Hsiang Sun
     * @date 2019/10/11 9:57
     */
    List<Garbage> findAll(@Param("category") String category,@Param("city") String city,@Param("search") String search,@Param("operation") String operation);

    /**
     * 查询当前城市-分类是否已经存在该分类名
     * @param cityId
	 * @param categoryId
	 * @param name
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/11 11:30
     */
    Integer isExist(@Param("city") String cityId,@Param("category") String categoryId,@Param("name") String name);

    List<Garbage> findGarbageByCategoryId(String categoryId);

    List<SuperLeSe> searchLeSe(@Param("search") String search, @Param("id") String id);
}