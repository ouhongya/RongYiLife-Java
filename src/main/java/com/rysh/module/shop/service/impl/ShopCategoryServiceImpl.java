package com.rysh.module.shop.service.impl;

import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.shop.beans.Shop;
import com.rysh.module.shop.beans.ShopCategory;
import com.rysh.module.shop.mapper.ShopCategoryMapper;
import com.rysh.module.shop.service.ShopCategoryService;
import com.rysh.module.shop.service.ShopItemService;
import com.rysh.module.shop.service.ShopService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Log4j2
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryMapper mapper;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopItemService itemService;

    @Override
    public int addCategory(ShopCategory category, String login) {
        //查询当前用户的农场
        Shop store = shopService.getStoreByLogin(login);
        category.setCreatedTime( new Date() );
        category.setShopId(store.getId());
        category.setStatus(1);
        return mapper.insertCategory(category);
    }

    @Override
    public List<ShopCategory> AvailableCategory(String login) {
        Shop farm = shopService.getStoreByLogin(login);
        return mapper.findAllAvailableCategory(farm.getId());
    }

    @Override
    public int deleteCategory(String id, String login) {
        String storeId = shopService.getStoreByLogin(login).getId();
        //判断当前分类是否已经空挂载
        int ietmNum = itemService.getItemNumByStoreCategoryId(id);
        System.err.println(ietmNum);
        if (ietmNum > 0){
            log.error("当前用户："+login+"试图删除有商品的分类");
            return 0;
        }
        return mapper.deleteCategoryById(id,storeId);
    }

    @Override
    public ShopCategory getCategoryById(String id) {
        return mapper.findCategoryById(id);
    }

    @Override
    public int updateName(String id, String name) {
        return mapper.updateNameById(id,name);
    }

    @Override
    public List<CategoryInfo> getCategoryInfo(String login) {
        return  mapper.findCategoryByLogin(login);
    }
}
