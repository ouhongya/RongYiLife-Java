package com.rysh.module.statistics.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.clientOrders.beans.Orders;
import com.rysh.module.clientOrders.beans.OrdersItem;
import com.rysh.module.clientOrders.beans.OrdersSplit;
import com.rysh.module.clientOrders.mapper.OrdersMapper;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.FarmAndUser;
import com.rysh.module.farm.mapper.FarmMapper;
import com.rysh.module.grange.beans.Grange;
import com.rysh.module.grange.mapper.GrangeMapper;
import com.rysh.module.serverSystem.beans.Store;
import com.rysh.module.serverSystem.mapper.StoreMapper;
import com.rysh.module.shop.beans.ShopCategory;
import com.rysh.module.shop.mapper.ShopCategoryMapper;
import com.rysh.module.statistics.beans.*;
import com.rysh.module.statistics.mapper.OrdersStatisticsMapper;
import com.rysh.module.statistics.service.OrdersStatisticsService;
import com.rysh.module.utils.TimeCale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrdersStatisticsServiceImpl implements OrdersStatisticsService {


    @Autowired
    private OrdersStatisticsMapper ordersStatisticsMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private FarmMapper farmMapper;

    @Autowired
    private GrangeMapper grangeMapper;

    @Autowired
    private ShopCategoryMapper shopCategoryMapper;
    /**
     * 查询被统计的店铺
     * @param belongType
     * @param paramBean
     * @return
     */
    @Override
    public PageInfo findOrdersMarket(Integer belongType, ParamBean paramBean,String startTime,String endTime) throws Exception {
        //统计店铺集合初始化
        List<OrdersMarketSS> ordersMarketSSES=new ArrayList<>();
        if(endTime!=null){
            //将endTime增加一天
            endTime=sf.format(new Date(sf.parse(endTime).getTime()+86400000));
        }
        switch (belongType){
            case 1:
                //查询所有商铺
                PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
                List<Store> stores = storeMapper.findAllStorePlus(paramBean.getSearch());
                for (Store store : stores) {
                    //构建统计店铺对象
                    OrdersMarketSS ordersMarketSS = new OrdersMarketSS();
                    //店铺id
                    ordersMarketSS.setId(store.getId());
                    //店铺名称
                    ordersMarketSS.setMarketName(store.getName());
                    //积分
                    ordersMarketSS.setScore(0);
                    //店铺订单数量
                    Integer count=ordersStatisticsMapper.findOrdersCountByMarketId(store.getId());
                    ordersMarketSS.setMarketOrdersCount(count);
                    //店铺在时间区间内的收益金额
                    BigDecimal price=ordersStatisticsMapper.findSumOrdersPriceByTime(store.getId(),startTime,endTime);
                    if(price!=null){
                        ordersMarketSS.setMarketPrice(price);
                    }
                    ordersMarketSSES.add(ordersMarketSS);
                }
                break;
            case 2:
                //查询所有农场
                PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
                List<FarmAndUser> farmAndUsers=farmMapper.findAllPlus(paramBean.getSearch());
                for (FarmAndUser farmAndUser : farmAndUsers) {
                    //构建统计店铺对象
                    OrdersMarketSS ordersMarketSS = new OrdersMarketSS();
                    //店铺id
                    ordersMarketSS.setId(farmAndUser.getId());
                    //店铺名称
                    ordersMarketSS.setMarketName(farmAndUser.getName());
                    //积分
                    ordersMarketSS.setScore(farmAndUser.getScore());
                    //店铺订单数量
                    Integer count = ordersStatisticsMapper.findOrdersCountByMarketId(farmAndUser.getId());
                    ordersMarketSS.setMarketOrdersCount(count);
                    //店铺在时间区间内的收益金额
                    BigDecimal price=ordersStatisticsMapper.findSumOrdersPriceByTime(farmAndUser.getId(),startTime,endTime);
                    if(price!=null){
                        ordersMarketSS.setMarketPrice(price);
                    }
                    ordersMarketSSES.add(ordersMarketSS);
                }
                break;
            case 3:
                //查询所有农庄
                PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
                List<Grange> granges=grangeMapper.findAllPlus(paramBean.getSearch());
                for (Grange grange : granges) {
                    //构建统计店铺对象
                    OrdersMarketSS ordersMarketSS = new OrdersMarketSS();
                    //店铺id
                    ordersMarketSS.setId(grange.getId());
                    //店铺名称
                    ordersMarketSS.setMarketName(grange.getName());
                    //积分
                    ordersMarketSS.setScore(grange.getScore());
                    //店铺订单数量
                    Integer count = ordersStatisticsMapper.findOrdersCountByMarketId(grange.getId());
                    ordersMarketSS.setMarketOrdersCount(count);
                    //店铺在时间区间内的收益金额
                    BigDecimal price=ordersStatisticsMapper.findSumOrdersPriceByTime(grange.getId(),startTime,endTime);
                    if(price!=null){
                        ordersMarketSS.setMarketPrice(price);
                    }
                    ordersMarketSSES.add(ordersMarketSS);
                }
                break;
            case 4:
                PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
                   List<ShopCategory> shopCategories=shopCategoryMapper.findAllCategory(paramBean.getSearch());
                for (ShopCategory shopCategory : shopCategories) {
                    OrdersMarketSS ordersMarketSS = new OrdersMarketSS();
                    //自营分类id
                    ordersMarketSS.setId(shopCategory.getId());
                    //分类名称
                    ordersMarketSS.setMarketName(shopCategory.getName());
                    //包含此分类商品的订单数量
                    Integer count = ordersStatisticsMapper.findShopOrdersCount(shopCategory.getId());
                    ordersMarketSS.setMarketOrdersCount(count);
                    //时间区间内的收益金额
                    BigDecimal price=ordersStatisticsMapper.findSumShopPriceByTime(shopCategory.getId(),startTime,endTime);
                    if(price!=null){
                        ordersMarketSS.setMarketPrice(price);
                    }
                    ordersMarketSSES.add(ordersMarketSS);
                }
                break;
        }
        PageInfo<OrdersMarketSS> pageInfo = new PageInfo<>(ordersMarketSSES);
        return pageInfo;
    }


    /**
     * 查询店铺下被统计的订单
     * @param marketId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public  PageInfo<OrdersOrdersSplitSS> findOrdersByMarketId(ParamBean paramBean,String marketId, String startTime, String endTime) throws Exception {
        //店铺下的订单集合初始化
        List<OrdersOrdersSplitSS> ordersOrdersSplitSSES = new ArrayList<>();
        if(endTime!=null&&!"".equals(endTime)){
            //处理endTime
            Date endTimeDate = sf.parse(endTime);
            //将结束时间增加一天
            endTime=sf.format(new Date(endTimeDate.getTime()+86400000));
        }
        //查询订单
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        List<OrdersSplit> ordersSplits=ordersStatisticsMapper.findOrdersByMarketId(marketId,startTime,endTime);
        for (OrdersSplit ordersSplit : ordersSplits) {
            //创建统计订单对象
            OrdersOrdersSplitSS ordersOrdersSplitSS = new OrdersOrdersSplitSS();
            //运费
            ordersOrdersSplitSS.setFreight(ordersSplit.getFreight());
            //子订单id
            ordersOrdersSplitSS.setId(ordersSplit.getId());
            //订单实付金额
            ordersOrdersSplitSS.setOrdersPrice(ordersSplit.getRealPlay());
            //使用的积分
            ordersOrdersSplitSS.setUseScore(ordersSplit.getUsedSorce());
            //查询总订单信息
            Orders orders = ordersMapper.findOrdersByOrderIdToStatistics(ordersSplit.getOrdersId());
            //订单号
            ordersOrdersSplitSS.setOrderNum(orders.getOrdersNum());
            //支付时间
            ordersOrdersSplitSS.setPayTime(sf.format(orders.getPayTime()));
            //查询子订单下的商品种类
            Integer itemCount=ordersStatisticsMapper.findOrdersItemCountBySplitId(ordersSplit.getId());
            ordersOrdersSplitSS.setItemCount(itemCount);

            ordersOrdersSplitSSES.add(ordersOrdersSplitSS);
        }


        PageInfo<OrdersOrdersSplitSS> pageInfo = new PageInfo<>(ordersOrdersSplitSSES);
        return pageInfo;
    }


    /**
     * 查询统计表数据
     * @param marketId
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,Object> findCountTable(String marketId,String startTime,String endTime) throws Exception {
       if(startTime!=null&&endTime!=null){
           List<TimeCount> timeCounts = TimeCale.timeList(startTime, endTime);
           //每天金额统计集合初始化
           List<MoneyCount> moneyCounts=new ArrayList<>();
           //查询用户每天的订单数量
           for (TimeCount timeCount : timeCounts) {
               Integer count=ordersStatisticsMapper.findOrdersCount(marketId,timeCount.getTime(),sf.format(new Date(sf.parse(timeCount.getTime()).getTime()+86400000)));
               timeCount.setCount(count);
               //创建金额统计对象
               MoneyCount moneyCount = new MoneyCount();
               moneyCount.setTime(timeCount.getTime());
               //查询当天的金额
               BigDecimal price=ordersStatisticsMapper.findMoneyCount(marketId,timeCount.getTime(),sf.format(new Date(sf.parse(timeCount.getTime()).getTime()+86400000)));
               if(price!=null){
                   moneyCount.setPrice(price);
               }
               moneyCounts.add(moneyCount);
           }
           Map<String,Object> map = new HashMap<>();
           map.put("time",timeCounts);
           map.put("money",moneyCounts);
           return map;
       }else {
           return null;
       }
    }


    /**
     * 查询子订单下的被统计的商品详情
     * @param ordersSplitId
     * @return
     */
    @Override
    public PageInfo<OrdersItemSS> findItemDetailBySplitId(String ordersSplitId,ParamBean paramBean) throws Exception {
        //商品详情集合初始化
        List<OrdersItemSS> ordersItemSSES=new ArrayList<>();
        //查询ordersItem
        //分页
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        List<OrdersItem> ordersItems = ordersMapper.findOrdersItemByOrdersSplitId(ordersSplitId);

        for (OrdersItem ordersItem : ordersItems) {
            //创建统计商品对象
            OrdersItemSS ordersItemSS = new OrdersItemSS();
            //商品单价
            ordersItemSS.setItemPrice(ordersItem.getPrice());
            //商品数量
            ordersItemSS.setQuantity(ordersItem.getQuantity());
            //商品名称
            ordersItemSS.setItemName(ordersItem.getProductName());
            //商品封面图
            ordersItemSS.setImg(ordersItem.getProductUrl());

            ordersItemSSES.add(ordersItemSS);
        }
        PageInfo<OrdersItemSS> pageInfo = new PageInfo<>(ordersItemSSES);
        return pageInfo;
    }


    /**
     * 查询农场农庄商铺自营的销售总金额
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    @Override
    public List<RYSHPrice> findSumPrice(String startTime, String endTime) throws Exception {
        if(endTime!=null){
            //将结束时间增加一天
            Date endTimeDate = sf.parse(endTime);
            endTime=sf.format(new Date(endTimeDate.getTime()+86400000));
        }
        List<RYSHPrice> ryshPrices=ordersStatisticsMapper.findSumPrice(startTime,endTime);
            return ryshPrices;
    }
}
