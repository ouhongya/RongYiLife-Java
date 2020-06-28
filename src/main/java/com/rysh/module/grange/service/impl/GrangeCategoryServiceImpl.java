package com.rysh.module.grange.service.impl;

import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.grange.beans.Grange;
import com.rysh.module.grange.beans.GrangeCategory;
import com.rysh.module.grange.mapper.GrangeCategoryMapper;
import com.rysh.module.grange.mapper.GrangeMapper;
import com.rysh.module.grange.service.GrangeCategoryService;
import com.rysh.module.grange.service.GrangeService;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.utils.ToNullUtil;
import com.rysh.system.UserType;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@Log4j2
public class GrangeCategoryServiceImpl implements GrangeCategoryService {
    @Autowired
    private GrangeCategoryMapper mapper;

    @Autowired
    private GrangeService grangeService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private GrangeMapper grangeMapper;

    public int addCategory(GrangeCategory category,String login,String itemId) {

        User user = accountMapper.findUserByUsername(login);
        if (user.getIsInsider() == UserType.农庄账户.typeCode()){
            //查询当前用户的农场
            Grange grange = grangeService.getGrangeByLogin(login);
            category.setCreatedTime( new Date() );
            category.setGrangeId(grange.getId());
            category.setStatus(1);
            return mapper.insert(category);
        }else if (user.getIsInsider() == UserType.内部账户.typeCode()){
            category.setCreatedTime( new Date() );
            category.setGrangeId(itemId);
            category.setStatus(1);
            log.warn("当前用户{}给农庄{}添加了一个分类信息",login,itemId);
            return mapper.insert(category);
        }else {
            log.error("当前用户{}试图给农庄{}添加分类",login,itemId);
            return 0;
        }
    }

    public List<GrangeCategory> allNeedCheck() {
        return mapper.findBySatus();
    }

    public List<GrangeCategory> AvailableCategory(String login,String itemId) {
        User user = accountMapper.findUserByUsername(login);
        if (user.getIsInsider() == UserType.农庄账户.typeCode()){
            Grange grange = grangeService.getGrangeByLogin(login);
            return mapper.findAllAvailableCategory(grange.getId());
        }else if (user.getIsInsider() == UserType.内部账户.typeCode()){
            Grange grange = grangeMapper.findGrangeByLogin(login, itemId);
            return mapper.findAllAvailableCategory(grange.getId());
        }else {
            log.error("当前用户{}获取农庄分类失败",login);
            return null;
        }
    }

    public int deleteCategory(String id) {
        return mapper.deleteById(id);
    }

    public int updateName(String id, String name) {
        return mapper.updateNameById(id,name);
    }

    public int checkPassMany(String name, List<String> ids) {
        return mapper.checkPassMany(name,ids);
    }

    public String getNameById(String id){
        return mapper.findNameById(id);
    }

    @Override
    public GrangeCategory getCategoryById(String id) {
        return mapper.findCategoryById(id);
    }

    @Override
    public List<CategoryInfo> getCategoryInfo(String login,String itemId) {
        String mayNull = ToNullUtil.toNull(itemId);
        return mapper.findCategoryByLogin(login,mayNull);
    }
}
