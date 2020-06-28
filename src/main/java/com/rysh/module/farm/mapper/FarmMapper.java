package com.rysh.module.farm.mapper;

import com.rysh.module.farm.beans.DisplayInfo;
import com.rysh.module.farm.beans.Farm;
import com.rysh.module.farm.beans.FarmAlbum;
import com.rysh.module.farm.beans.FarmAndUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FarmMapper {
    /*
     * 
     * 添加农场 
     * @return int
     * @author HsiangSun
     * @date 2019/9/3 10:16
     */
    int addFarm(Farm farm);

    /*
     * 查询所有农场
     * @param
     * @return java.util.List<com.rysh.module.farm.beans.Farm>
     * @author HsiangSun
     * @date 2019/9/3 11:04
     */
    List<FarmAndUser> findAll();

    FarmAndUser findOneById(@Param("id") String id);

    /*
     * 更新农场信息
     * @param farm
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/6 9:30
     */
    int updateById(Farm farm);

    /**
     * 删除农庄
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/18 15:36
     */
    int updateStateById(@Param("id") String id);

    /**
     * 查询所有农庄与农场搜索
     * @param condition
     * @return java.util.List<com.rysh.module.farm.beans.FarmAndUser>
     * @author Hsiang Sun
     * @date 2019/9/19 16:52
     */
    List<FarmAndUser> findByCondition(@Param("condition") String condition);

    /**
     * 查询当前登录用户的农场信息
     * @param login
     * @return com.rysh.module.farm.beans.Farm
     * @author Hsiang Sun
     * @date 2019/9/25 15:07
     */
    Farm findFarmByLogin(@Param("login") String login,@Param("itemId") String itemId);

    /**
     * 更新农场基本信息
     * @param farm
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/21 9:39
     */
    void updateFarmInfo(FarmAndUser farm);

    /**
     * 查询当前用户的农场基本信息
     * @param login
     * @return com.rysh.module.farm.beans.DisplayInfo
     * @author Hsiang Sun
     * @date 2019/10/21 11:49
     */
    DisplayInfo findDisplayInfo(String login);

    void updateScoreById(@Param("marketId") String marketId, @Param("usedSorce") Integer usedSorce);

    /**
     * 检查当前农场是否有封面图
     * @param id
     * @return java.lang.Integer
     * @author Hsiang Sun
     * @date 2019/10/28 15:39
     */
    Integer hasCoverImg(String id);

    /**
     * 查询当前农场需要展示的信息
     * @param id
     * @return com.rysh.module.farm.beans.DisplayInfo
     * @author Hsiang Sun
     * @date 2019/10/28 15:40
     */
    DisplayInfo findDisplayInfoById(String id);

    /**
     * 将当前农场上架到客户端
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/28 15:41
     */
    int updateStatus(@Param("id") String id,@Param("operation") String operation);


    Farm findOneByIdToOrders(String farmId);

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
    FarmAlbum findAlbumById(String resultId);

    /**
     * 查询当前农场封面图片
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/5 14:27
     */
    FarmAlbum findAlbumCover(String id);

    /**
     * 根据id删除相册内容
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 11:28
     */
    void deleteAlbumById(String id);

    /**
     * 添加农场相册
     * @param album
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 11:10
     */
    void addAlbum(FarmAlbum album);

    /**
     * 根据相册的id查询所属的农场
     * @param id
     * @return java.lang.String
     * @author Hsiang Sun
     * @date 2019/11/5 14:35
     */
    String findfarmIdByAlbumId(String id);

    /**
     * 查询所有的商铺相册
     * @param id
     * @return java.util.List<com.rysh.module.store.beans.StoreAlbum>
     * @author Hsiang Sun
     * @date 2019/11/5 11:40
     */
    List<FarmAlbum> findAllAlbum(String id);

    /**
     * 更新农场排序大小
     * @param id
	 * @param sortValue
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/18 11:49
     */
    void updateSort(@Param("id") String id,@Param("sort") int sortValue);

    List<FarmAndUser> findAllPlus(@Param("search") String search);
}
