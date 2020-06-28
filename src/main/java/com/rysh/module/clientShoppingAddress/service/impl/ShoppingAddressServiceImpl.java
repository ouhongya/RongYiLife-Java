package com.rysh.module.clientShoppingAddress.service.impl;

import com.github.pagehelper.PageHelper;
import com.rysh.module.clientShoppingAddress.beans.ShoppingAddress;
import com.rysh.module.clientShoppingAddress.mapper.ShoppingAddressMapper;
import com.rysh.module.clientShoppingAddress.service.ShoppingAddressService;
import com.rysh.module.commonService.beans.ParamBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ShoppingAddressServiceImpl implements ShoppingAddressService {

        @Autowired
        private ShoppingAddressMapper shoppingAddressMapper;

        //电话号码判读 正则表达式
        private String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
    /**
     * 添加收货地址
     * @param shoppingAddress   收货地址对象
     */
    @Override
    public String addAddress(ShoppingAddress shoppingAddress) throws Exception {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(shoppingAddress.getPhone());
        if(matcher.matches()){
            //设置收货地址id
            shoppingAddress.setId(UUID.randomUUID().toString().replace("-","").toUpperCase());
            //设置收货地址创建时间
            shoppingAddress.setCreatedTime(new Date());
            //添加收获地址
            shoppingAddressMapper.addAddress(shoppingAddress);
            //判断用户是否设置默认收货地址
            if(shoppingAddress.getState()==1){
                //将用户之前设置的默认收货地址清除
                shoppingAddressMapper.updateDefaultShoppingAddressByUserId(shoppingAddress.getUserId());
                //设置当前收货地址为默认收货地址
                shoppingAddressMapper.updateDefaultShoppingAddressById(shoppingAddress.getId());
            }
            return shoppingAddress.getId();
        }else {
            return "滚";
        }

    }

    /**
     * 查询该用户所有收货地址
     * @param userId
     * @param paramBean
     * @return
     */
    @Override
    public List<ShoppingAddress> findAllShoppingAddress(String userId, ParamBean paramBean) throws Exception {
        //设置分页参数
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        //查询该用户所有收货地址
        List<ShoppingAddress> shoppingAddresses=shoppingAddressMapper.findAllShoppingAddress(userId);


        return shoppingAddresses;
    }

    /**
     *通过id查询收货地址  （编辑回显）
     * @param shoppingAddressId
     * @return
     * @throws Exception
     */
    @Override
    public ShoppingAddress findShoppingAddressById(String shoppingAddressId) throws Exception {
        //通过id查询收获地址
        ShoppingAddress shoppingAddress=shoppingAddressMapper.findShoppingAddressById(shoppingAddressId);
        return shoppingAddress;
    }

    /**
     * 删除收货地址
     * @param shoppingAddressId
     * @throws Exception
     */
    @Override
    public Boolean deleteAddress(String shoppingAddressId,String uid) throws Exception {
        //查询收货地址对象
        ShoppingAddress shoppingAddress = shoppingAddressMapper.findShoppingAddressById(shoppingAddressId);
        if(shoppingAddress.getUserId().equals(uid)){
            //软删除收获地址
            shoppingAddressMapper.deleteShoppingAddress(shoppingAddressId);
            return true;
        }else {
            return false;
        }

    }

    /**
     * 编辑收货地址
     * @param shoppingAddress
     * @throws Exception
     */
    @Override
    public String updateAddress(ShoppingAddress shoppingAddress) throws Exception {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(shoppingAddress.getPhone());
        if(matcher.matches()){
            ShoppingAddress shoppingAddress1 = shoppingAddressMapper.findShoppingAddressById(shoppingAddress.getId());
            if(shoppingAddress1.getUserId().equals(shoppingAddress.getUserId())){
                //如果用户编辑了默认收货地址
                if(shoppingAddress.getState()==1){
                    //清空用户原来的默认收货地址
                    shoppingAddressMapper.updateDefaultShoppingAddressByUserId(shoppingAddress.getUserId());
                }
                //编辑收获地址
                shoppingAddressMapper.updateShoppingAddress(shoppingAddress);
                return "来";
            }else {
                return "爬";
            }
        }else {
            return "滚";
        }
    }
}
