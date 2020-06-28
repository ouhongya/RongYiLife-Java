package com.rysh.module.grange.mapper;

import com.rysh.module.farm.beans.DisplayInfo;
import com.rysh.module.grange.beans.Grange;
import com.rysh.module.grange.beans.GrangeAlbum;
import com.rysh.module.grange.beans.GrangeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GrangeMapper {

    int insert(Grange grange);

    List<Grange> findAll();

    GrangeInfo findGrangeInfoById(@Param("id") String id);

    int update(Grange grange);

    //int insertCategory(GrangeCategory category);

    int updateStatusById(String id);
    //用于查询所有和有条件的搜索
    List<Grange> findByCondition(@Param("condition") String searchCondition);

    Grange findGrangeByLogin(@Param("login") String login,@Param("itemId") String itemId);

    /**
     * 根据id查询农庄
     * @param id
     * @return com.rysh.module.grange.beans.Grange
     * @author Hsiang Sun
     * @date 2019/10/21 10:21
     */
    Grange findById(String id);

    /**
     * 更新农庄基本信息
     * @param grangeInfo
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/21 11:12
     */
    void updateGrangeInfo(GrangeInfo grangeInfo);

    void updateScoreById(@Param("marketId") String marketId, @Param("usedSorce") Integer usedSorce);

    /**
     * 查询当前农庄是否有封面图
     * @param id
     * @return java.lang.Integer
     * @author Hsiang Sun
     * @date 2019/10/28 15:47
     */
    Integer hasCoverImg(String id);

    DisplayInfo findDisplayInfoById(String id);

    /**
     * 将当前农庄上架到客户端
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/28 15:49
     */
    int updateStatus(@Param("id") String id,@Param("operation") String operation);

    Grange findByIdToOrders(String grangeId);

    /**
     * 查询当前用户拥有多少张图片
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/5 13:49
     */
    int findImgCountById(String id);

    /**
     * 通过相册id查询相册内容
     * @param resultId
     * @return com.rysh.module.store.beans.StoreAlbum
     * @author Hsiang Sun
     * @date 2019/11/5 11:56
     */
    GrangeAlbum findAlbumById(String resultId);

    /**
     * 查询当前商铺封面图片数量
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/5 14:27
     */
    GrangeAlbum findAlbumCover(String id);

    /**
     * 根据id删除相册内容
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 11:28
     */
    void deleteAlbumById(String id);

    /**
     * 根据相册的id查询所属的农庄
     * @param id
     * @return java.lang.String
     * @author Hsiang Sun
     * @date 2019/11/5 14:35
     */
    String findStoreIdByAlbumId(String id);

    /**
     * 查询所有的农庄相册
     * @param id
     * @return java.util.List<com.rysh.module.store.beans.StoreAlbum>
     * @author Hsiang Sun
     * @date 2019/11/5 11:40
     */
    List<GrangeAlbum> findAllAlbum(String id);

    /**
     * 添加农庄相册
     * @param album
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 11:10
     */
    void addAlbum(GrangeAlbum album);

    /**
     * 更新农庄排序字段
     * @param id
	 * @param sortValue
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/18 13:35
     */
    void updateSort(@Param("id") String id,@Param("sort") int sortValue);

    List<Grange> findAllPlus(@Param("search") String search);
}