package com.rysh.module.shop.mapper;

import com.rysh.module.shop.beans.Shop;
import com.rysh.module.shop.beans.ShopItem;
import com.rysh.module.shop.beans.ShopItemView;
import com.rysh.module.shop.beans.ShopParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ShopMapper {
    
    /**
     * 
     * @param login 
     * @return com.rysh.module.Shop.beans.Shop
     * @author Hsiang Sun
     * @date 2019/10/8 16:58
     */
    Shop findShopByLogin(@Param("login") String login);

    /**
     * 按条件进行搜索
     * @param search
     * @return java.util.List<com.rysh.module.Shop.beans.Shop>
     * @author Hsiang Sun
     * @date 2019/10/8 17:21
     */
    List<Shop> findBySearch(@Param("search") String search);

    /**
     * 新增商铺商品
     * @param item
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/8 17:52
     */
    int insertNew(ShopItem item);

    /**
     * 查询未通过审核的
     * @param
     * @return
     * @author Hsiang Sun
     * @date 2019/10/9 9:07
     */
    List<ShopParam> findUncheck();

    int updateStatus(@Param("operator") String operator, @Param("id") String id);

  /**
   * 一键审核通过
   * @param ids
   * @param operator
   * @param operation
   * @param passTime
   * @param passComment
   * @return int
   * @author Hsiang Sun
   * @date 2019/10/9 9:28
   */
    int updateManyById(@Param("ids") List<String> ids,
                       @Param("operator") String operator,
                       @Param("operation") String operation,
                       @Param("passTime") Date passTime,
                       @Param("passComment") String passComment
    );

    //查询所有农场主可以查看的信息
    List<ShopItemView> findByCondition(@Param("login") String login, @Param("search") String search);

    Shop findById(@Param("id") String id);

    int updateItem(ShopItem item);

    int itemUpOrDownShelf(@Param("ids") List<String> ids, @Param("operator") String operator, @Param("operation") String operation);

    int findCountByFarmId(@Param("categoryId") String categoryId);

    List<ShopParam> findUncheckById(@Param("id") String id);

    int deleteById(@Param("id") String id);

    List<ShopItemView> findCheckHistory(@Param("search") String search);

    /**
     * 查询当前自营商城的数量
     * @param
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/9 17:09
     */
    int shopCount();

    /**
     * 新增商城
     * @param shop
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/10 9:16
     */
    int insert(Shop shop);

    /**
     * 更新自营商城
     * @param shop
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/10 9:56
     */
    int update(Shop shop);

    int updateStatusById(String id);

    Shop findByIdToOrders(String shopId);
}