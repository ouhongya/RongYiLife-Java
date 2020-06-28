package com.rysh.module.shop.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.shop.beans.ShopItemView;
import com.rysh.module.shop.beans.ShopParam;

import java.util.List;

public interface ShopItemService {
    /**
     * 添加商铺商品
     *
     * @param storeParam
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/20 20:56
     */
    public int addNew(ShopParam storeParam);

    /*
     * 查询所有需要审核的商铺商品
     * @param
     * @return java.util.List<com.rysh.module.farm.beans.FarmItem>
     * @author Hsiang Sun
     * @date 2019/9/6 10:50
     */
    public List<ShopParam> getAllNeedCheck();

    /*
     * 商品下架
     * @param status
     * @param operator
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/6 10:57
     */
    public int itemInactive(String operator, String id);

    /**
     * 商品审核 通过与不通过
     *
     * @param ids
     * @param name
     * @param operation
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/24 16:55
     */
    public int checkPass(List<String> ids, String name, String operation, String comment);

    /**
     * 查询所有的数据(可以根据参数进行搜索)
     *
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.farm.beans.FarmItemView>
     * @author Hsiang Sun
     * @date 2019/9/23 15:26
     */
    public PageInfo<ShopItemView> search(ParamBean paramBean, String category);

    /**
     * 根据Id回显数据
     *
     * @param id
     * @return com.rysh.module.farm.beans.FarmParam
     * @author Hsiang Sun
     * @date 2019/9/23 17:28
     */
    public ShopParam getItemById(String id);

    /**
     * 更新商铺商品信息 用于数据编辑
     *
     * @param storeParam
     * @param operator
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/24 17:41
     */
    public int updateItem(ShopParam storeParam, String operator);

    /**
     * 商品一键上下架
     *
     * @param ids
     * @param name
     * @param operation
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/24 17:46
     */
    public int shelf(List<String> ids, String name, String operation);

    /**
     * 根据商铺id 查询下面挂载的商品数量
     *
     * @param categoryId
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/25 17:12
     */
    public int getItemNumByStoreCategoryId(String categoryId);

    /**
     * 根据id查询没通过审核的商品信息
     *
     * @param
     * @return com.rysh.module.farm.beans.FarmParam
     * @author Hsiang Sun
     * @date 2019/9/27 11:06
     */
    public List<ShopParam> getOneUncheck(String id);

    /**
     * 根据id删除商铺商品
     *
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/27 12:55
     */
    public int deleteById(String id);

    /**
     * 查询商铺审核的历史纪录
     *
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.farm.beans.FarmItemView>
     * @author Hsiang Sun
     * @date 2019/9/27 15:18
     */
    public PageInfo<ShopItemView> checkHistory(ParamBean paramBean);

    /**
     * 更新自营商城排序
     * @param id
	 * @param sort
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/18 15:55
     */
    void updateSort(String id, int sort);
}

