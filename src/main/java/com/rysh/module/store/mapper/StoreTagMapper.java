package com.rysh.module.store.mapper;

import com.rysh.module.store.beans.StoreTag;
import com.rysh.module.store.beans.StoreTagDisplay;

import java.util.List;

public interface StoreTagMapper {
    /**
     * 添加周边商铺标签
     * @param tag
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/1 17:59
     */
    int addTag(StoreTag tag);

    /**
     * 查询当前标签下面挂载的数量
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/1 17:59
     */
    int findTagUseCountById(String id);

    /**
     * 根据标签id删除标签
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/1 18:02
     */
    void deleteTagById(String id);

    /**
     * 根据id查询标签
     * @param id
     * @return com.rysh.module.store.beans.StoreTag
     * @author Hsiang Sun
     * @date 2019/11/4 9:17
     */
    StoreTag findTagById(String id);

    /**
     * 更新标签
     * @param tag
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/4 9:20
     */
    void updateTag(StoreTag tag);

    /**
     * 查询所有的标签展示信息
     * @param
     * @return java.util.List<com.rysh.module.store.beans.StoreTagDisplay>
     * @author Hsiang Sun
     * @date 2019/11/4 9:42
     */
    List<StoreTagDisplay> findTagDisplay();
}
