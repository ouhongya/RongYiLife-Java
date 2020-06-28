package com.rysh.module.farm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.*;
import com.rysh.module.farm.mapper.FarmItemMapper;
import com.rysh.module.farm.mapper.FarmSpecMapper;
import com.rysh.module.farm.service.FarmItemService;
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

@Log4j2
@Service
public class FarmItemServiceImpl implements FarmItemService {

    @Autowired
    private FarmItemMapper mapper;

    @Autowired
    private FarmImgServiceImpl imgService;

    @Autowired
    private FarmSpecMapper specMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private WebSocket webSocket;

    /**
     * 添加农场商品
     * @param farmParam
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/20 20:56
     */
    @Transactional
    public int addNew(FarmParam farmParam) {

        String name = farmParam.getName();
        String description = farmParam.getDescription();
        List<FarmImg> imgUrls = farmParam.getImgUrls();
        FarmSpec spec = farmParam.getSpec();
        String categoryId = farmParam.getCategoryId();
        //商品id
        String ID = GenerateUUID.create();
        //添加商品信息
        FarmItem farmItem = new FarmItem();
        farmItem.setId(ID);
        farmItem.setName(name);
        farmItem.setCategoryId(categoryId);
        farmItem.setStatus(1);
        farmItem.setOprerator("system");
        farmItem.setCreatedTime(new Date());
        farmItem.setDescription(description);
        farmItem.setState(0);
        farmItem.setPass(0);
        int i = mapper.insertNew(farmItem);

        //添加规格价格信息
        FarmSpec farmSpec = new FarmSpec();
        farmSpec.setItemId(ID);
        farmSpec.setCreatedTime(new Date());
        farmSpec.setPrice(spec.getPrice());
        farmSpec.setUnit(spec.getUnit());
        int spec1 = specMapper.addSpec(farmSpec);

        //添加商品图片
        int img0 = 0;

        for (FarmImg imgUrl : imgUrls) {
            FarmImg imgobj = new FarmImg();
            imgobj.setUrl(imgUrl.getUrl());
            imgobj.setCreatedTime(new Date());
            imgobj.setFarmItemId(ID);
            imgobj.setStatus(1);
            imgobj.setLocation(imgUrl.getLocation());
            int img1 = imgService.addImg(imgobj);
            img0 += img1;
        }
        //发送消息
        webSocket.sendMsg("3EC3A66347C947FC9AADC189EC1601A7","青蛙农场有新的商品来了");
        return i + spec1 + img0 ;
    }

    /*
     * 查询所有需要审核的农场商品
     * @param
     * @return java.util.List<com.rysh.module.farm.beans.FarmItem>
     * @author Hsiang Sun
     * @date 2019/9/6 10:50
     */
    public List<FarmParam> getAllNeedCheck() {
        return mapper.findUncheck();
    }

    /*
     * 商品下架
     * @param status
	 * @param operator
	 * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/6 10:57
     */
    public int itemInactive(String operator,String id){
        int i = mapper.updateSattus(operator,id);
        return i;
    }

    /**
     * 商品审核 通过与不通过
     * @param ids
	 * @param name
	 * @param operation
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/24 16:55
     */
    public int checkPass(List<String> ids, String name,String operation,String comment) {
        //查询当前进行操作人的真实姓名
        User user = accountMapper.findUserByUsername(name);
        return mapper.updateManyById(ids,user.getTrueName(),operation,new Date(),comment);
    }

