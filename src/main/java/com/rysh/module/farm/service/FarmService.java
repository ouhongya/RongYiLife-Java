package com.rysh.module.farm.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.Farm;
import com.rysh.module.farm.beans.FarmAlbum;
import com.rysh.module.farm.beans.FarmAndUser;
import com.rysh.module.farm.beans.DisplayInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FarmService {
    /*
     * 添加农场
     * @param farm
     * @return int
     * @author HsiangSun
     * @date 2019/9/3 11:11
     */
    public int addFarm(Farm farm);

    /*
     * 查询所有的农场
     * @param
     * @return java.util.List<com.rysh.module.farm.beans.Farm>
     * @author HsiangSun
     * @date 2019/9/3 11:11
     */
    public PageInfo<FarmAndUser> getAllFarm(ParamBean param);

    /*
     * 根据Id 查询农场
     * @param
     * @return java.util.List<com.rysh.module.farm.beans.Farm>
     * @author HsiangSun
     * @date 2019/9/5 20:38
     */
    public FarmAndUser farmById(String id);

    /*
     * 更新农场信息
     * @param farm
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/6 10:49
     */
    public int updateFarm(Farm farm);

    /*
     * 删除农场
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/18 15:07
     */
    public int deletFarm(String id);

    /**
     * 农场搜索
     * @param param
     * @return com.github.pagehelper.PageInfo<com.rysh.module.farm.beans.FarmAndUser>
     * @author Hsiang Sun
     * @date 2019/9/19 15:35
     */
    public PageInfo<FarmAndUser> search(ParamBean param);

    /**
     * 查询当前登录用户的农场基本信息
     * @param login
     * @return com.rysh.module.farm.beans.FarmInfo
     * @author Hsiang Sun
     * @date 2019/9/25 15:18
     */
    public DisplayInfo farmInfo(String login,String itemId);

    /**
     * 根据当前登录用户查询他所有的 农场
     * @param login
     * @return com.rysh.module.farm.beans.Farm
     * @author Hsiang Sun
     * @date 2019/9/25 16:40
     */
    public Farm getFarmByLogin(String login);

    /**
     * 更新农场基本信息
     * @param farm
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/21 9:38
     */
    void updateFarmInfo(FarmAndUser farm);

    /**
     * 添加农场相册
     * @param farmAlbums
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/21 13:48
     */
    void addFarmAlbum(List<FarmAlbum> farmAlbums,String itemId);

    /**
     * 查询当前用户的所有农场相册
     * @param
     * @return java.util.List<com.rysh.module.farm.beans.FarmAlbum>
     * @author Hsiang Sun
     * @date 2019/10/22 14:50
     */
    List<FarmAlbum> getAllAlbum(String itemId);

    /**
     * 更新当前用户的相册
     * @param farmAlbums
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/22 15:12
     */
    void updateAlbum(List<FarmAlbum> farmAlbums,String itemId);

    /**
     * 将当前农场推送到客户端
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/26 15:13
     */
    int applyToClient(String id,String operation);


    /**
     * 添加农场banner相册
     * @param file
	 * @param id
     * @return com.rysh.module.farm.beans.FarmAlbum
     * @author Hsiang Sun
     * @date 2019/11/5 16:04
     */
    FarmAlbum addBannerAlbum(MultipartFile file, String id);

    /**
     * 添加商铺相册cover
     * @param file
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 11:01
     */
    FarmAlbum addCoverAlbum(MultipartFile file,String id);

    /**
     * 删除农场相册相册
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 11:27
     */
    void deleteAlbum(String id);

    /**
     * 查询当前用户的所有相册
     * @param id
     * @return java.util.List<com.rysh.module.store.beans.StoreAlbum>
     * @author Hsiang Sun
     * @date 2019/11/5 11:37
     */
    List<FarmAlbum> allStoreAlbum(String id);

    /**
     * 更新农场排序大小
     * @param id
	 * @param sortValue
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/18 11:49
     */
    void updateSort(String id, int sortValue);
}
