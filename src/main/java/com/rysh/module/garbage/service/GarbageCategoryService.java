package com.rysh.module.garbage.service;

import com.rysh.module.garbage.beans.GarbageCategory;
import com.rysh.module.garbage.beans.ResponseCategory;

import java.util.List;

public interface GarbageCategoryService {
    /**
     * 新增垃圾分类类别
     * @param category
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/10 17:08
     */
    void add(GarbageCategory category);

    /**
     * 删除类别
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/10 17:18
     */
    void delete(String id);

    /**
     * 更新
     * @param category
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/10 17:46
     */
    void update(GarbageCategory category);

    /**
     * 查询所有
     * @param
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/10 18:05
     */
    List<ResponseCategory> allAvailable(String id);
}
