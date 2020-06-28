package com.rysh.module.productCustomize.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.productCustomize.beans.*;

public interface ProductCustomizeService {
    /**
     * 添加社群定制
     * @param productCustomizeVo
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/14 16:37
     */
    void add(ProductCustomizeVo productCustomizeVo);

    /**
     * 查看所有的社群定制信息
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.productCustomize.beans.ProductCustomizeRo>
     * @author Hsiang Sun
     * @date 2019/11/15 9:29
     */
    PageInfo<ProductCustomizeRo> all(ParamBean paramBean);

    /**
     * 社群定制上下架
     * @param id
	 * @param operation
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/15 10:27
     */
    void applyToClient(String id, String operation);

    /**
     * 删除
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/15 10:33
     */
    void delete(String id);

    /**
     * 根据id 回显数据
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/15 10:39
     */
    ProductCustomizeRo detail(String id);

    /**
     * 更新
     * @param productCustomizeVo
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/15 11:02
     */
    void update(ProductCustomizeVo productCustomizeVo);

    /**
     * 更新排序
     * @param id
	 * @param sort
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/15 11:58
     */
    void updateSort(String id, String sort);

    /**
     * 查询社群定制留言
     * @param id
     * @return com.rysh.module.productCustomize.beans.ProductCustomizeContact
     * @author Hsiang Sun
     * @date 2019/11/15 13:49
     */
    PageInfo<ProductCustomizeContact> findContact(ParamBean paramBean,String id);
    
    /**
     * 一键审核
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/15 14:25
     */
    void checkPass(CheckParam checkParam);

    /**
     * 查询所有的未审核内容
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.productCustomize.beans.ProductCustomizeVo>
     * @author Hsiang Sun
     * @date 2019/11/15 14:57
     */
    PageInfo<ProductCustomizeVo> uncheck(ParamBean paramBean);

    /**
     * 查询所有没有审核的内容
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.productCustomize.beans.ProductCustomizeVo>
     * @author Hsiang Sun
     * @date 2019/11/15 15:11
     */
    PageInfo<ProductCustomize> checkHistory(ParamBean paramBean);
}
