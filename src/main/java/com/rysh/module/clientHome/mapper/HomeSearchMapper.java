package com.rysh.module.clientHome.mapper;

import com.rysh.module.clientHome.beans.ClientGarbage;
import com.rysh.module.clientHome.beans.FarmItemAndSpecAndImg;
import com.rysh.module.clientHome.beans.GrangeItemAndSpecAndImg;
import com.rysh.module.clientHome.beans.SearchResponse;
import com.rysh.module.farm.beans.Farm;
import com.rysh.module.farm.beans.FarmItem;
import com.rysh.module.grange.beans.Grange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HomeSearchMapper {
    /**
     * 客户端首页搜索
     * @param search
     * @return java.util.List<com.rysh.module.clientHome.beans.HomeSearch>
     * @author Hsiang Sun
     * @date 2019/10/16 10:22
     */
    List<SearchResponse> homeSearch(@Param("search") String search);

    /**
     * 农庄搜索
     * @param search
     * @return java.util.List<com.rysh.module.grange.beans.Grange>
     * @author Hsiang Sun
     * @date 2019/10/16 10:28
     */
    List<Grange> findGrange(String search);

    /**
     * 农场搜索
     * @param search
     * @return java.util.List<com.rysh.module.farm.beans.Farm>
     * @author Hsiang Sun
     * @date 2019/10/16 10:30
     */
    List<Farm> findFarm(String search);

    /**
     * 搜索农庄商品以及价格和图片
     * @param search
     * @return java.util.List<com.rysh.module.clientHome.beans.GrangeItemAndSpec>
     * @author Hsiang Sun
     * @date 2019/10/16 10:33
     */
    List<GrangeItemAndSpecAndImg> findGrangeItem(String search);

    /**
     * 搜索农场商品以及价格和图片
     * @param search
     * @return java.util.List<com.rysh.module.clientHome.beans.FarmItemAndSpecAndImg>
     * @author Hsiang Sun
     * @date 2019/10/16 11:03
     */
    List<FarmItemAndSpecAndImg> findFarmItem(String search);

    /**
     * 模糊查询垃圾名在某城市的分类
     * @param name
	 * @param cityId
     * @return java.util.List<com.rysh.module.clientHome.beans.ClientGarbage>
     * @author Hsiang Sun
     * @date 2019/10/23 15:02
     */
    List<ClientGarbage> findGarbageCategoryByName(@Param("name") String name,@Param("cityId") String cityId);
}
