package com.rysh.module.farm.service;

import com.rysh.module.farm.beans.FarmImg;

public interface FarmImgService {
    /*
     * 添加图片服务
     * @param img
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/6 9:38
     */
    public int addImg(FarmImg img);

    /*
     * 图片更新服务(更新状态，路径)
     * @param img
     * @return void
     * @author Hsiang Sun
     * @date 2019/9/6 10:17
     */
    public void checkImg(FarmImg img);

    /**
     * 图片删除服务
     * @param itemId
     * @return void
     * @author Hsiang Sun
     * @date 2019/9/29 13:00
     */
    public void deleteByItemId(String itemId);

    /**
     * 更新图片服务
     * @param farmImg
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/29 13:00
     */
    public int updateImg(FarmImg farmImg);
}
