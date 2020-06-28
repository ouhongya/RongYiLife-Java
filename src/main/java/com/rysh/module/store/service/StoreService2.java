package com.rysh.module.store.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.store.beans.Store;
import com.rysh.module.store.beans.StoreAlbum;
import com.rysh.module.store.beans.StoreInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoreService2 {

    /**
     * 查询当前用户的商铺信息
     * @param login
     * @return com.rysh.module.farm.beans.FarmInfo
     * @author Hsiang Sun
     * @date 2019/10/8 16:49
     */
    StoreInfo grangeInfo(String login);

    /**
     * 商铺的搜索
     * @param param
     * @return com.github.pagehelper.PageInfo<com.rysh.module.store.beans.Store>
     * @author Hsiang Sun
     * @date 2019/10/8 17:14
     */
    PageInfo<Store> search(ParamBean param);

    /**
     * 查询当前用户的商品信息
     * @param login
     * @return com.rysh.module.store.beans.Store
     * @author Hsiang Sun
     * @date 2019/10/9 10:53
     */
    Store getStoreByLogin(String login);

    /**
     * 根据商铺id查询回显信息
     * @param id
     * @return com.rysh.module.store.beans.StoreInfo
     * @author Hsiang Sun
     * @date 2019/11/4 16:38
     */
    StoreInfo storeById(String id);

    /**
     * 更新周边商铺信息
     * @param storeInfo
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/4 17:45
     */
    void updateStoreInfo(StoreInfo storeInfo);

    /**
     * 添加商铺相册banner
     * @param file
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 11:01
     */
    StoreAlbum addBannerAlbum(MultipartFile file,String id);

    /**
     * 添加商铺相册cover
     * @param file
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 11:01
     */
    StoreAlbum addCoverAlbum(MultipartFile file,String id);

    /**
     * 删除商铺相册
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
    List<StoreAlbum> allStoreAlbum(String id);

    /**
     * 上/下架当前商铺
     * @param operation
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/8 14:30
     */
    void applyToClient(String operation, String id);
}
