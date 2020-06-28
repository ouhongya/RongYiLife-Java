package com.rysh.module.productCustomize.mapper;

import com.rysh.module.productCustomize.beans.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCustomizeMapper {

    /**
     * 添加社群定制
     * @param customize
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/14 16:50
     */
    void addProductCustom(ProductCustomize customize);

    /**
     * 添加社群分类内容
     * @param customizeContent
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/14 16:53
     */
    void addProductCustomContent(ProductCustomizeContent customizeContent);

    /**
     * 查看所有的综合信息
     * @param search
     * @return java.util.List<com.rysh.module.productCustomize.beans.ProductCustomizeRo>
     * @author Hsiang Sun
     * @date 2019/11/15 9:34
     */
    List<ProductCustomizeRo> findAll(@Param("search") String search);

    /**
     * 社群定制的上下架
     * @param id
	 * @param operation
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/15 10:28
     */
    void applyToClient(@Param("id") String id,@Param("operation") String operation);

    /**
     * 通过id查询回显数据
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/15 10:40
     */
    ProductCustomizeRo findOneById(String id);

    /**
     * 更新
     * @param productCustomize
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/15 11:12
     */
    void update(ProductCustomize productCustomize);

    /**
     * 根据custom id删除内容
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/15 11:15
     */
    void deleteContentByCustomId(String id);


    /**
     * 更新排序
     * @param id
	 * @param sort
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/15 11:59
     */
    void updateSort(@Param("id") String id,@Param("sort") String sort);

    /**
     * 查询留言
     * @param id
     * @return com.rysh.module.productCustomize.beans.ProductCustomizeContact
     * @author Hsiang Sun
     * @date 2019/11/15 13:50
     */
    List<ProductCustomizeContact> findContact(String id);
    
    /**
     * 
     * @param checkPass
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/15 14:40
     */
    void checkPass(@Param("checkPass") CheckPass checkPass);

    /**
     * 查询所有的未审核内容
     * @param
     * @return java.util.List<com.rysh.module.productCustomize.beans.ProductCustomizeVo>
     * @author Hsiang Sun
     * @date 2019/11/15 14:58
     */
    List<ProductCustomizeVo> findAllUncheck();

    /**
     * 历史审核记录
     * @param
     * @return java.util.List<com.rysh.module.productCustomize.beans.ProductCustomizeVo>
     * @author Hsiang Sun
     * @date 2019/11/15 15:13
     */
    List<ProductCustomize> checkHistory(@Param("search") String search);
}