    /**
     * 查询所有的数据(可以根据参数进行搜索)
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.farm.beans.FarmItemView>
     * @author Hsiang Sun
     * @date 2019/9/23 15:26
     */
    public PageInfo<FarmItemView> search(ParamBean paramBean,String login,String itemId) {
        User user = accountMapper.findUserByUsername(login);
        if (user.getIsInsider() == UserType.内部账户.typeCode()){
            PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
            PageHelper.orderBy("fi."+paramBean.getSortByFiled() +" "+paramBean.getSortByOrder());
            List<FarmItemView> farmViewList = mapper.findInnerContent(paramBean.getSearch(),itemId);
            return new PageInfo<FarmItemView>(farmViewList);
        }else if (user.getIsInsider() == UserType.农场账户.typeCode()){
            PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
            String orderBy="t1."+paramBean.getSortByFiled() + " "+paramBean.getSortByOrder();
            PageHelper.orderBy(orderBy);
            List<FarmItemView> farmViewList = mapper.findByCondition(login,paramBean.getSearch());
            return new PageInfo<FarmItemView>(farmViewList);
        }else {
            log.error("当前用户{} 试图获取农场商品列表信息",login);
            return null;
        }
    }
    /**
     * 根据Id回显数据
     * @param id
     * @return com.rysh.module.farm.beans.FarmParam
     * @author Hsiang Sun
     * @date 2019/9/23 17:28
     */
    public FarmParam getItemById(String id) {

        FarmParam result = new FarmParam();

        List<FarmParam> farmParamList = mapper.findById(id);
        for (FarmParam farmParam : farmParamList) {
            result.setId(farmParam.getId());
            result.setDescription(farmParam.getDescription());
            result.setCategoryId(farmParam.getCategoryId());
            result.setName(farmParam.getName());
            result.setImgUrls(farmParam.getImgUrls());
            result.setSpec(farmParam.getSpec());
        }
        return result;
    }

    /**
     * 更新农场商品信息 用于数据编辑
     * @param farmParam
	 * @param operator
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/24 17:41
     */
    public int updateItem(FarmParam farmParam,String operator) {
        //更新item表
        String name = farmParam.getName();
        String categoryId = farmParam.getCategoryId();
        String description = farmParam.getDescription();
        String id = farmParam.getId();//primary key

        FarmItem item = new FarmItem();
        item.setId(id);
        item.setName(name);
        item.setCategoryId(categoryId);
        item.setOprerator(operator);
        item.setDescription(description);
        //需要重新审核
        item.setPass(0);

        int i =  mapper.updateItem(item);
        //更新img表
        List<FarmImg> imgUrls = farmParam.getImgUrls();
        int i0 = 0;
        //先删除img再添加
        imgService.deleteByItemId(id);
        //添加
        for (FarmImg imgUrl : imgUrls) {
            FarmImg farmImg = new FarmImg();
            farmImg.setUrl(imgUrl.getUrl());
            farmImg.setCreatedTime(new Date());
            farmImg.setFarmItemId(id);
            farmImg.setStatus(1);
            farmImg.setLocation(imgUrl.getLocation());
            int i1 = imgService.updateImg(farmImg);
            i0 += i1 ;
        }
        //spec表
        FarmSpec spec = farmParam.getSpec();
        FarmSpec farmSpec = new FarmSpec();
        farmSpec.setPrice(spec.getPrice());
        farmSpec.setUnit(spec.getUnit());
        farmSpec.setItemId(id);

        int i3 = specMapper.updateSpec(farmSpec);
        return i + i0 + i3;
    }
    /**
     * 商品一键上下架
     * @param ids
	 * @param name
	 * @param operation
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/24 17:46
     */
    public int shelf(List<String> ids, String name, String operation) {
        User user = accountMapper.findUserByUsername(name);
        return mapper.itemUpOrDownShelf(ids,user.getTrueName(),operation);
    }

    /**
     * 根据农场id 查询下面挂载的商品数量
     * @param categoryId
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/25 17:12
     */
    public int getItemNumByFarmCateegoryId(String categoryId){
        return mapper.findCountByFarmId(categoryId);
    }

    /**
     * 根据id查询没通过审核的商品信息
     * @param
     * @return com.rysh.module.farm.beans.FarmParam
     * @author Hsiang Sun
     * @date 2019/9/27 11:06
     */
    public List<FarmParam> getOneUncheck(String id) {
        return mapper.findUncheckById(id);
    }

    /**
     * 根据id删除农场商品
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/27 12:55
     */
    public int deleteById(String id) {
       return mapper.deleteById(id);
    }

    /**
     * 查询农场审核的历史纪录
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.farm.beans.FarmItemView>
     * @author Hsiang Sun
     * @date 2019/9/27 15:18
     */
    public PageInfo<FarmItemView> checkHistory(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        String orderBy=paramBean.getSortByFiled() + " "+paramBean.getSortByOrder();
        PageHelper.orderBy(orderBy);
        List<FarmItemView> farmViewList = mapper.findCheckHistory(paramBean.getSearch());
        return new PageInfo<FarmItemView>(farmViewList);
    }
}
