package com.rysh.module.grange.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.grange.beans.GrangeItemView;
import com.rysh.module.grange.beans.GrangeImg;
import com.rysh.module.grange.beans.GrangeItem;
import com.rysh.module.grange.beans.GrangeParam;
import com.rysh.module.grange.beans.GrangeSpec;
import com.rysh.module.grange.mapper.GrangeItemMapper;
import com.rysh.module.grange.mapper.GrangeSpecMapper;
import com.rysh.module.grange.service.GrangeImgService;
import com.rysh.module.grange.service.GrangeItemService;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
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
public class GrangeItemServiceImpl implements GrangeItemService {

    @Autowired
    private GrangeItemMapper mapper;

    @Autowired
    private GrangeSpecMapper specMapper;

    @Autowired
    private GrangeImgService imgService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public int addNew(GrangeParam farmParam) {
        String name = farmParam.getName();
        String description = farmParam.getDescription();
        List<GrangeImg> imgUrls = farmParam.getImgUrls();
        GrangeSpec spec = farmParam.getSpec();
        String categoryId = farmParam.getCategoryId();
        //商品id
        String ID = GenerateUUID.create();
        //添加商品信息
        GrangeItem item = new GrangeItem();
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
        GrangeSpec farmSpec = new GrangeSpec();
        farmSpec.setItemId(ID);
        farmSpec.setCreatedTime(new Date());
        farmSpec.setPrice(spec.getPrice());
        farmSpec.setUnit(spec.getUnit());
        int spec1 = specMapper.addSpec(farmSpec);

        //添加商品图片
        int img0 = 0;

        for (GrangeImg imgUrl : imgUrls) {
            GrangeImg imgobj = new GrangeImg();
            imgobj.setUrl(imgUrl.getUrl());
            imgobj.setCreatedTime(new Date());
            imgobj.setGrangeItemId(ID);
            imgobj.setStatus(1);
            imgobj.setLocation(imgUrl.getLocation());
            int img1 = imgService.addImg(imgobj);
            img0 += img1;
        }
        //发送消息
        webSocket.sendMsg("5B05B7EED998406E9ED81026A1B838D9","青蛙农庄有新的商品来了");
        return i + spec1 + img0 ;
    }

    @Override
    public List<GrangeParam> getAllNeedCheck() {
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
    public PageInfo<GrangeItemView> search(ParamBean paramBean, String login,String itemId) {
        User user = accountMapper.findUserByUsername(login);
        if (user.getIsInsider() == UserType.农庄账户.typeCode()){
            PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
            String orderBy="t1."+paramBean.getSortByFiled() + " "+paramBean.getSortByOrder();
            PageHelper.orderBy(orderBy);
            List<GrangeItemView> farmViewList = mapper.findByCondition(login,paramBean.getSearch());
            return new PageInfo<GrangeItemView>(farmViewList);
        }else if (user.getIsInsider() == UserType.内部账户.typeCode()){
            PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
            PageHelper.orderBy("fi."+paramBean.getSortByFiled() +" "+paramBean.getSortByOrder());
            List<GrangeItemView> farmViewList = mapper.findInnerContent(paramBean.getSearch(),itemId);
            return new PageInfo<GrangeItemView>(farmViewList);
        }else {
            log.error("当前用户{} 试图获取农庄商品列表信息",login);
            return null;
        }
    }

    @Override
    public GrangeParam getItemById(String id) {
        GrangeParam result = new GrangeParam();
        List<GrangeParam> farmParamList = mapper.findById(id);
        for (GrangeParam farmParam : farmParamList) {
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
    public int updateItem(GrangeParam farmParam, String operator) {
        //更新item表
        String name = farmParam.getName();
        String categoryId = farmParam.getCategoryId();
        String description = farmParam.getDescription();
        String id = farmParam.getId();//primary key

        GrangeItem item = new GrangeItem();
        item.setId(id);
        item.setName(name);
        item.setCategoryId(categoryId);
        item.setOprerator(operator);
        item.setDescription(description);
        //需要重新审核
        item.setPass(0);

        int i =  mapper.updateItem(item);
        //更新img表
        List<GrangeImg> imgUrls = farmParam.getImgUrls();
        int i0 = 0;
        //先删除img再添加
        imgService.deleteByItemId(id);
        //添加
        for (GrangeImg imgUrl : imgUrls) {
            GrangeImg farmImg = new GrangeImg();
            farmImg.setUrl(imgUrl.getUrl());
            farmImg.setCreatedTime(new Date());
            farmImg.setGrangeItemId(id);
            farmImg.setStatus(1);
            farmImg.setLocation(imgUrl.getLocation());
            int i1 = imgService.updateImg(farmImg);
            i0 += i1 ;
        }
        //spec表
        GrangeSpec spec = farmParam.getSpec();
        GrangeSpec farmSpec = new GrangeSpec();
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
    public int getItemNumByFarmCateegoryId(String categoryId) {
        return mapper.findCountByFarmId(categoryId);
    }

    @Override
    public List<GrangeParam> getOneUncheck(String id) {
        return mapper.findUncheckById(id);
    }

    @Override
    public int deleteById(String id) {
        return mapper.deleteById(id);
    }

    @Override
    public PageInfo<GrangeItemView> checkHistory(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        String orderBy=paramBean.getSortByFiled() + " "+paramBean.getSortByOrder();
        PageHelper.orderBy(orderBy);
        List<GrangeItemView> farmViewList = mapper.findCheckHistory(paramBean.getSearch());
        return new PageInfo<GrangeItemView>(farmViewList);
    }
}
