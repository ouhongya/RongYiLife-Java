package com.rysh.module.store.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.store.beans.StoreTag;
import com.rysh.module.store.beans.StoreTagDisplay;
import com.rysh.module.store.mapper.StoreTagMapper;
import com.rysh.module.store.service.StoreTagService;
import com.rysh.module.utils.GenerateUUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StoreTagServiceImpl implements StoreTagService {

    @Autowired
    private StoreTagMapper mapper;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public int addTag(StoreTag tag) {
        tag.setCreatedTime(new Date());
        tag.setId(GenerateUUID.create());
        tag.setOperator(accountMapper.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getTrueName());
        return mapper.addTag(tag);
    }

    @Override
    public void deleteTag(String id) {
        //首先判断当前标签下面是否存在挂载
        int count = mapper.findTagUseCountById(id);
        if (count > 0){
            throw new RuntimeException("当前标签已经被使用不可删除");
        }
        mapper.deleteTagById(id);
    }

    @Override
    public StoreTag info(String id) {
        return mapper.findTagById(id);
    }

    @Override
    public void updateTag(StoreTag tag) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = accountMapper.findUserByUsername(name);
        tag.setOperator(user.getTrueName());
        mapper.updateTag(tag);
    }

    @Override
    public PageInfo<StoreTagDisplay> displayTag(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<StoreTagDisplay> list = mapper.findTagDisplay();
        return new PageInfo<>(list);
    }
}
