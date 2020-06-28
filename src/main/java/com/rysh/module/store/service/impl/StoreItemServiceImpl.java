package com.rysh.module.store.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.store.beans.*;
import com.rysh.module.store.mapper.StoreImgMapper;
import com.rysh.module.store.mapper.StoreMapper2;
import com.rysh.module.store.mapper.StoreSpecMapper;
import com.rysh.module.store.service.StoreItemService;
import com.rysh.module.utils.GenerateUUID;
import com.rysh.module.webSocket.WebSocket;
import com.rysh.system.UserType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class StoreItemServiceImpl implements StoreItemService {

    @Autowired
    private StoreMapper2 mapper;

    @Autowired
    private StoreSpecMapper specMapper;

    @Autowired
    private StoreImgMapper imgMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public int addNew(StoreParam storeParam) {
        String name = storeParam.getName();
        String description = storeParam.getDescription();
        List<StoreImg> imgUrls = storeParam.getImgUrls();
        StoreSpec spec = storeParam.getSpec();
        String categoryId = storeParam.getCategoryId();
        //商品id
        String ID = GenerateUUID.create();
        //添加商品信息
        StoreItem item = new StoreItem();
        item.setId(ID);
        item.setName(name);
        item.setCategoryId(categoryId);
        item.setStatus(1);
        item.setOprerator("system");
        item.setCreatedTime(new Date());
        item.setDescription(description);
        item.setState(0);
        item.setPass(0);
        int i = mapper.insertNew(item);

        //添加规格价格信息
        StoreSpec farmSpec = new StoreSpec();
        farmSpec.setItemId(ID);
        farmSpec.setCreatedTime(new Date());
        farmSpec.setPrice(spec.getPrice());
        farmSpec.setUnit(spec.getUnit());
        int spec1 = specMapper.addSpec(farmSpec);

        //添加商品图片
        int img0 = 0;

        for (StoreImg imgUrl : imgUrls) {
            StoreImg imgobj = new StoreImg();
            imgobj.setUrl(imgUrl.getUrl());
            imgobj.setCreatedTime(new Date());
            imgobj.setItemId(ID);
            imgobj.setStatus(1);
            imgobj.setLocation(imgUrl.getLocation());
            int img1 = imgMapper.addImg(imgobj);
            img0 += img1;
        }

        //发送消息
        webSocket.sendMsg("B4E972089FB044838A730CE1BBA809CC","周边商铺有新的商品来了");
        return i + spec1 + img0 ;
    }

    @Override
    public List<StoreParam> getAllNeedCheck() {
        return mapper.findUncheck();
    }

    @Override
    public int itemInactive(String operator, String id) {
        return mapper.updateStatus(operator,id);
    }

    @Override
    public int checkPass(List<String> ids, String name, String operation, String comment) {
        User user = accountMapper.findUserByUsername(name);
        return mapper.updateManyById(ids,user.getTrueName(),operation,new Date(),comment);
    }

    @Override
    public PageInfo<StoreItemView> search(ParamBean paramBean, String login,String itemId) {
        User user = accountMapper.findUserByUsername(login);
        if (user.getIsInsider() == UserType.商铺账户.typeCode()){
            PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
            String orderBy="t1."+paramBean.getSortByFiled() + " "+paramBean.getSortByOrder();
            PageHelper.orderBy(orderBy);
            List<StoreItemView> farmViewList = mapper.findByCondition(login,paramBean.getSearch());
            return new PageInfo<StoreItemView>(farmViewList);
        }else if (user.getIsInsider() == UserType.内部账户.typeCode()){
            PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
            PageHelper.orderBy("fi."+paramBean.getSortByFiled() +" "+paramBean.getSortByOrder());
            List<StoreItemView> farmViewList = mapper.findInnerContent(paramBean.getSearch(),itemId);
            return new PageInfo<StoreItemView>(farmViewList);
        }else {
            log.error("当前用户{} 试图获取农庄商品列表信息",login);
            return null;
        }
    }

    @Override
    public StoreParam getItemById(String id) {
        StoreParam result = new StoreParam();
        List<StoreParam> farmParamList = mapper.findById(id);
        for (StoreParam farmParam : farmParamList) {
            result.setId(farmParam.getId());
            result.setDescription(farmParam.getDescription());
            result.setCategoryId(farmParam.getCategoryId());
            result.setName(farmParam.getName());
            result.setImgUrls(farmParam.getImgUrls());
            result.setSpec(farmParam.getSpec());
        }
        return result;
    }

    @Override
    public int updateItem(StoreParam storeParam, String operator) {
        //更新item表
        String name = storeParam.getName();
        String categoryId = storeParam.getCategoryId();
        String description = storeParam.getDescription();
        String id = storeParam.getId();//primary key

        StoreItem item = new StoreItem();
        item.setId(id);
        item.setName(name);
        item.setCategoryId(categoryId);
        item.setOprerator(operator);
        item.setDescription(description);
        //需要重新审核
        item.setPass(0);

        int i =  mapper.updateItem(item);
        //更新img表
        List<StoreImg> imgUrls = storeParam.getImgUrls();
        int i0 = 0;
        //先删除img再添加
        imgMapper.deleteByItemId(id);
        //添加
        for (StoreImg imgUrl : imgUrls) {
            StoreImg farmImg = new StoreImg();
            farmImg.setUrl(imgUrl.getUrl());
            farmImg.setCreatedTime(new Date());
            farmImg.setItemId(id);
            farmImg.setStatus(1);
            farmImg.setLocation(imgUrl.getLocation());
            int i1 = imgMapper.addImg(farmImg);
            i0 += i1 ;
        }
        //spec表
        StoreSpec spec = storeParam.getSpec();
        StoreSpec farmSpec = new StoreSpec();
        farmSpec.setPrice(spec.getPrice());
        farmSpec.setUnit(spec.getUnit());
        farmSpec.setItemId(id);

        int i3 = specMapper.updateSpec(farmSpec);
        return i + i0 + i3;
    }

    @Override
    public int shelf(List<String> ids, String name, String operation) {
        User user = accountMapper.findUserByUsername(name);
        return mapper.itemUpOrDownShelf(ids,user.getTrueName(),operation);
    }

    @Override
    public int getItemNumByStoreCategoryId(String categoryId) {
        return mapper.findCountByFarmId(categoryId);
    }

    @Override
    public List<StoreParam> getOneUncheck(String id) {
        return mapper.findUncheckById(id);
    }

    @Override
    public int deleteById(String id) {
        return mapper.deleteById(id);
    }

    @Override
    public PageInfo<StoreItemView> checkHistory(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        String orderBy=paramBean.getSortByFiled() + " "+paramBean.getSortByOrder();
        PageHelper.orderBy(orderBy);
        List<StoreItemView> farmViewList = mapper.findCheckHistory(paramBean.getSearch());
        return new PageInfo<StoreItemView>(farmViewList);
    }

    @Override
    public int getItemNumByFarmCategoryId(String id) {
        return mapper.findCountByFarmId(id);
    }
}
