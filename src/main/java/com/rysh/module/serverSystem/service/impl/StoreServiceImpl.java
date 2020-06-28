package com.rysh.module.serverSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.City;
import com.rysh.module.community.beans.Community;
import com.rysh.module.community.mapper.CommunityMapper;
import com.rysh.module.serverSystem.beans.*;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.serverSystem.mapper.StoreMapper;
import com.rysh.module.serverSystem.service.StoreService;
import com.rysh.module.utils.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CommunityMapper communityMapper;

    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 添加商铺
     * @param store   商铺对象
     * @return   boolean 是否成功
     * @throws Exception
     */
    public boolean addStore(Store store) throws Exception {
        //设置id  UUID
        store.setId(UUID.randomUUID().toString().toUpperCase().replace("-",""));
        //设置状态值
        store.setStatus(1);
        //设置创建时间
        store.setCreatedTime(new Date());
        store.setFreight(new BigDecimal("0"));
        //添加商铺信息
        storeMapper.addStore(store);
        //给商铺绑定小区
        for (String communityId : store.getCommunityIds()) {
            storeMapper.addCommunityStore(GenerateUUID.create(),communityId,store.getId());
        }
        return true;
    }

    /**
     * 查询所有商铺
     * @param paramBean
     * @return
     */
    public PageInfo<Store> findAllStore(ParamBean paramBean) throws Exception {

        if(paramBean.getPage()<=0){
            paramBean.setPage(1);
        }
        if(paramBean.getSize()<=0){
            paramBean.setSize(5);
        }
        //分页
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<Store> stores=storeMapper.findAllStore();
        //设置商铺的时间字符串和所有者真名和用户名
        for (Store store : stores) {
            store.setCreateTimeStr(sf.format(store.getCreatedTime()));
            List<User> users = accountMapper.findUserByStoreId(store.getId());
            for (User user : users) {
                Owner owner = new Owner();
                owner.setOwnerName(user.getTrueName());
                owner.setOwnerUsername(user.getUsername());
                store.getOwners().add(owner);
            }
           //查询商铺绑定的所有小区
            List<String> communityIds=storeMapper.findCommunityIdByStoreId(store.getId());
            for (String communityId : communityIds) {
                Community community = communityMapper.findCommunityById(communityId);
                //给商品设置绑定的社区名称  区域id  城市id
                StoreCommunity storeCommunity = new StoreCommunity();
                storeCommunity.setCommunityId(community.getId());
                storeCommunity.setCommunityName(community.getName());
                //通过小区id查询区域
                Area area = storeMapper.findAreaByAreaId(community.getAreaId());
                storeCommunity.setAreaId(area.getId());
                //通过区域id查城市
                City city = storeMapper.findCityByCityId(area.getCityId());
                storeCommunity.setCityId(city.getId());
                store.getStoreCommunities().add(storeCommunity);
            }
        }
        PageInfo<Store> pageInfo = new PageInfo<>(stores);
        return pageInfo;
    }

    /**
     * 通过id查询商铺   （编辑商铺回显）
     * @param storeId
     * @return
     * @throws Exception
     */
    public Store findStoreById(String storeId) throws Exception {
        Store store = storeMapper.findStoreById(storeId);
        //给商铺设置所属者真名和用户名
        List<User> users = accountMapper.findUserByStoreId(storeId);
        for (User user : users) {
            Owner owner = new Owner();
            owner.setOwnerName(user.getTrueName());
            owner.setOwnerUsername(user.getUsername());
            store.getOwners().add(owner);
        }
        //给商品设置绑定的社区名称  区域id  城市id
        //查询商铺绑定的所有小区
        List<String> communityIds=storeMapper.findCommunityIdByStoreId(store.getId());
        for (String communityId : communityIds) {
            Community community = communityMapper.findCommunityById(communityId);
            //给商品设置绑定的社区名称  区域id  城市id
            StoreCommunity storeCommunity = new StoreCommunity();
            storeCommunity.setCommunityId(community.getId());
            storeCommunity.setCommunityName(community.getName());
            //通过小区id查询区域
            Area area = storeMapper.findAreaByAreaId(community.getAreaId());
            storeCommunity.setAreaId(area.getId());
            //通过区域id查城市
            City city = storeMapper.findCityByCityId(area.getCityId());
            storeCommunity.setCityId(city.getId());
            store.getStoreCommunities().add(storeCommunity);
        }
        return store;
    }

    /**
     * 编辑商铺基本信息
     * @param store
     * @return
     * @throws Exception
     */
    public boolean updateStore(Store store) throws Exception {
        //修改商铺信息
        storeMapper.updateStore(store);
        //删除原来绑定的小区
        storeMapper.deleteCommunityStore(store.getId());
        //修改商铺绑定小区
        for (String communityId : store.getCommunityIds()) {
            //添加新的小区id
            storeMapper.addCommunityStore(GenerateUUID.create(),communityId,store.getId());
        }
        return true;
    }

    /**
     * 删除商铺  并断开和用户的外键关系
     * @param storeId
     * @return
     */
    public boolean deleteStore(String storeId) throws Exception {
        storeMapper.updateCompany(storeId);
        OperatorAndStore os = new OperatorAndStore();
        os.setStoreId(storeId);
        storeMapper.deleteStore(storeId);
        return true;
    }

    /**
     * 按条件搜索商铺
     * @param paramBean  当前页

     * @return
     */
    public PageInfo<Store> searchAllStore(ParamBean paramBean) {

        if(paramBean.getPage()<=0){
            paramBean.setPage(1);
        }
        if(paramBean.getSize()<=0){
            paramBean.setSize(5);
        }
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<Store> stores=storeMapper.searchAllRole(paramBean.getSearch());
        //设置商铺的时间字符串和所有者真名和用户名
        for (Store store : stores) {
            store.setCreateTimeStr(sf.format(store.getCreatedTime()));
            List<User> users = accountMapper.findUserByStoreId(store.getId());
            for (User user : users) {
                Owner owner = new Owner();
                owner.setOwnerName(user.getTrueName());
                owner.setOwnerUsername(user.getUsername());
                store.getOwners().add(owner);
            }

            //给商品设置绑定的社区名称  区域id  城市id
            //查询商铺绑定的所有小区
            List<String> communityIds=storeMapper.findCommunityIdByStoreId(store.getId());
            for (String communityId : communityIds) {
                Community community = communityMapper.findCommunityById(communityId);
                //给商品设置绑定的社区名称  区域id  城市id
                StoreCommunity storeCommunity = new StoreCommunity();
                storeCommunity.setCommunityId(community.getId());
                storeCommunity.setCommunityName(community.getName());
                //通过小区id查询区域
                Area area = storeMapper.findAreaByAreaId(community.getAreaId());
                storeCommunity.setAreaId(area.getId());
                //通过区域id查城市
                City city = storeMapper.findCityByCityId(area.getCityId());
                storeCommunity.setCityId(city.getId());
                store.getStoreCommunities().add(storeCommunity);
            }
        }
        PageInfo<Store> storePageInfo = new PageInfo<>(stores);
        return storePageInfo;
    }
}
