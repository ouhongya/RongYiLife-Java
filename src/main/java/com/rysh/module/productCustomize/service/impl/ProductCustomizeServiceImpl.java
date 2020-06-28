package com.rysh.module.productCustomize.service.impl;
import com.rysh.module.productCustomize.beans.CheckParam;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.productCustomize.beans.*;
import com.rysh.module.productCustomize.mapper.ProductCustomizeMapper;
import com.rysh.module.productCustomize.service.ProductCustomizeService;
import com.rysh.module.utils.GenerateUUID;
import com.rysh.module.utils.ToNullUtil;
import com.rysh.module.webSocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCustomizeServiceImpl implements ProductCustomizeService {

    @Autowired
    private ProductCustomizeMapper mapper;


    @Autowired
    private WebSocket webSocket;
    @Override
    @Transactional
    public void add(ProductCustomizeVo productCustomizeVo) {
        ProductCustomize customize = new ProductCustomize();
        String ID = GenerateUUID.create();
        customize.setId(ID);
        customize.setTitle(productCustomizeVo.getTitle());
        customize.setCover(productCustomizeVo.getCover());
        customize.setProductCustomizedCategoryId(productCustomizeVo.getProductCustomizedCategoryId());
        customize.setOperator(SecurityContextHolder.getContext().getAuthentication().getName());
        customize.setPass(0);//未审核
        customize.setPassOperator(null);
        customize.setPassTime(null);
        customize.setPassComment(null);
        customize.setCreatedTime(new Date());
        customize.setStatus(0);//未发布到客户端
        customize.setDefaultSort(productCustomizeVo.getDefaultSort());

        mapper.addProductCustom(customize);

        List<ProductCustomizeContent> contents = productCustomizeVo.getContents();
        for (ProductCustomizeContent content : contents) {
            ProductCustomizeContent customizeContent = new ProductCustomizeContent();
            customizeContent.setId(GenerateUUID.create());
            customizeContent.setContent(content.getContent());
            customizeContent.setType(content.getType());
            customizeContent.setQueue(content.getQueue());
            customizeContent.setProductCustomizedId(ID);

            mapper.addProductCustomContent(customizeContent);
        }

        webSocket.sendMsg("C76B480C45294686A3F51AF01F08F985","有新的社群定制来了");
    }

    @Override
    public PageInfo<ProductCustomizeRo> all(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        String search = ToNullUtil.toNull(paramBean.getSearch());
        if (search == null){
            return null;
        }
        List<ProductCustomizeRo> responseList = mapper.findAll(search);
        return new PageInfo<>(responseList);
    }

    @Override
    public void applyToClient(String id, String operation) {
        mapper.applyToClient(id,operation);
    }

    @Override
    public void delete(String id) {
        applyToClient(id,"delete");
    }

    @Override
    public ProductCustomizeRo detail(String id) {
        return mapper.findOneById(id);
    }

    @Override
    @Transactional
    public void update(ProductCustomizeVo productCustomizeVo) {
        String ID = productCustomizeVo.getId();

        ProductCustomize productCustomize = new ProductCustomize();
        productCustomize.setId(ID);
        productCustomize.setTitle(productCustomizeVo.getTitle());
        productCustomize.setCover(productCustomizeVo.getCover());
        productCustomize.setProductCustomizedCategoryId(productCustomizeVo.getProductCustomizedCategoryId());
        productCustomize.setOperator(SecurityContextHolder.getContext().getAuthentication().getName());
        productCustomize.setPass(0);
        productCustomize.setStatus(0);
        productCustomize.setDefaultSort(productCustomizeVo.getDefaultSort());

        mapper.update(productCustomize);

        //更新内容
        List<ProductCustomizeContent> contents = productCustomizeVo.getContents();
        //删除之前的内容
        mapper.deleteContentByCustomId(ID);
        for (ProductCustomizeContent content : contents) {
            ProductCustomizeContent customizeContent = new ProductCustomizeContent();
            customizeContent.setId(GenerateUUID.create());
            customizeContent.setContent(content.getContent());
            customizeContent.setType(content.getType());
            customizeContent.setQueue(content.getQueue());
            customizeContent.setProductCustomizedId(ID);
            mapper.addProductCustomContent(customizeContent);
        }
    }

    @Override
    public void updateSort(String id, String sort) {
        mapper.updateSort(id,sort);
    }

    @Override
    public PageInfo<ProductCustomizeContact> findContact(ParamBean paramBean,String id) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<ProductCustomizeContact> contact = mapper.findContact(id);
        return new PageInfo<>(contact);

    }

    @Override
    public void checkPass(CheckParam checkParam) {
        CheckPass pass = new CheckPass();
        pass.setCheckParam(checkParam);
        pass.setPassTime(new Date());
        pass.setPassOperator(SecurityContextHolder.getContext().getAuthentication().getName());
        mapper.checkPass(pass);//这里可以优化一下
    }

    @Override
    public PageInfo<ProductCustomizeVo> uncheck(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<ProductCustomizeVo> resultList = mapper.findAllUncheck();
        return new PageInfo<>(resultList);
    }

    @Override
    public PageInfo<ProductCustomize> checkHistory(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        String searchStr = paramBean.getSearch();
        String search = ToNullUtil.toNull(searchStr);
        List<ProductCustomize> resultList = mapper.checkHistory(search);
        return new PageInfo<>(resultList);
    }
}
