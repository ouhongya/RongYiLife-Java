package com.rysh.module.farm.mapper;

import com.rysh.module.farm.beans.FarmItem;
import com.rysh.module.farm.beans.FarmItemView;
import com.rysh.module.farm.beans.FarmParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface FarmItemMapper {
    int insertNew(FarmItem farmItem);

    List<FarmItem> findAllByStatus();

    String findPrimaryKey(Date date);

    int updateSattus(@Param("operator") String operator,@Param("id") String id);

    //查询所有没有通过审核的商品
    List<FarmParam> findUncheck();
    //一键审核
    int updateManyById(@Param("ids") List<String> ids,
                       @Param("operator") String operator,
                       @Param("operation") String operation,
                       @Param("passTime") Date passTime,
                       @Param("passComment") String passComment
    );

    //查询所有农场主可以查看的信息
    List<FarmItemView> findByCondition(@Param("login") String login,@Param("search") String search);

    //根据Id回显数据
    List<FarmParam> findById(String id);

    int updateItem(FarmItem item);

    int itemUpOrDownShelf(@Param("ids") List<String> ids, @Param("operator") String operator, @Param("operation") String operation);

    int findCountByFarmId(@Param("categoryId") String categoryId);

    FarmItem findFarmItemById(String goodsId);

    List<FarmParam> findUncheckById(@Param("id") String id);

    int deleteById(@Param("id") String id);
    //查询审核的历史纪录
    List<FarmItemView> findCheckHistory(@Param("search") String search);

    List<FarmItem> findFarmItemByCategoryId(@Param("categoryId") String categoryId);

    /**
     * 查询内部账户可以看见的农场商品列表信息
     * @param itemId
     * @return java.util.List<com.rysh.module.farm.beans.FarmItemView>
     * @author Hsiang Sun
     * @date 2019/10/24 14:14
     */
    List<FarmItemView> findInnerContent(@Param("search") String search,@Param("itemId") String itemId);

    FarmItem findFarmItemByIdToOrders(String productItemId);

    FarmItem findFarmItemByIdPlus(String itemId);
}
