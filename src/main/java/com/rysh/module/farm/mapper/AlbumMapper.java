package com.rysh.module.farm.mapper;

import com.rysh.module.farm.beans.FarmAlbum;
import com.rysh.module.grange.beans.GrangeAlbum;

import java.util.List;

public interface AlbumMapper {
    /**
     * 查询当前用户的农场相册情况
     * @param companyId
     * @return java.util.List<com.rysh.module.farm.beans.FarmAlbum>
     * @author Hsiang Sun
     * @date 2019/10/22 14:14
     */
    List<FarmAlbum> findFarmAlbumById(String companyId);

    /**
     * 新增农场相册
     * @param album
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/22 14:18
     */
    void addFarmAlbum(FarmAlbum album);

    /**
     * 根据农场主id查询他的相册
     * @param companyId
     * @return java.util.List<com.rysh.module.farm.beans.FarmAlbum>
     * @author Hsiang Sun
     * @date 2019/10/22 14:53
     */
    List<FarmAlbum> findAllFarmAlbum(String companyId);

    /**
     * 根据id删除农场相册
     * @param companyId
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/22 15:15
     */
    void deleteFarmAlbum(String companyId);


    //**************************下面是农庄*************************************

    /**
     * 根据农庄id查询详细信息
     * @param companyId
     * @return java.util.List<com.rysh.module.grange.beans.GrangeAlbum>
     * @author Hsiang Sun
     * @date 2019/10/22 15:44
     */
    List<GrangeAlbum> findGrangeAlbumById(String companyId);


    /**
     * 查询所有的农庄相册
     * @param companyId
     * @return java.util.List<com.rysh.module.grange.beans.GrangeAlbum>
     * @author Hsiang Sun
     * @date 2019/10/22 15:38
     */
    List<GrangeAlbum> findAllGrangeAlbum(String companyId);

    /**
     * 添加农庄相册
     * @param album
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/22 15:46
     */
    void addGrangeAlbum(GrangeAlbum album);

    /**
     * 删除农庄相册
     * @param companyId
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/22 15:48
     */
    void deleteGrangeAlbum(String companyId);

    String findFarmCoverImg(String id);

    List<String> findFarmImgNotCover(String id);

    String findGrangeCoverImg(String id);

    List<String> findGrangeImgNotCover(String id);
}
