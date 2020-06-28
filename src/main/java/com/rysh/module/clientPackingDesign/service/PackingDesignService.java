package com.rysh.module.clientPackingDesign.service;

import com.rysh.module.clientPackingDesign.beans.PackingDesign;
import com.rysh.module.clientPackingDesign.beans.PackingDesignCategory;
import com.rysh.module.clientPackingDesign.beans.PackingDesignDetail;
import com.rysh.module.commonService.beans.ParamBean;

import java.util.List;

public interface PackingDesignService {
    List<PackingDesign> findPackingDesign(ParamBean paramBean,String categoryId) throws Exception;

    List<PackingDesignDetail> findPackingDesignDetailById(String id) throws Exception;

    List<PackingDesignCategory> findPackingDesignCategory(ParamBean paramBean) throws Exception;

    void addMessageToP(String tel, String name, String customizedId) throws Exception;
}
