package com.rysh.module.clientHome.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.clientHome.beans.ClientGarbage;
import com.rysh.module.clientHome.beans.SearchResponse;
import com.rysh.module.commonService.beans.ParamBean;

import java.util.List;

public interface HomeSearchService {

   /**
    * 客户端首页搜索
	* @param paramBean
    * @return java.util.List<com.rysh.module.clientHome.beans.HomeSearch>
    * @author Hsiang Sun
    * @date 2019/10/15 17:35
    */
   PageInfo<SearchResponse> homeSearch(ParamBean paramBean);

   /**
    * 客户端垃圾分类搜索
    * @param name
	 * @param userId
    * @return java.util.List<com.rysh.module.clientHome.beans.ClientGarbage>
    * @author Hsiang Sun
    * @date 2019/10/23 14:30
    */
    List<ClientGarbage> searchGarbage(String name, String userId);
}
