package com.rysh.module.store.service;


import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.store.beans.StoreTag;
import com.rysh.module.store.beans.StoreTagDisplay;

import java.util.List;

public interface StoreTagService {

    /**
     * 添加周边商铺标签
     * @param tag
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/1 17:31
     */
    int addTag(StoreTag tag);

    /**
     * 根据id删除标签
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/1 17:53
     */
    void deleteTag(String id);

    /**
     * 根据id回显标签
     * @param id
     * @return com.rysh.module.store.beans.StoreTag
     * @author Hsiang Sun
     * @date 2019/11/4 9:16
     */
    StoreTag info(String id);

    /**
     * 更新标签
     * @param tag
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/4 9:25
     */
    void updateTag(StoreTag tag);

    /**
     * 查询标签展示的信息
     * @param
     * @return com.rysh.module.store.beans.StoreTagDisplay
     * @author Hsiang Sun
     * @date 2019/11/4 9:39
     */
    PageInfo<StoreTagDisplay> displayTag(ParamBean paramBean);
}
