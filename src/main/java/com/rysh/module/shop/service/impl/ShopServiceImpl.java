package com.rysh.module.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.DisplayInfo;
import com.rysh.module.serverSystem.beans.Owner;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.serverSystem.mapper.StoreMapper;
import com.rysh.module.shop.beans.Shop;
import com.rysh.module.shop.mapper.ShopMapper;
import com.rysh.module.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper mapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private StoreMapper storeMapper;


    @Override
    public DisplayInfo grangeInfo(String login) {
        DisplayInfo info = new DisplayInfo();
        //查询用户基本信息
        User loginUser = accountMapper.findUserByUsername(login);
        //查询商铺基本信息
        Shop shop = mapper.findShopByLogin(login);
        List<User> users = accountMapper.findUserByStoreId(shop.getId());
        info.setAccount(users);
        info.setAddress(shop.getAddress());
        info.setName(shop.getName());
        info.setTrueName(loginUser.getTrueName());
        return info;
    }

    @Override
    public PageInfo<Shop> search(ParamBean param) {
        PageHelper.startPage(param.getPage(),param.getSize());
        PageHelper.orderBy(param.getSortByFiled() + " "+param.getSortByOrder());
        List<Shop> farmAndUserList = mapper.findBySearch(param.getSearch());
        return getUserInfo(farmAndUserList);
    }

    @Override
    public Shop getStoreByLogin(String login) {
        return mapper.findShopByLogin(login);
    }

    @Override
    public int addNewShop(Shop shop) {
        //先判断是否已经存在自营商城
        int count = mapper.shopCount();
        if (count > 0){
            return count+1;
        }
        shop.setCreatedTime(new Date());
        return mapper.insert(shop);
    }

    @Override
    public int shopCount() {
        return mapper.shopCount();
    }

    @Override
    public Shop shopById(String id) {
        Shop shop = mapper.findById(id);
        List<User> userList = accountMapper.findUserByStoreId(id);
        for (User user : userList) {
            Owner owner = new Owner();
            String trueName = user.getTrueName();
            String username = user.getUsername();
            owner.setOwnerUsername(username);
            owner.setOwnerName(trueName);
            shop.getOwners().add(owner);
        }
        return shop;
    }

    @Override
    public int updateShop(Shop shop) {
        return mapper.update(shop);
    }

    @Override
    @Transactional
    public int deleteShop(String id) {
        storeMapper.updateCompany(id);
        return mapper.updateStatusById(id);
    }

    //获取每一个商铺Id去查询所属用户
    private PageInfo<Shop> getUserInfo(List<Shop> farmAndUserList) {
        for (Shop farmAndUser : farmAndUserList) {
            String id = farmAndUser.getId();
            List<User> userList = accountMapper.findUserByStoreId(id);
            for (User user : userList) {
                Owner owner = new Owner();
                String trueName = user.getTrueName();
                String username = user.getUsername();
                owner.setOwnerUsername(username);
                owner.setOwnerName(trueName);
                farmAndUser.getOwners().add(owner);
            }
        }
        return new PageInfo<>(farmAndUserList);
    }
}
