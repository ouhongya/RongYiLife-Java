package com.rysh.module.garbage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.garbage.beans.Garbage;
import com.rysh.module.garbage.mapper.GarbageMapper;
import com.rysh.module.garbage.service.GarbageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class GarbageServiceImpl implements GarbageService {

    @Autowired
    private GarbageMapper mapper;

    @Override
    public int add(Garbage garbage) {
        String cityId = garbage.getCityId();
        String categoryId = garbage.getCategoryId();
        String name = garbage.getName();
        //先判断是否已经在城市-分类存在该分类名
        Integer row = mapper.isExist(cityId,categoryId,name);
        if (row != null && row > 0 ){
            log.error("重复的分类名");
            return 0;
        }
        garbage.setStatus(1);
        garbage.setCreatedTime(new Date());
        return mapper.add(garbage);
    }

    @Override
    public int deleteGarbage(String id) {
        return mapper.deleteById(id);
    }

    @Override
    public int updateGarbage(Garbage garbage) {
        return mapper.update(garbage);
    }

    @Override
    public PageInfo<Garbage> all(ParamBean paramBean, String category, String city) {
        String operation = null;
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        if (paramBean.getSearch() == null || "".equals(paramBean.getSearch())){
            operation = "inactive";
        }else {
            operation = "active";
        }
        List<Garbage> garbageList = mapper.findAll(category,city,paramBean.getSearch(),operation);
        return new PageInfo<>(garbageList);
    }
}
