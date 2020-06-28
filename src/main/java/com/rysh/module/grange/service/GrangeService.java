package com.rysh.module.grange.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.DisplayInfo;
import com.rysh.module.grange.beans.Grange;
import com.rysh.module.grange.beans.GrangeAlbum;
import com.rysh.module.grange.beans.GrangeInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GrangeService  {
    /**
     * 新增农庄
     * @param grange
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/19 16:45
     */
    public int addNewGrange(Grange grange);

    /**
     * 根据ID查询农庄基本信息
     * @param id
     * @return com.rysh.module.grange.beans.Grange
     * @author Hsiang Sun
     * @date 2019/9/19 16:45
     */
    public GrangeInfo grangeById(String id);

    /**
     * 更新农庄信息
     * @param grange
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/19 16:45
     */
    public int updateGrange(Grange grange);

    /**
     * 删除农庄
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/19 16:46
     */
    public int deletGrange(String id);

    /**
     * 查询农庄的所有信息与农庄搜索
     * @param param
     * @return com.github.pagehelper.PageInfo<com.rysh.module.grange.beans.Grange>
     * @author Hsiang Sun
     * @date 2019/9/19 16:46
     */
    public PageInfo<Grange> search(ParamBean param);

    DisplayInfo grangeInfo(String login,String itemId);

    /**
     * 查询当前用户的农庄
     * @param login
     * @return com.rysh.module.grange.beans.Grange
     * @author Hsiang Sun
     * @date 2019/10/8 10:16
     */
    Grange getGrangeByLogin(String login);

    /**
     * 农庄基本信息更新
     * @param grangeInfo
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/21 11:11
     */
    void updateGrangeInfo(GrangeInfo grangeInfo);

    /**
     * 农庄相册上传
     * @param farmAlbums
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/22 15:30
     */
    void addFarmAlbum(List<GrangeAlbum> farmAlbums,String itemId);

    /**
     * 查询当前用户的相册
     * @param
     * @return java.util.List<com.rysh.module.grange.beans.GrangeAlbum>
     * @author Hsiang Sun
     * @date 2019/10/22 15:30
     */
    List<GrangeAlbum> getAllAlbum(String itemId);

    /**
     * 更新当前用户的相册
     * @param farmAlbums
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/22 15:30
     */
    void updateAlbum(List<GrangeAlbum> farmAlbums,String id);

    /**
     * 将农庄发送到客户端
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/28 9:25
     */
    int applyToClient(String id,String operation);

    /**
     * 添加农庄相册banner
     * @param file
	 * @param id
     * @return com.rysh.module.grange.beans.GrangeAlbum
     * @author Hsiang Sun
     * @date 2019/11/5 16:49
     */
    GrangeAlbum addBannerAlbum(MultipartFile file, String id);

    /**
     * 添加农庄相册cover
     * @param file
	 * @param id
     * @return com.rysh.module.grange.beans.GrangeAlbum
     * @author Hsiang Sun
     * @date 2019/11/5 16:50
     */
    GrangeAlbum addCoverAlbum(MultipartFile file, String id);

    /**
     * 删除农庄相册
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 16:51
     */
    void deleteAlbum(String id);

    /**
     * 查询当前农庄所有的相册内容
     * @param id
     * @return java.util.List<com.rysh.module.grange.beans.GrangeAlbum>
     * @author Hsiang Sun
     * @date 2019/11/5 16:51
     */
    List<GrangeAlbum> allStoreAlbum(String id);

    /**
     * 更新农场排序
     * @param id
	 * @param sortValue
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/18 13:34
     */
    void updateSort(String id, int sortValue);
}
