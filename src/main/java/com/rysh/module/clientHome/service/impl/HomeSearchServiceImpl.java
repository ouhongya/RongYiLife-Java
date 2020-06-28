package com.rysh.module.clientHome.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.clientHome.beans.ClientGarbage;
import com.rysh.module.clientHome.beans.SearchResponse;
import com.rysh.module.clientHome.mapper.HomeSearchMapper;
import com.rysh.module.clientHome.service.HomeSearchService;
import com.rysh.module.clientLoginRegister.mapper.LoginAndRegisterMapper;
import com.rysh.module.commonService.beans.ParamBean;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class HomeSearchServiceImpl implements HomeSearchService {

    @Autowired
    private HomeSearchMapper mapper;

    @Autowired
    private LoginAndRegisterMapper userMapper;

    @Override
    public PageInfo<SearchResponse> homeSearch(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        String search = paramBean.getSearch();

        //LinkedHashMap<Integer, Object> map = new LinkedHashMap<>();

        List<SearchResponse> resultList = mapper.homeSearch(search);

        /*//查询农庄
        List<Grange> grangeList = mapper.findGrange(search);
        //查询农场
        List<Farm> farmList = mapper.findFarm(search);
        //查询农庄商品
        List<GrangeItemAndSpecAndImg> grangeItemList = mapper.findGrangeItem(search);
        //查询农场商品
        List<FarmItemAndSpecAndImg> farmItemList = mapper.findFarmItem(search);

        if (grangeList != null){
           map.put(1,grangeList);
        }
        if (farmList != null){
            map.put(2,farmList);
        }
        if (grangeItemList != null){
            map.put(3,grangeItemList);
        }
        if (farmItemList != null){
            map.put(4,farmItemList);
        }
*/
        return new PageInfo<SearchResponse>(resultList);

    }

    @Override
    public List<ClientGarbage> searchGarbage(String name, String userId) {
        //查询当前用户所处哪个城市
        String cityId = userMapper.findCityIdByUserId(userId);
        if (cityId == null){
            log.error("当前用户{}尚未绑定小区",userId);
            return null;
        }
        return mapper.findGarbageCategoryByName(name,cityId);
    }
}
