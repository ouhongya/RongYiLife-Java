package com.rysh.module.farm.mapper;

import com.rysh.module.farm.beans.TagEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagItemMapper {
    int addTagItem(TagEntity tagEntity);

    List<TagEntity> findAll();

    int deleteById(@Param("id") String id);
}
