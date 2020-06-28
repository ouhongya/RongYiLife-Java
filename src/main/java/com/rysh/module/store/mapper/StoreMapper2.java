package com.rysh.module.store.mapper;

import com.rysh.module.store.beans.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface StoreMapper2 {
    
    /**
     * 
     * @param login 
     * @return com.rysh.module.store.beans.Store
     * @author Hsiang Sun
     * @date 2019/10/8 16:58
     */
    Store findStoreByLogin(@Param("login") String login);

    /**
     * 按条件进行搜索
     * @param search
     * @return java.util.List<com.rysh.module.store.beans.Store>
     * @author Hsiang Sun
     * @date 2019/10/8 17:21
     */
    List<Store> findBySearch(@Param("search") String search);

    /**
     * 新增商铺商品
     * @param item
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/8 17:52
     */
    int insertNew(StoreItem item);

    /**
     * 查询未通过审核的
     * @param
     * @return
     * @author Hsiang Sun
     * @date 2019/10/9 9:07
     */
    List<StoreParam> findUncheck();

    int updateStatus(@Param("operator") String operator,@Param("id") String id);

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
    List<StoreItemView> findByCondition(@Param("login") String login, @Param("search") String search);

    List<StoreParam> findById(String id);

    int updateItem(StoreItem item);

    int itemUpOrDownShelf(@Param("ids") List<String> ids, @Param("operator") String operator, @Param("operation") String operation);

    int findCountByFarmId(@Param("categoryId") String categoryId);

    List<StoreParam> findUncheckById(@Param("id") String id);

    int deleteById(@Param("id") String id);

    List<StoreItemView> findCheckHistory(@Param("search") String search);

    /**
     * 查询周边商铺info 通过companyId
     * @param companyId
     * @return com.rysh.module.store.beans.StoreInfo
     * @author Hsiang Sun
     * @date 2019/11/1 16:35
     */
    StoreInfo findStoreInfoByCompanyId(@Param("companyId") String companyId);

    /**
     * 更新商铺
     * @param store
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/4 17:56
     */
    void updateStore(Store store);

    /**
     * 断开store_tag_entity中的id
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 9:25
     */
    void deleteTag(String id);

    /**
     * 更新store_tag_entity表
     * @param entity
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 9:28
     */
    void updateStoreTagEntity(StoreTagEntity entity);

    /**
     * 添加商铺相册
     * @param album
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 11:10
     */
    void addAlbum(StoreAlbum album);

    /**
     * 根据id删除相册内容
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 11:28
     */
    void deleteAlbumById(String id);
    
    /**
     * 查询所有的商铺相册
     * @param id 
     * @return java.util.List<com.rysh.module.store.beans.StoreAlbum>
     * @author Hsiang Sun
     * @date 2019/11/5 11:40
     */
    List<StoreAlbum> findAllAlbum(String id);

    /**
     * 通过相册id查询相册内容
     * @param resultId
     * @return com.rysh.module.store.beans.StoreAlbum
     * @author Hsiang Sun
     * @date 2019/11/5 11:56
     */
    StoreAlbum findAlbumById(String resultId);

    /**
     * 查询当前用户拥有多少张图片
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/5 13:49
     */
    int findImgCountById(String id);

    /**
     * 查询当前商铺封面图片数量
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/5 14:27
     */
    StoreAlbum findAlbumCover(String id);

    /**
     * 根据相册的id查询所属的商铺
     * @param id
     * @return java.lang.String
     * @author Hsiang Sun
     * @date 2019/11/5 14:35
     */
    String findStoreIdByAlbumId(String id);

    /**
     * 查询内部账户所能看到的商铺信息
     * @param search
	 * @param itemId
     * @return java.util.List<com.rysh.module.store.beans.StoreItemView>
     * @author Hsiang Sun
     * @date 2019/11/6 11:28
     */
    List<StoreItemView> findInnerContent(@Param("search") String search,@Param("itemId") String itemId);

    String findAlbumCoverByMarketId(String id);

    /**
     * 通过商品id查询缩略图
     * @param id
     * @return java.lang.String
     * @author Hsiang Sun
     * @date 2019/11/6 17:00
     */
    String findStoreItemCover(String id);

  List<String> findAlbumNotCoverByMarketId(String id);

  /**
   * 商铺上下架
   * @param operation
   * @param id
   * @return void
   * @author Hsiang Sun
   * @date 2019/11/8 14:31
   */
  void applyToClient(@Param("operation") String operation,@Param("id") String id);
}