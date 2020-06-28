package com.rysh.module.clientSearch.controller;

import com.rysh.module.clientSearch.beans.*;
import com.rysh.module.clientSearch.service.SearchService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.commonService.beans.ShopBean;
import com.rysh.module.serverCompanyRanking.beans.CompanyRankF;
import com.rysh.module.store.beans.StoreTag;
import com.rysh.module.utils.CheckRedisTokenUtils;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/client/ragbag")
@Log4j2
@Api(description = "移动端农场农庄搜索接口")
public class SearchRagBag {
    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private SearchService searchService;

    @Autowired
    private CheckRedisTokenUtils checkRedisTokenUtils;

    /**
     *农场农庄搜索
     * @param paramBean  分页参数 搜索条件
     * @param cityId  城市id
     * @param areaId  区域id
     * @param state  1评分正序  2评分倒序   3销量正序  4销量倒序
     * @param tagId  标签id
     * @return
     */
    @ApiOperation(value = "农场农庄搜索",httpMethod = "POST",notes = "paramBean 只需要传page(当前第几页)  size(每页显示记录数)  search(搜索关键字)   sortByFiled和sortByOrder不用管   除了belongType(店铺类型)，其他参数需要就传 不需要就不传",response = ShopBean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cityId",value = "城市id"),
            @ApiImplicitParam(name = "areaId",value = "区域id"),
            @ApiImplicitParam(name = "state",value = "0默认排序  1评分正序  2评分倒序   3销量正序  4销量倒序"),
            @ApiImplicitParam(name = "tagId",value = "标签id"),
            @ApiImplicitParam(name = "belongType",value = "店铺类型 2农场  3农庄")
    })
    @PostMapping(value = "/freeSearch")
    public QueryResponseResult freeSearch(ParamBean paramBean,String cityId ,String areaId,Integer state,String tagId,Integer belongType){
        try {
            if(belongType==null||(belongType!=2&&belongType!=3)){
                return new QueryResponseResult(CommonCode.PARAMETER_ERROR);
            }
            List<ShopBean> shopBeans = searchService.freeSearch(paramBean, cityId, areaId, tagId, state, belongType);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(shopBeans);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("农场搜索异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 通过店铺id查询该店的商品
     * @param marketId  店铺id
     * @param belongType  店铺类型 1商铺  2农场 3农庄
     * @return
     */
    @ApiOperation(value = "店内商品搜索",httpMethod = "POST",notes = "belongType(店铺类型 1商铺  2农场 3农庄)  marketId(店铺id)  paramBean 只需要传page(当前第几页)  size(每页显示记录数)  search(搜索关键字)   sortByFiled和sortByOrder不用管",response = SuperProductItem.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "marketId",value = "店铺id"),
            @ApiImplicitParam(name = "belongType",value = "店铺类型 1商铺  2农场 3农庄"),
            @ApiImplicitParam(name = "state",value = "1 时间倒序 2销量倒序 3销量正序 4价格倒序 5价格正序"),
            @ApiImplicitParam(name = "categoryId",value = "商品分类id")
    })
    @PostMapping(value = "/goodsFreeSearch")
    public QueryResponseResult goodsFreeSearch(ParamBean paramBean,String marketId,Integer belongType,Integer state,String categoryId){
        try {
            if(belongType==null||(belongType!=1&&belongType!=2&&belongType!=3)||marketId==null){
                    return new QueryResponseResult(CommonCode.PARAMETER_ERROR);
            }
            QueryResult<Object> result = new QueryResult<>();
            result.setData(searchService.goodsFreeSearch(paramBean,marketId,belongType,state,categoryId));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("通过店铺id搜商品异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 通过商品id查询商品详情
     * @param goodsId  商品id
     * @return
     */
    @ApiOperation(value = "商品详情查询",httpMethod = "POST",response = ProductItem.class,notes = "goodsId(商品id)  belongType(商品类型)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId",value = "商品id"),
            @ApiImplicitParam(name = "belongType",value = "商品类型  1商铺  2农场  3农庄  4自营")
    })
    @PostMapping(value = "/findGoodsDetailByGoodsId")
    public QueryResponseResult findGoodsDetailByGoodsId(String goodsId,Integer belongType){
        try {
            if(belongType==null||(belongType!=1&&belongType!=2&&belongType!=3&&belongType!=4)){
                return new QueryResponseResult(CommonCode.PARAMETER_ERROR);
            }
            ProductItem productItem = searchService.findGoodsDetailByGoodsId(goodsId, belongType);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(productItem);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("通过商品id查询商品详情异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 查询自营商城商品
     * @param paramBean  分页参数 搜索条件
     * @param state  排序类型
     * @return
     */
    @ApiOperation(value = "查询自营商城商品",httpMethod = "POST",notes = "paramBean 只需要传page(当前第几页)  size(每页显示记录数)  search(搜索关键字)   sortByFiled和sortByOrder不用管",response = SuperProductItem.class)
    @ApiImplicitParam(name = "state",value = "1 时间倒序 2销量倒序 3销量正序 4价格倒序 5价格正序")
    @PostMapping(value = "/searchShopGoods")
    public QueryResponseResult searchShopGoods(ParamBean paramBean,Integer state,String categoryId){
        try {
            QueryResult<Object> result = new QueryResult<>();
            result.setData(searchService.searchShopGoods(paramBean, state, categoryId));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询自营商城商品异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 查询周边商铺
     * @param token
     * @param paramBean  分页参数 搜索条件
     * @param state 1评分正序  2评分倒序   3销量正序  4销量倒序
     * @return
     */
    @ApiOperation(value = "查询周边商铺",httpMethod = "POST",notes = "token  paramBean 只需要传page(当前第几页)  size(每页显示记录数)  search(搜索关键字)   sortByFiled和sortByOrder不用管")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state",value = "1评分正序  2评分倒序   3销量正序  4销量倒序")
    })
    @PostMapping(value = "/searchStore")
    public QueryResponseResult searchStore(String token,ParamBean paramBean,Integer state,String tagId){
            try {
                if(token==null){
                    return new QueryResponseResult(CommonCode.PARAMETER_ERROR);
                }
                String uid = checkRedisTokenUtils.checkRedisToken(token);
                if(uid!=null){
                    QueryResult<Object> result = new QueryResult<>();
                    result.setData( searchService.searchStore(uid,paramBean,state,tagId));
                    return new QueryResponseResult(CommonCode.SUCCESS,result);
                }else {
                    return new QueryResponseResult(CommonCode.TOKEN_ERROR);
                }
            }catch (Exception e){
                log.error("查询周边商铺异常");
                log.error(e);
                log.error(sf.format(new Date()));
                return new QueryResponseResult(CommonCode.SERVER_ERROR);
            }
    }

    @ApiOperation(value = "查询自营商城banner图和分类",httpMethod = "POST",response = BannerAndCategory.class)
    @PostMapping(value = "/getNfsjBaseInfos")
    public QueryResponseResult getNfsjBaseInfos(){
        try {
            BannerAndCategory bannerAndCategory=searchService.getNfsjBaseInfos();
            QueryResult<Object> result = new QueryResult<>();
            result.setData(bannerAndCategory);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询自营商城分类异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    @ApiOperation(value = "查询社区便民分类",httpMethod = "POST",response = Type.class)
    @PostMapping(value = "/getConvenientCommunityType")
    public QueryResponseResult getConvenientCommunityType(){
        try {
            List<Type> typeList=searchService.getConvenientCommunityType();
            QueryResult<Object> result = new QueryResult<>();
            result.setData(typeList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询社区便民分类异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 查询周边商铺标签
     * @param token
     * @return
     */
    @ApiOperation(value = "查询周边商铺标签",httpMethod = "POST")
    @ApiImplicitParam(name = "token",value = "token")
    @PostMapping(value = "/findStoreTag")
    public  QueryResponseResult findStoreTag(String token){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                List<StoreTag> storeTags=searchService.findStoreTag(uid);
                QueryResult<Object> result = new QueryResult<>();
                result.setData(storeTags);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("查询周边商铺标签异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    @ApiOperation(value = "首页搜索商品",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search",value = "搜索关键字"),
            @ApiImplicitParam(name = "token",value = "token")
    })
    @PostMapping(value = "/searchAllGoods")
    public QueryResponseResult searchAllGoods(String search,String token){
        try {
            if(search==null||"".equals(search)){
                return new QueryResponseResult(CommonCode.PARAMETER_ERROR);
            }
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                List<ProductItem> productItems=searchService.searchAllGoods(search,uid);
                QueryResult<Object> result = new QueryResult<>();
                result.setData(productItems);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.SERVER_ERROR);
            }
        }catch (Exception e){
            log.error("首页搜索异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    @ApiOperation(value = "查询公益之星",response = CompanyRankF.class)
    @PostMapping(value = "/findCompanyRanking")
    public QueryResponseResult findCompanyRanking(){
        try {
            CompanyRankF companyRankF =searchService.findCompanyRanking();
            QueryResult<Object> result = new QueryResult<>();
            result.setData(companyRankF);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询公益之星异常 - 手机");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
