package com.rysh.module.garbage.service.impl;

import com.rysh.module.garbage.beans.GarbageCategory;
import com.rysh.module.garbage.beans.ResponseCategory;
import com.rysh.module.garbage.mapper.GarbageCategoryMapper;
import com.rysh.module.garbage.service.GarbageCategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Log4j2
@Service
public class GarbageCategoryServiceImpl implements GarbageCategoryService {

    @Autowired
    private GarbageCategoryMapper mapper;

    @Override
    public void add(GarbageCategory category) {
        //先判断是否已经在城市-分类存在该分类名
        Integer row = mapper.isExist(category.getCityId(),category.getName());
        if (row != null && row > 0 ){
            log.error("重复的分类名");
            throw new RuntimeException("重复的分类名");
        }
        category.setStatus(1);
        category.setCreatedTime(new Date());
        mapper.add(category);
    }

    @Override
    public void delete(String id) {
        //先判断当前类别下面挂载的垃圾item
        int count = mapper.countCategory(id);
        if (count > 0){
            throw new RuntimeException("当前分类存在关联内容不允许删除");
        }
        mapper.deleteById(id);
    }

    @Override
    public void update(GarbageCategory category) {
        mapper.update(category);
    }

    @Override
    public List<ResponseCategory> allAvailable(String id) {
       return mapper.findAll(id);
    }
}
