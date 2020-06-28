package com.rysh.module.store.service.impl;

import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.store.beans.Store;
import com.rysh.module.store.beans.StoreCategory;
import com.rysh.module.store.mapper.StoreCategoryMapper;
import com.rysh.module.store.service.StoreCategoryService;
import com.rysh.module.store.service.StoreItemService;
import com.rysh.module.store.service.StoreService2;
import com.rysh.module.utils.ToNullUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class StoreCategoryServiceImpl implements StoreCategoryService {

    @Autowired
    private StoreCategoryMapper mapper;

    @Autowired
    private StoreService2 storeService2;

    @Autowired
    private StoreItemService itemService;

    @Override
    public int addCategory(StoreCategory category) {
        //查询当前用户的农场
        category.setCreatedTime( new Date() );
        category.setStatus(1);
        return mapper.insertCategory(category);
    }

    @Override
    public List<StoreCategory> AvailableCategory(String id) {
        String aNull = ToNullUtil.toNull(id);
        if (aNull == null){
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Store farm = storeService2.getStoreByLogin(name);
            return mapper.findAllAvailableCategory(farm.getId());
        }else {
            return mapper.findAllAvailableCategory(id);
        }
    }

    @Override
    public int deleteCategory(String id, String login) {
        String storeId = storeService2.getStoreByLogin(login).getId();
        //判断当前分类是否已经空挂载
        int ietmNum = itemService.getItemNumByFarmCategoryId(id);
        System.err.println(ietmNum);
        if (ietmNum > 0){
            log.error("当前用户："+login+"试图删除有商品的分类");
            return 0;
        }
        return mapper.deleteCategoryById(id,storeId);
    }

    @Override
    public StoreCategory getCategoryById(String id) {
        return mapper.findCategoryById(id);
    }

    @Override
    public int updateName(String id, String name) {
        return mapper.updateNameById(id,name);
    }

    @Override
    public List<CategoryInfo> getCategoryInfo(String id) {
        String aNull = ToNullUtil.toNull(id);
        if (aNull == null){
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            return  mapper.findCategoryByLogin(aNull,name);
        }else {
            return  mapper.findCategoryByLogin(aNull,id);
        }

    }
}
