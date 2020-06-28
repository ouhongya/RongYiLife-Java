package com.rysh.module.grange.service.impl;

import com.rysh.module.grange.beans.GrangeImg;
import com.rysh.module.grange.mapper.GrangeImgMapper;
import com.rysh.module.grange.service.GrangeImgService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class GrangeImgServiceImpl implements GrangeImgService {

    @Autowired
    private GrangeImgMapper mapper;

    @Override
    public int addImg(GrangeImg img) {
        int i = mapper.addImg(img);
        if ( i != 1){
            log.error("[Add img has Error!]");
        }
        return i;
    }

    @Override
    public void checkImg(GrangeImg img) {
        int i = mapper.updateById(img);
        if (i != 1 ){
            log.error("更新图片失败");
        }
    }

    @Override
    public void deleteByItemId(String itemId) {
        mapper.dropByItemId(itemId);
    }

    @Override
    public int updateImg(GrangeImg farmImg) {
        return mapper.addImg(farmImg);
    }
}
