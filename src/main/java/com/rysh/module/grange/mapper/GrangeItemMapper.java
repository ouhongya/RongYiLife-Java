package com.rysh.module.grange.mapper;

import com.rysh.module.grange.beans.GrangeItemView;
import com.rysh.module.grange.beans.GrangeItem;
import com.rysh.module.grange.beans.GrangeParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GrangeItemMapper {

    int insertNew(GrangeItem grangeItem);

    List<GrangeItem> findAllByStatus();

    int updateSattus(@Param("operator") String operator,@Param("id") String id);

    //查询所有没有通过审核的商品
    List<GrangeParam> findUncheck();
    //一键审核
    int updateManyById(@Param("ids") List<String> ids,
                       @Param("operator") String operator,
                       @Param("operation") String operation,
                       @Param("passTime") Date passTime,
                       @Param("passComment") String passComment
    );

    //查询所有农场主可以查看的信息
    List<GrangeItemView> findByCondition(@Param("login") String login, @Param("search") String search);

    //根据Id回显数据
    List<GrangeParam> findById(String id);

    int updateItem(GrangeItem item);

    int itemUpOrDownShelf(@Param("ids") List<String> ids, @Param("operator") String operator, @Param("operation") String operation);

    int findCountByFarmId(@Param("categoryId") String categoryId);

    GrangeItem findFarmItemById(String goodsId);

    List<GrangeParam> findUncheckById(@Param("id") String id);

    int deleteById(@Param("id") String id);
    //查询审核的历史纪录
    List<GrangeItemView> findCheckHistory(@Param("search") String search);

    List<GrangeItem> findGrangeItemByCategoryId(@Param("categoryId") String categoryId);

    /**
     * 内部账号查看农庄商品信息
     * @param search
	 * @param itemId
     * @return java.util.List<com.rysh.module.grange.beans.GrangeItemView>
     * @author Hsiang Sun
     * @date 2019/10/24 15:29
     */
    List<GrangeItemView> findInnerContent(@Param("search") String search,@Param("itemId") String itemId);

    GrangeItem findFarmItemByIdToOrders(String productItemId);

    GrangeItem findGrangeItemByIdPlus(String itemId);
}