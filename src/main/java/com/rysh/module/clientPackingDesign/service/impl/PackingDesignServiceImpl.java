package com.rysh.module.clientPackingDesign.service.impl;

import com.github.pagehelper.PageHelper;
import com.rysh.module.clientPackingDesign.beans.PackingDesign;
import com.rysh.module.clientPackingDesign.beans.PackingDesignCategory;
import com.rysh.module.clientPackingDesign.beans.PackingDesignDetail;
import com.rysh.module.clientPackingDesign.mapper.PackingDesignMapper;
import com.rysh.module.clientPackingDesign.service.PackingDesignService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.utils.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
@Service
public class PackingDesignServiceImpl implements PackingDesignService {

    @Autowired
    private PackingDesignMapper packingDesignMapper;

    /**
     * 查询社群定制
     * @param paramBean
     * @param categoryId
     * @return
     */
    @Override
    public List<PackingDesign> findPackingDesign(ParamBean paramBean,String categoryId) throws Exception {
        //分页
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        return packingDesignMapper.findPackingDesign(categoryId);
    }


    /**
     * 查询社群定制详情
     * @param id
     * @return
     */
    @Override
    public List<PackingDesignDetail> findPackingDesignDetailById(String id) throws Exception {
        return packingDesignMapper.findPackingDesignDetailById(id);
    }


    /**
     * 查询社群定制异常
     * @return
     */
    @Override
    public List<PackingDesignCategory> findPackingDesignCategory(ParamBean paramBean) throws Exception {
        //分页
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        return packingDesignMapper.findPackingDesignCategory();
    }

    @Override
    public void addMessageToP(String tel, String name, String customizedId) throws Exception {
        //
        packingDesignMapper.addMessageToP(GenerateUUID.create(),tel,name,customizedId,new Date());
    }
}
