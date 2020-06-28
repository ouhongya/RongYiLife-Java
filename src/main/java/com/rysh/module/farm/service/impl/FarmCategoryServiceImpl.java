package com.rysh.module.farm.service.impl;

import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.farm.beans.Farm;
import com.rysh.module.farm.beans.FarmCategory;
import com.rysh.module.farm.mapper.FarmCategoryMapper;
import com.rysh.module.farm.service.FarmCategoryService;
import com.rysh.module.farm.service.FarmItemService;
import com.rysh.module.farm.service.FarmService;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.utils.ToNullUtil;
import com.rysh.system.UserType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class FarmCategoryServiceImpl implements FarmCategoryService {

    @Autowired
    private FarmCategoryMapper mapper;

    @Autowired
    private FarmService farmService;

    @Autowired
    private FarmItemService itemService;

    @Autowired
    private AccountMapper accountMapper;

    public int addCategory(FarmCategory category,String login,String itemId){

        User user = accountMapper.findUserByUsername(login);
        if (user.getIsInsider() == UserType.农场账户.typeCode()){
            //查询当前用户的农场
            Farm farm = farmService.getFarmByLogin(user.getId());
            category.setCreatedTime( new Date() );
            category.setFarmId(farm.getId());
            category.setStatus(1);
            return mapper.insertCategory(category);
        }else if (user.getIsInsider() == UserType.内部账户.typeCode()){
            category.setCreatedTime( new Date() );
            category.setFarmId(itemId);
            category.setStatus(1);
            log.warn("管理员【{}】于 {} 添加了 {} 农场商品分类",login,new Date(),itemId);
            return mapper.insertCategory(category);
        }else {
            log.error("当前用户{} 试图为农场id为 {} 添加商品分类",login,itemId);
            return -1;
        }
    }

    public List<FarmCategory> AvailableCategory(String login,String id) {
        User user = accountMapper.findUserByUsername(login);
        if (user.getIsInsider() == UserType.农场账户.typeCode()){
            Farm farm = farmService.getFarmByLogin(user.getId());
            return mapper.findAllAvailableCategory(farm.getId());
        }else if (user.getIsInsider() == UserType.内部账户.typeCode()){
            return mapper.findAllAvailableCategory(id);
        }else {
            log.error("当前用户【{}】 获取农场商品分类失败",login);
            return null;
        }
    }

    public int deleteCategory(String itemId,String categoryId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = accountMapper.findUserByUsername(login);
        if (user.getIsInsider() == UserType.农场账户.typeCode()){
            String farmId = farmService.getFarmByLogin(user.getId()).getId();
            //判断当前分类是否已经空挂载
            int ietmNum = itemService.getItemNumByFarmCateegoryId(itemId);
            if (ietmNum > 0){
                log.error("当前用户："+login+"试图删除有商品的分类");
                return 0;
            }
            return mapper.deleteCategoryById(farmId,categoryId);
        }else if (user.getIsInsider() == UserType.内部账户.typeCode()){
            //判断当前分类是否已经空挂载
            int ietmNum = itemService.getItemNumByFarmCateegoryId(itemId);
            if (ietmNum > 0){
                log.error("当前用户："+login+"试图删除有商品的分类");
                return 0;
            }
            return mapper.deleteCategoryById(itemId,categoryId);
        }else {
            return -1;
        }
    }

    public int updateName(String id,String name) {
        return mapper.updateNameById(id,name);
    }

    public List<CategoryInfo> getCategoryInfo(String login,String itemId) {
        String mayNull = ToNullUtil.toNull(itemId);
        return  mapper.findCategoryByLogin(login,mayNull);
    }

    public FarmCategory getCategoryById(String categoryId) {
        return mapper.findCategoryById(categoryId);
    }
}
