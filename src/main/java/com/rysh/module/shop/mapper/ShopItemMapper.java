package com.rysh.module.shop.mapper;

import com.rysh.module.shop.beans.ShopItem;
import com.rysh.module.shop.beans.ShopItemView;
import com.rysh.module.shop.beans.ShopParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ShopItemMapper {

    /**
     * 新增自营商城商品
     * @param item
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/10 10:10
     */
    int insertNew(ShopItem item);

    /**
     * 查询所有没有通过审核的商品
     * @param
     * @return java.util.List<com.rysh.module.shop.beans.ShopParam>
     * @author Hsiang Sun
     * @date 2019/10/10 10:15
     */
    List<ShopParam> findUncheck();

    int updateSattus(@Param("operator") String operator,@Param("id") String id);

    //一键审核
    int updateManyById(@Param("ids") List<String> ids,
                       @Param("operator") String operator,
                       @Param("operation") String operation,
                       @Param("passTime") Date passTime,
                       @Param("passComment") String passComment
    );
    //查询所有自营商城管理员可以查看的信息
    List<ShopItemView> findByCondition(@Param("search") String search,@Param("category") String category);

    //根据Id回显数据
    List<ShopParam> findById(String id);

    int updateItem(ShopItem item);

    int itemUpOrDownShelf(@Param("ids") List<String> ids, @Param("operator") String operator, @Param("operation") String operation);

    /**
     * 查询分类下商品的数量
     * @param categoryId
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/10 10:44
     */
    int findCountByShopId(String categoryId);

    List<ShopParam> findUncheckById(String id);

    int deleteById(String id);

    List<ShopItemView> findCheckHistory(@Param("search") String search);

    ShopItem findShopItemById(String productItemId);

    ShopItem findShopItemByIdToOrders(String productItemId);

    /**
     * 更新自营商城排序
     * @param id
	 * @param sort
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/18 15:57
     */
    void updateSort(@Param("id") String id,@Param("sort") int sort);

    ShopItem findShopItemByIdPlus(String itemId);
}