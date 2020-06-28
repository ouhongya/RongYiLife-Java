package com.rysh.module.garbage.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.garbage.beans.Garbage;

public interface GarbageService {
    /**
     * 添加垃圾
     * @param garbage
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/11 9:29
     */
    int add(Garbage garbage);

    /**
     * 删除垃圾
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/11 9:29
     */
    int deleteGarbage(String id);

    /**
     * 更新垃圾
     * @param garbage
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/11 9:32
     */
    int updateGarbage(Garbage garbage);

    /**
     * 查询当前所有垃圾
     * @param paramBean
	 * @param category
	 * @param city
     * @return com.github.pagehelper.PageInfo<com.rysh.module.garbage.beans.Garbage>
     * @author Hsiang Sun
     * @date 2019/10/11 9:53
     */
    PageInfo<Garbage> all(ParamBean paramBean, String category, String city);
}
