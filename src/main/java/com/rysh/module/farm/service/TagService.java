package com.rysh.module.farm.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.Tag;
import com.rysh.module.farm.beans.TagResponse;

public interface TagService {

    /**
     * 添加一个标签
     * @param tagName
     * @param operator
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/21 14:05
     */
    int addTag(String tagName,String operator);

    /**
     * 查询所有的标签信息
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.farm.beans.Tag>
     * @author Hsiang Sun
     * @date 2019/10/21 14:15
     */
    PageInfo<TagResponse> allTag(ParamBean paramBean);

    /**
     * 删除标签
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/21 14:54
     */
    void deleteTag(String id);

    /**
     * 更新标签
     * @param tag
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/21 15:13
     */
    void updateTag(Tag tag,String login);
}
