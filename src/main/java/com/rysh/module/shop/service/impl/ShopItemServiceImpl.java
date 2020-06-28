package com.rysh.module.shop.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.shop.beans.*;
import com.rysh.module.shop.mapper.ShopImgMapper;
import com.rysh.module.shop.mapper.ShopItemMapper;
import com.rysh.module.shop.mapper.ShopSpecMapper;
import com.rysh.module.shop.service.ShopItemService;
import com.rysh.module.utils.GenerateUUID;
import com.rysh.module.utils.ToNullUtil;
import com.rysh.module.webSocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ShopItemServiceImpl implements ShopItemService {

    @Autowired
    private ShopItemMapper mapper;

    @Autowired
    private ShopSpecMapper specMapper;

    @Autowired
    private ShopImgMapper imgMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private WebSocket webSocket;

    @Override
    public int addNew(ShopParam shopParam) {
        String name = shopParam.getName();
        String description = shopParam.getDescription();
        List<ShopImg> imgUrls = shopParam.getImgUrls();
        ShopSpec spec = shopParam.getSpec();
        String categoryId = shopParam.getCategoryId();
        //商品id
        String ID = GenerateUUID.create();
        //添加商品信息
        ShopItem item = new ShopItem();
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
        ShopSpec farmSpec = new ShopSpec();
        farmSpec.setItemId(ID);
        farmSpec.setCreatedTime(new Date());
        farmSpec.setPrice(spec.getPrice());
        farmSpec.setUnit(spec.getUnit());
        int spec1 = specMapper.addSpec(farmSpec);

        //添加商品图片
        int img0 = 0;

        for (ShopImg imgUrl : imgUrls) {
            ShopImg imgobj = new ShopImg();
            imgobj.setUrl(imgUrl.getUrl());
            imgobj.setCreatedTime(new Date());
            imgobj.setItemId(ID);
            imgobj.setStatus(1);
            imgobj.setLocation(imgUrl.getLocation());
            int img1 = imgMapper.addImg(imgobj);
            img0 += img1;
        }

        //发送消息
        webSocket.sendMsg("FFDA08446D61498ABD22569E124FECA8","自营商城有新商品来了");
        return i + spec1 + img0 ;
    }

    @Override
    public List<ShopParam> getAllNeedCheck() {
        return mapper.findUncheck();
    }

    @Override
    public int itemInactive(String operator, String id) {
        return mapper.updateSattus(operator,id);
    }

    @Override
    public int checkPass(List<String> ids, String name, String operation, String comment) {
        User user = accountMapper.findUserByUsername(name);
        return mapper.updateManyById(ids,user.getTrueName(),operation,new Date(),comment);
    }

    @Override
    public PageInfo<ShopItemView> search(ParamBean paramBean,String categoryStr) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        String search = ToNullUtil.toNull(paramBean.getSearch());
        String category = ToNullUtil.toNull(categoryStr);
        List<ShopItemView> farmViewList = mapper.findByCondition(search,category);
        return new PageInfo<ShopItemView>(farmViewList);
    }

    @Override
    public ShopParam getItemById(String id) {
        ShopParam result = new ShopParam();
        List<ShopParam> farmParamList = mapper.findById(id);
        for (ShopParam farmParam : farmParamList) {
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
    public int updateItem(ShopParam shopParam, String operator) {
        //更新item表
        String name = shopParam.getName();
        String categoryId = shopParam.getCategoryId();
        String description = shopParam.getDescription();
        String id = shopParam.getId();//primary key

        ShopItem item = new ShopItem();
        item.setId(id);
        item.setName(name);
        item.setCategoryId(categoryId);
        item.setOprerator(operator);
        item.setDescription(description);
        //需要重新审核
        item.setPass(0);

        int i =  mapper.updateItem(item);
        //更新img表
        List<ShopImg> imgUrls = shopParam.getImgUrls();
        int i0 = 0;
        //先删除img再添加
        imgMapper.deleteByItemId(id);
        //添加
        for (ShopImg imgUrl : imgUrls) {
            ShopImg farmImg = new ShopImg();
            farmImg.setUrl(imgUrl.getUrl());
            farmImg.setCreatedTime(new Date());
            farmImg.setItemId(id);
            farmImg.setStatus(1);
            farmImg.setLocation(imgUrl.getLocation());
            int i1 = imgMapper.addImg(farmImg);
            i0 += i1 ;
        }
        //spec表
        ShopSpec spec = shopParam.getSpec();
        ShopSpec farmSpec = new ShopSpec();
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
        return mapper.findCountByShopId(categoryId);
    }

    @Override
    public List<ShopParam> getOneUncheck(String id) {
        return mapper.findUncheckById(id);
    }

    @Override
    public int deleteById(String id) {
        return mapper.deleteById(id);
    }

    @Override
    public PageInfo<ShopItemView> checkHistory(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        String orderBy=paramBean.getSortByFiled() + " "+paramBean.getSortByOrder();
        PageHelper.orderBy(orderBy);
        List<ShopItemView> farmViewList = mapper.findCheckHistory(paramBean.getSearch());
        return new PageInfo<ShopItemView>(farmViewList);
    }

    @Override
    public void updateSort(String id, int sort) {
        mapper.updateSort(id,sort);
    }
}
