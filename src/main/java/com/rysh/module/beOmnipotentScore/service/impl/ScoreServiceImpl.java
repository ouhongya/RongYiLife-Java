package com.rysh.module.beOmnipotentScore.service.impl;

import com.rysh.module.beOmnipotentScore.beans.ScoreDetail;
import com.rysh.module.beOmnipotentScore.beans.UserScoreDetail;
import com.rysh.module.beOmnipotentScore.service.ScoreService;
import com.rysh.module.clientOrders.beans.Orders;
import com.rysh.module.clientOrders.beans.OrdersItem;
import com.rysh.module.clientOrders.beans.OrdersSplit;
import com.rysh.module.clientOrders.mapper.OrdersMapper;
import com.rysh.module.farm.beans.Farm;
import com.rysh.module.farm.mapper.AlbumMapper;
import com.rysh.module.farm.mapper.FarmMapper;
import com.rysh.module.grange.beans.Grange;
import com.rysh.module.grange.mapper.GrangeMapper;
import com.rysh.module.shop.beans.Shop;
import com.rysh.module.shop.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private FarmMapper farmMapper;

    @Autowired
    private GrangeMapper grangeMapper;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Value("${result.tagId}")
    private String shopId;

    @Value("${result.selfHeadUrl}")
    private String selfHeadUrl;
    /**
     * 查询店铺积分的来源
     * @param marketId
     * @return
     */
    @Override
    public List<ScoreDetail> findMarketScoreSource(String marketId) {
        //积分来源集合初始化
        List<ScoreDetail> scoreDetails=new ArrayList<>();
        //通过店铺id查询子订单集合
        List<OrdersSplit> ordersSplits=ordersMapper.findOrdersSplitByShopId(marketId);
        //初始化总订单集合
        List<Orders> orderss=new ArrayList<>();
        for (OrdersSplit ordersSplit : ordersSplits) {
            //通过总订单id查询所有已支付总订单
            Orders orders = ordersMapper.findOrdersByOrderIdToScore(ordersSplit.getOrdersId());

            orderss.add(orders);
        }

        for (Orders orders : orderss) {
            //通过总订单id查询子订单
            List<OrdersSplit> ordersSplitsT = ordersMapper.findOrdersSplitByOrderIdAndShopId(orders.getId(),marketId);

            //通过子订单id查询各个子订单下的商品明细
            for (OrdersSplit ordersSplit : ordersSplitsT) {
                List<OrdersItem> ordersItems = ordersMapper.findOrdersItemByOrdersSplitId(ordersSplit.getId());
                //创建积分来源对象
                ScoreDetail scoreDetail = new ScoreDetail();
                //店铺应得积分
                scoreDetail.setMarketScore(ordersSplit.getUsedSorce());
                //商品明细集合
                scoreDetail.setOrdersItems(ordersItems);
                //通过总订单id查询总订单 订单号
                Orders ordersT = ordersMapper.findOrdersByOrderId(ordersSplit.getOrdersId());
                //订单号
                scoreDetail.setOrdersNum(ordersT.getOrdersNum());

                scoreDetails.add(scoreDetail);
            }
        }
        for (int i = 0; i < scoreDetails.size(); i++) {
            if(scoreDetails.get(i).getMarketScore()<=0){
                scoreDetails.remove(i);
                i--;
            }
        }
        return scoreDetails;
    }

    /**
     * 查询用户积分明细
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public List<UserScoreDetail> findUserScoreSource(String uid) throws Exception {
        //通过userId查询所有非自动取消的订单
        List<Orders> orderss=ordersMapper.findNotAutomaticCancelOrdersByUserId(uid);
        //用户积分对象集合初始化
        List<UserScoreDetail> userScoreDetails=new ArrayList<>();
        for (Orders orders : orderss) {
            //通过总订单id查询所有子订单
            List<OrdersSplit> ordersSplits=ordersMapper.findOrdersSplitByOrderId(orders.getId());

            for (OrdersSplit ordersSplit : ordersSplits) {
                //用户积分对象初始化
                UserScoreDetail userScoreDetail = new UserScoreDetail();
                switch (orders.getState()){
                    case 1:
                        //下单未支付状态 扣除使用积分
                        switch (ordersSplit.getBelongType()){

                            case 2:
                                //查询农场信息
                                Farm farm = farmMapper.findOneByIdToOrders(ordersSplit.getMarketId());
                                //查询农场头像
                                String farmAlbumImgUrl = albumMapper.findFarmCoverImg(ordersSplit.getMarketId());

                                if(farm!=null){
                                    //设置店铺名称
                                    userScoreDetail.setMarketName(farm.getName());
                                    //设置积分消费时间
                                    userScoreDetail.setCreateTimeMills(orders.getLastedUpdateTime().getTime());
                                    //设置积分
                                    userScoreDetail.setScore(0-ordersSplit.getUsedSorce());
                                    if(farmAlbumImgUrl!=null){
                                        //设置店铺头像
                                        userScoreDetail.setMarketUrl(farmAlbumImgUrl);
                                    }

                                    userScoreDetails.add(userScoreDetail);
                                }
                                break;
                            case 3:
                                //查询农庄信息
                                Grange grange = grangeMapper.findByIdToOrders(ordersSplit.getMarketId());
                                //查询农庄头像
                                String grangeAlbumImgUrl = albumMapper.findGrangeCoverImg(ordersSplit.getMarketId());

                                if(grange!=null){
                                    //设置店铺名称
                                    userScoreDetail.setMarketName(grange.getName());
                                    //设置积分消费时间
                                    userScoreDetail.setCreateTimeMills(orders.getLastedUpdateTime().getTime());
                                    //设置积分
                                    userScoreDetail.setScore(0-ordersSplit.getUsedSorce());
                                    if(grangeAlbumImgUrl!=null){
                                        //设置店铺头像
                                        userScoreDetail.setMarketUrl(grangeAlbumImgUrl);
                                    }
                                    userScoreDetails.add(userScoreDetail);
                                }
                                break;
                            case 4:
                                //查询自营商城信息
                                Shop shop = shopMapper.findById(ordersSplit.getMarketId());
                                //自营商城头像
                                String shopAlubmImgUrl = selfHeadUrl;

                                if(shop!=null){
                                    //设置店铺名称
                                    userScoreDetail.setMarketName(shop.getName());
                                    //设置消费时间
                                    userScoreDetail.setCreateTimeMills(orders.getLastedUpdateTime().getTime());
                                    //设置积分
                                    userScoreDetail.setScore(ordersSplit.getUsedSorce());
                                    if(shopAlubmImgUrl!=null){
                                        userScoreDetail.setMarketUrl(shopAlubmImgUrl);
                                    }
                                    userScoreDetails.add(userScoreDetail);
                                }
                                break;
                        }
                        break;
                    case 2:
                        //下单已支付状态 增加使用积分
                        switch (ordersSplit.getBelongType()){
                            case 2:
                                //查询农场信息
                                Farm farm = farmMapper.findOneByIdToOrders(ordersSplit.getMarketId());
                                //查询农场头像
                                String farmAlubmImgUrl = albumMapper.findFarmCoverImg(ordersSplit.getMarketId());

                                if(farm!=null){
                                    //设置店铺名称
                                    userScoreDetail.setMarketName(farm.getName());
                                    //设置消费时间
                                    userScoreDetail.setCreateTimeMills(orders.getLastedUpdateTime().getTime());
                                    //支付前需要扣除的积分
                                    userScoreDetail.setScore(0-ordersSplit.getUsedSorce());
                                    if(farmAlubmImgUrl!=null){
                                        userScoreDetail.setMarketUrl(farmAlubmImgUrl);
                                    }
                                    userScoreDetails.add(userScoreDetail);
                                    //构建一个新的用户积分对象
                                    UserScoreDetail userScoreDetail1 = new UserScoreDetail();
                                    userScoreDetail1.setMarketName(farm.getName());
                                    userScoreDetail1.setCreateTimeMills(orders.getCreatedTime().getTime());
                                    //设置消费增加的积分
                                    //实付金额减去运费
                                    BigDecimal realPlay = ordersSplit.getRealPlay();
                                    realPlay=realPlay.subtract(ordersSplit.getFreight());
                                    realPlay=realPlay.setScale(0,BigDecimal.ROUND_DOWN);
                                    userScoreDetail1.setScore(realPlay.intValue());
                                    userScoreDetails.add(userScoreDetail1);
                                    if(farmAlubmImgUrl!=null){
                                        userScoreDetail1.setMarketUrl(farmAlubmImgUrl);
                                    }
                                }
                                break;
                            case 3:
                                //查询农庄信息
                                Grange grange = grangeMapper.findByIdToOrders(ordersSplit.getMarketId());
                                //查询农庄头像
                                String grangeAlubmImgUrl = albumMapper.findGrangeCoverImg(ordersSplit.getMarketId());

                                if(grange!=null){
                                    //设置店铺名称
                                    userScoreDetail.setMarketName(grange.getName());
                                    //设置消费时间
                                    userScoreDetail.setCreateTimeMills(orders.getLastedUpdateTime().getTime());
                                    //支付前需要扣除的积分
                                    userScoreDetail.setScore(0-ordersSplit.getUsedSorce());
                                    if(grangeAlubmImgUrl!=null){
                                        userScoreDetail.setMarketUrl(grangeAlubmImgUrl);
                                    }
                                    userScoreDetails.add(userScoreDetail);
                                    //构建一个新的用户积分对象
                                    UserScoreDetail userScoreDetail1 = new UserScoreDetail();
                                    userScoreDetail1.setMarketName(grange.getName());
                                    userScoreDetail1.setCreateTimeMills(orders.getCreatedTime().getTime());
                                    //设置消费增加的积分
                                    //实付金额减去运费
                                    BigDecimal realPlay = ordersSplit.getRealPlay();
                                    realPlay=realPlay.subtract(ordersSplit.getFreight());
                                    realPlay=realPlay.setScale(0,BigDecimal.ROUND_DOWN);


                                    userScoreDetail1.setScore(realPlay.intValue());
                                    userScoreDetails.add(userScoreDetail1);
                                    if(grangeAlubmImgUrl!=null){
                                        userScoreDetail1.setMarketUrl(grangeAlubmImgUrl);
                                    }
                                }
                                break;

                            case 4:
                                //查询自营商城信息
                                Shop shop = shopMapper.findById(ordersSplit.getMarketId());
                                //自营商城头像
                                String shopAlubmImgUrl=selfHeadUrl;

                                if(shop!=null){
                                    //设置店铺名称
                                    userScoreDetail.setMarketName(shop.getName());
                                    //设置消费时间
                                    userScoreDetail.setCreateTimeMills(orders.getLastedUpdateTime().getTime());
                                    //支付前需要扣除的积分
                                    userScoreDetail.setScore(0-ordersSplit.getUsedSorce());
                                    if(shopAlubmImgUrl!=null){
                                        userScoreDetail.setMarketUrl(shopAlubmImgUrl);
                                    }
                                    userScoreDetails.add(userScoreDetail);
                                    //构建一个新的用户积分对象
                                    UserScoreDetail userScoreDetail1 = new UserScoreDetail();
                                    userScoreDetail1.setMarketName(shop.getName());
                                    userScoreDetail1.setCreateTimeMills(orders.getCreatedTime().getTime());
                                    //设置消费增加的积分
                                    //实付金额减去运费
                                    BigDecimal realPlay = ordersSplit.getRealPlay();

                                    realPlay=realPlay.subtract(ordersSplit.getFreight());
                                    realPlay=realPlay.setScale(0,BigDecimal.ROUND_DOWN);
                                    userScoreDetail1.setScore(realPlay.intValue());
                                    userScoreDetails.add(userScoreDetail1);
                                    if(shopAlubmImgUrl!=null){
                                        userScoreDetail1.setMarketUrl(shopAlubmImgUrl);
                                    }
                                }
                                break;
                        }
                        break;

                    case 3:
                        //下单手动取消状态 退还用户积分
                        switch (ordersSplit.getBelongType()){
                            case 2:
                                //查询农场信息
                                Farm farm = farmMapper.findOneByIdToOrders(ordersSplit.getMarketId());
                                //查询农场头像
                                String farmAlubmImgUrl = albumMapper.findFarmCoverImg(ordersSplit.getMarketId());

                                if(farm!=null){
                                    //设置店铺名称
                                    userScoreDetail.setMarketName(farm.getName());
                                    //设置消费时间
                                    userScoreDetail.setCreateTimeMills(orders.getLastedUpdateTime().getTime());
                                    //设置下单需要扣除的积分
                                    userScoreDetail.setScore(0-ordersSplit.getUsedSorce());
                                    if(farmAlubmImgUrl!=null){
                                        userScoreDetail.setMarketUrl(farmAlubmImgUrl);
                                    }
                                    userScoreDetails.add(userScoreDetail);
                                    //构建一个新的用户积分对象
                                    UserScoreDetail userScoreDetail1 = new UserScoreDetail();
                                    userScoreDetail1.setMarketName(farm.getName());
                                    userScoreDetail1.setCreateTimeMills(orders.getCreatedTime().getTime());
                                    //设置取消订单返还的积分
                                    userScoreDetail1.setScore(ordersSplit.getUsedSorce());
                                    userScoreDetails.add(userScoreDetail1);
                                    if(farmAlubmImgUrl!=null){
                                        userScoreDetail1.setMarketUrl(farmAlubmImgUrl);
                                    }
                                }
                                break;
                            case 3:
                                //查询农庄信息
                                Grange grange = grangeMapper.findByIdToOrders(ordersSplit.getMarketId());
                                //查询农庄头像
                                String grangeAlubmImgUrl = albumMapper.findGrangeCoverImg(ordersSplit.getMarketId());

                                if(grange!=null){
                                    //设置店铺名称
                                    userScoreDetail.setMarketName(grange.getName());
                                    //设置消费时间
                                    userScoreDetail.setCreateTimeMills(orders.getLastedUpdateTime().getTime());
                                    //设置下单需要扣除的积分
                                    userScoreDetail.setScore(0-ordersSplit.getUsedSorce());
                                    if(grangeAlubmImgUrl!=null){
                                        userScoreDetail.setMarketUrl(grangeAlubmImgUrl);
                                    }
                                    userScoreDetails.add(userScoreDetail);
                                    //构建一个新的用户积分对象
                                    UserScoreDetail userScoreDetail1 = new UserScoreDetail();
                                    userScoreDetail1.setMarketName(grange.getName());
                                    userScoreDetail1.setCreateTimeMills(orders.getCreatedTime().getTime());
                                    //设置取消订单返还的积分
                                    userScoreDetail1.setScore(ordersSplit.getUsedSorce());
                                    userScoreDetails.add(userScoreDetail1);
                                    if(grangeAlubmImgUrl!=null){
                                        userScoreDetail1.setMarketUrl(grangeAlubmImgUrl);
                                    }
                                }
                                break;

                            case 4:
                                //查询自营商城信息
                                Shop shop = shopMapper.findById(ordersSplit.getMarketId());
                                //自营商城头像
                                String shopAlubmImgUrl=selfHeadUrl;

                                if(shop!=null){
                                    //设置店铺名称
                                    userScoreDetail.setMarketName(shop.getName());
                                    //设置消费时间
                                    userScoreDetail.setCreateTimeMills(orders.getLastedUpdateTime().getTime());
                                    //设置下单需要扣除的积分
                                    userScoreDetail.setScore(0-ordersSplit.getUsedSorce());
                                    if(shopAlubmImgUrl!=null){
                                        userScoreDetail.setMarketUrl(shopAlubmImgUrl);
                                    }
                                    userScoreDetails.add(userScoreDetail);
                                    //构建一个新的用户积分对象
                                    UserScoreDetail userScoreDetail1 = new UserScoreDetail();
                                    userScoreDetail1.setMarketName(shop.getName());
                                    userScoreDetail1.setCreateTimeMills(orders.getCreatedTime().getTime());
                                    //设置取消订单返还的积分
                                    userScoreDetail1.setScore(ordersSplit.getUsedSorce());
                                    userScoreDetails.add(userScoreDetail1);
                                    if(shopAlubmImgUrl!=null){
                                        userScoreDetail1.setMarketUrl(shopAlubmImgUrl);
                                    }
                                }
                                break;
                        }
                        break;
                }

            }

        }
        for (int i = 0; i < userScoreDetails.size(); i++) {
            if(userScoreDetails.get(i).getScore()==0){
                userScoreDetails.remove(i);
                i--;
            }
        }
        return userScoreDetails;
    }


}
