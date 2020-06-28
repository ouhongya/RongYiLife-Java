package com.rysh.module.productCustomize.service.impl;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.productCustomize.beans.ProductCustomizeCategory;
import com.rysh.module.productCustomize.mapper.ProductCustomizeCategoryMapper;
import com.rysh.module.productCustomize.service.ProductCustomizeCategoryService;
import com.rysh.module.utils.GenerateUUID;
import com.rysh.module.utils.ToNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCustomizeCategoryServiceImpl implements ProductCustomizeCategoryService {

    @Autowired
    private ProductCustomizeCategoryMapper mapper;

    @Override
    public void addCategory(String name) {
        ProductCustomizeCategory category = new ProductCustomizeCategory();
        category.setId(GenerateUUID.create());
        category.setName(name);
        category.setCreatedTime(new Date());
        category.setStatus(1);
        mapper.addCategory(category);
    }

    @Override
    public void deleteCategory(String id) {
        //先检查当前分类是否已经被使用
        Integer num = mapper.findCountByCategoryId(id);
        if (num > 0){
            throw new RuntimeException("当前分类已经被绑定无法删除");
        }
        mapper.deleteCategoryById(id);
    }

    @Override
    public void updateCategory(String id, String name) {
        mapper.updateCategory(id,name);
    }

    @Override
    public PageInfo<ProductCustomizeCategory> getAll(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        String aNull = ToNullUtil.toNull(paramBean.getSearch());
        List<ProductCustomizeCategory> resultInfo = mapper.findAll(aNull);
        for (ProductCustomizeCategory productCustomizeCategory : resultInfo) {
            productCustomizeCategory.setCount(mapper.findCountByCategoryId(productCustomizeCategory.getId()));//设置绑定数
        }
        return new PageInfo<>(resultInfo);
    }
}
