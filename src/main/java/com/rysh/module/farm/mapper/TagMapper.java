package com.rysh.module.farm.mapper;

import com.rysh.module.farm.beans.Tag;
import com.rysh.module.farm.beans.TagEntity;
import com.rysh.module.farm.beans.TagResponse;

import java.util.List;

public interface TagMapper {
    /**
     * 添加标签
     * @param tag
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/21 14:20
     */
    int addTag(Tag tag);

    /**
     * 查询所有的标签
     * @param
     * @return java.util.List<com.rysh.module.farm.beans.TagResponse>
     * @author Hsiang Sun
     * @date 2019/10/21 14:20
     */
    List<TagResponse> findAllTag();

    /**
     * 删除标签
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/21 14:54
     */
    void deleteTag(String id);

    /**
     * 根据tagId 查询下面的挂载
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/21 14:59
     */
    int findTagEntityNumById(String id);

    /**
     * 根据标签名查询标签
     * @param tagName
     * @return com.rysh.module.farm.beans.Tag
     * @author Hsiang Sun
     * @date 2019/10/21 15:07
     */
    List<Tag> findTagByName(String tagName);

    /**
     * 更新标签
     * @param tag
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/21 15:14
     */
    void updateTag(Tag tag);

    /**
     * 通过farm id查询tag
     * @param id
     * @return java.util.List<com.rysh.module.farm.beans.Tag>
     * @author Hsiang Sun
     * @date 2019/10/21 16:41
     */
    List<Tag> findTagByFarmId(String id);

    /**
     * 给店铺添加标签
     * @param tagEntity
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/21 17:00
     */
    void addTagEntity(TagEntity tagEntity);

    /**
     * 通过entityID删除该标签
     * @param entityId
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/21 17:17
     */
    void deleteTagEntityByEntityId(String entityId);
}
