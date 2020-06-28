package com.rysh.module.farm.service.impl;

import com.rysh.module.commonService.service.ImageUploadService;
import com.rysh.module.farm.beans.FarmImg;
import com.rysh.module.farm.mapper.FarmImgMapper;
import com.rysh.module.farm.service.FarmImgService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class FarmImgServiceImpl implements FarmImgService {

    @Autowired
    private FarmImgMapper mapper;

    @Autowired
    private ImageUploadService imageUploadService;

    /*
     * 添加图片服务
     * @param img
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/6 9:38
     */
    public int addImg(FarmImg img){
        int i = mapper.addImg(img);
        if ( i != 1){
            log.error("[Add img has Error!]");
        }
        return i;
    }

    /*
     * 图片更新服务(更新状态，路径)
     * @param img
     * @return void
     * @author Hsiang Sun
     * @date 2019/9/6 10:17
     */
    public void checkImg(FarmImg img){
        int i = mapper.updateById(img);
        if (i != 1 ){
            log.error("更新图片失败");
        }
    }

    public void deleteByItemId(String itemId){
        mapper.dropByItemId(itemId);
    }


    public int updateImg(FarmImg farmImg) {
        return mapper.addImg(farmImg);
    }
}
