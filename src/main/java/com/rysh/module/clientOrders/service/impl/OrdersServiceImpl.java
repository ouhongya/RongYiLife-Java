package com.rysh.module.clientOrders.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.rysh.module.clientLoginRegister.beans.User;
import com.rysh.module.clientLoginRegister.mapper.LoginAndRegisterMapper;
import com.rysh.module.clientMessage.beans.Message;
import com.rysh.module.clientMessage.mapper.MessageMapper;
import com.rysh.module.clientOrders.beans.*;
import com.rysh.module.clientOrders.mapper.OrdersMapper;
import com.rysh.module.clientOrders.service.OrdersService;
import com.rysh.module.clientShoppingCart.mapper.ShoppingCartMapper;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.commonService.service.impl.SendMessage;
import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.City;
import com.rysh.module.community.beans.Community;
import com.rysh.module.community.mapper.AreaMapper;
import com.rysh.module.community.mapper.CityMapper;
import com.rysh.module.community.mapper.CommunityMapper;
import com.rysh.module.farm.beans.*;
import com.rysh.module.farm.mapper.*;
import com.rysh.module.grange.beans.Grange;
import com.rysh.module.grange.beans.GrangeCategory;
import com.rysh.module.grange.beans.GrangeItem;
import com.rysh.module.grange.beans.GrangeSpec;
import com.rysh.module.grange.mapper.*;
import com.rysh.module.mobileUser.beans.UserIdAndScore;
import com.rysh.module.mobileUser.mapper.UserMapper;
import com.rysh.module.serverOrders.beans.CompanyIdAndState;
import com.rysh.module.serverOrders.mapper.BackGroundOrdersMapper;
import com.rysh.module.serverSystem.beans.Store;
import com.rysh.module.serverSystem.mapper.StoreMapper;
import com.rysh.module.shop.beans.Shop;
import com.rysh.module.shop.beans.ShopCategory;
import com.rysh.module.shop.beans.ShopItem;
import com.rysh.module.shop.beans.ShopSpec;
import com.rysh.module.shop.mapper.*;
import com.rysh.module.store.beans.StoreCategory;
import com.rysh.module.store.beans.StoreItem;
import com.rysh.module.store.beans.StoreSpec;
import com.rysh.module.store.mapper.*;
import com.rysh.module.utils.GenerateUUID;
import com.rysh.module.utils.OrdersNumUtils;
import com.rysh.module.webSocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Transactional
@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private FarmSpecMapper farmSpecMapper;

    @Autowired
    private FarmItemMapper farmItemMapper;

    @Autowired
    private FarmCategoryMapper farmCategoryMapper;

    @Autowired
    private FarmImgMapper farmImgMapper;

    @Autowired
    private FarmMapper farmMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StoreSpecMapper storeSpecMapper;

    @Autowired
    private StoreItemMapper storeItemMapper;

    @Autowired
    private StoreCategoryMapper storeCategoryMapper;

    @Autowired
    private StoreImgMapper storeImgMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private StoreMapper2 storeMapper2;

    @Autowired
    private GrangeSpecMapper grangeSpecMapper;

    @Autowired
    private GrangeItemMapper grangeItemMapper;

    @Autowired
    private GrangeCategoryMapper grangeCategoryMapper;

    @Autowired
    private GrangeImgMapper grangeImgMapper;

    @Autowired
    private GrangeMapper grangeMapper;

    @Autowired
    private ShopSpecMapper shopSpecMapper;

    @Autowired
    private ShopItemMapper shopItemMapper;

    @Autowired
    private ShopCategoryMapper shopCategoryMapper;

    @Autowired
    private ShopImgMapper shopImgMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    //积分折扣比例
    @Value("${result.discountRate}")
    private Integer discountRate;

    @Autowired
    private LoginAndRegisterMapper loginAndRegisterMapper;

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private OrdersNumUtils ordersNumUtils;

    @Autowired
    private MessageMapper messageMapper;

    //订单未支付 过期时间
    @Value("${result.ordersExpirationTime}")
    private Integer ordersExpirationTime;

    //消费返积分比例
    @Value("${result.consumeRate}")
    private Integer consumeRate;

    //使用积分的最低消费
    @Value("${result.minimums}")
    private Integer minimums;

    @Value("${result.tagId}")
    private String shopId;

    @Autowired
    private BackGroundOrdersMapper backGroundOrdersMapper;

    @Autowired
    private WebSocket webSocket;

    private final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private AlbumMapper albumMapper;

    @Value("${result.remindTime}")
    private Integer remindTime;

    @Value("${result.selfHeadUrl}")
    private String shopUrl;




    /**
     * 添加订单 拆分订单
     * @param orders 订单对象
     * @return
     * @throws Exception
     */
    @Override
    public OrdersIdAndRealPay addOrders(Orders orders) throws Exception {
        //总订单总价初始化
        BigDecimal ordersPrice = new BigDecimal("0");
        //总积分初始化
        BigDecimal score = new BigDecimal("0");
        //设置总订单基本信息
        //设置总订单id
        orders.setId(UUID.randomUUID().toString().toUpperCase().replace("-", ""));
        //设置下单时间
        orders.setCreatedTime(new Date());

        //通过userId查询用户信息
        User user = loginAndRegisterMapper.findNowUser(orders.getUserId());
        //构建订单号
        if(user.getCommunityId()!=null){
            //通过用户的communityId查询小区信息
            Community community = communityMapper.findCommunityById(user.getCommunityId());
            //通过小区id查询区域信息
            Area area = areaMapper.findAreaById(community.getAreaId());
            //通过区域id查询城市信息
            City city = cityMapper.findCityById(area.getCityId());
            //构建订单号
            String ordersNum = ordersNumUtils.createOrdersNum(city.getCityCode(), area.getAreaCode());
            //设置订单号
            orders.setOrdersNum(ordersNum);
        }
        //添加总订单
        ordersMapper.addOrders(orders);
        //拆分订单
        for (OrdersSplit ordersSplit : orders.getOrdersSplits()) {
            //通过商品id查询店铺id
            OrdersItem ordersItem1 = ordersSplit.getOrdersItems().get(0);
            switch (ordersSplit.getBelongType()){
                case 1:
                    //通过规格id规格信息
                    StoreSpec storeSpec = storeSpecMapper.findById(ordersItem1.getProductSpecId());
                    if(storeSpec!=null){
                        //通过商品id查询商品信息
                        StoreItem storeItem = storeItemMapper.findStoreItemById(storeSpec.getItemId());
                        if(storeItem!=null){
                            //通过分类id查询分类信息
                            StoreCategory storeCategory = storeCategoryMapper.findCategoryById(storeItem.getCategoryId());
                            if(storeCategory!=null){
                                //设置店铺id
                                ordersSplit.setMarketId(storeCategory.getStoreId());
                                //通过店铺id查询店铺
                                Store store = storeMapper.findStoreById(storeCategory.getStoreId());
                                if(store!=null){
                                    //设置运费
                                    ordersSplit.setFreight(store.getFreight());
                                }

                            }
                        }
                    }
                    break;
                case 2:
                     //通过规格id规格信息
                    FarmSpec farmSpec = farmSpecMapper.findById(ordersItem1.getProductSpecId());
                    if(farmSpec!=null){
                        //通过商品id查询商品信息
                        FarmItem farmItem = farmItemMapper.findFarmItemById(farmSpec.getItemId());
                        if(farmItem!=null){
                            //通过分类id查询分类信息
                            FarmCategory farmCategory = farmCategoryMapper.findCategoryById(farmItem.getCategoryId());
                            if(farmCategory!=null){
                                //设置店铺id
                                ordersSplit.setMarketId(farmCategory.getFarmId());
                                //通过店铺id查询店铺
                                FarmAndUser farmAndUser = farmMapper.findOneById(farmCategory.getFarmId());
                                if(farmAndUser!=null){
                                    //设置运费
                                    ordersSplit.setFreight(farmAndUser.getFreight());
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    //通过规格id规格信息
                    GrangeSpec grangeSpec = grangeSpecMapper.findById(ordersItem1.getProductSpecId());
                    if(grangeSpec!=null){
                        //通过商品id查询商品信息
                        GrangeItem grangeItem = grangeItemMapper.findFarmItemById(grangeSpec.getItemId());
                        if(grangeItem!=null){
                            //通过分类id查询分类信息
                            GrangeCategory grangeCategory = grangeCategoryMapper.findCategoryById(grangeItem.getCategoryId());
                            if(grangeCategory!=null){
                                //设置店铺id
                                ordersSplit.setMarketId(grangeCategory.getGrangeId());
                                //通过店铺id查询店铺
                                Grange grange = grangeMapper.findById(grangeCategory.getGrangeId());
                                if(grange!=null){
                                    //设置运费
                                    ordersSplit.setFreight(grange.getFreight());
                                }
                            }
                        }
                    }
                    break;
                case 4:
                    Shop shop = shopMapper.findById(shopId);
                    //设置店铺id
                    ordersSplit.setMarketId(shopId);
                    if(shop!=null){
                        //设置运费
                        ordersSplit.setFreight(shop.getFreight());
                    }
                    break;
            }
            //子订单总价初始化
            BigDecimal ordersSplitPrice = new BigDecimal("0");
            //设置子订单基本信息
            //子订单id
            ordersSplit.setId(UUID.randomUUID().toString().toUpperCase().replace("-", ""));
            //子订单生成时间
            ordersSplit.setCreatedTime(new Date());
            //设置积分兑换比例
            ordersSplit.setDiscountRate(discountRate);
            //关联总订单
            ordersSplit.setOrdersId(orders.getId());
//            //设置运费
//            switch (ordersSplit.getBelongType()){
//                case 1:
//                    Store store = storeMapper.findStoreById(ordersSplit.getMarketId());
//                    if(store!=null){
//                        ordersSplit.setFreight(store.getFreight());
//                    }
//                    break;
//                case 2:
//                    FarmAndUser farmAndUser = farmMapper.findOneById(ordersSplit.getMarketId());
//                    if(farmAndUser!=null){
//                        ordersSplit.setFreight(farmAndUser.getFreight());
//                    }
//                    break;
//                case 3:
//                    Grange grange = grangeMapper.findById(ordersSplit.getMarketId());
//                    if(grange!=null){
//                        ordersSplit.setFreight(grange.getFreight());
//                    }
//                    break;
//                case 4:
//                    Shop shop = shopMapper.findById(ordersSplit.getMarketId());
//                    if(shop!=null){
//                        ordersSplit.setFreight(shop.getFreight());
//                    }
//                    break;
//            }
            //添加子订单
            ordersMapper.addOrdersSplit(ordersSplit);
            //添加订单明细
            for (OrdersItem ordersItem : ordersSplit.getOrdersItems()) {

                switch (ordersSplit.getBelongType()) {
                    //1=商铺   2=农场  3=农庄  4=自营
                    case 1:
                        //商铺不能使用积分
                        ordersSplit.setUsedSorce(0);
                        //通过商品id查询商品规格和单价
                        StoreSpec storeSpec=storeSpecMapper.findById(ordersItem.getProductSpecId());
                        //设置商品单价
                        ordersItem.setPrice(storeSpec.getPrice());
                        //设置商品规格名称
                        ordersItem.setUnit(storeSpec.getUnit());
                        //设置商品名称
                        StoreItem storeItem = storeItemMapper.findStoreItemById(storeSpec.getItemId());
                        ordersItem.setProductName(storeItem.getName());
                        //设置商品id
                        ordersItem.setProductItemId(storeItem.getId());
                        //设置商品缩略图
                        List<String> storeImgs = storeImgMapper.findStoreBannerImgUrlByItemId(storeItem.getId());
                        if(storeImgs.size()>0){
                            ordersItem.setProductUrl(storeImgs.get(0));
                        }
                        //设置商品分类名称
                        StoreCategory storeCategory = storeCategoryMapper.findCategoryById(storeItem.getCategoryId());
                        ordersItem.setCategoryName(storeCategory.getName());
                        //计算累加子订单总价
                        ordersSplitPrice=ordersSplitPrice.add(storeSpec.getPrice().multiply(new BigDecimal(ordersItem.getQuantity().toString())));
                        break;
                    case 2:
                        //通过商品id查询商品规格和单价
                        FarmSpec farmSpec = farmSpecMapper.findById(ordersItem.getProductSpecId());
                        //设置商品单价
                        ordersItem.setPrice(farmSpec.getPrice());
                        //设置商品规格
                        ordersItem.setUnit(farmSpec.getUnit());
                        //设置商品名称
                        FarmItem farmItem = farmItemMapper.findFarmItemById(farmSpec.getItemId());
                        ordersItem.setProductName(farmItem.getName());
                        //设置商品id
                        ordersItem.setProductItemId(farmItem.getId());
                        //设置商品分类名称
                        FarmCategory farmCategory = farmCategoryMapper.findCategoryById(farmItem.getCategoryId());
                        ordersItem.setCategoryName(farmCategory.getName());
                        //设置商品缩略图
                        List<String> farmImgs = farmImgMapper.findFarmBannerImgUrlByItemId(farmSpec.getItemId());
                        if(farmImgs.size()>0){
                            ordersItem.setProductUrl(farmImgs.get(0));
                        }
                        //计算累加子订单总价
                        ordersSplitPrice=ordersSplitPrice.add(farmSpec.getPrice().multiply(new BigDecimal(ordersItem.getQuantity().toString())));
                        break;
                    case 3:
                        GrangeSpec grangeSpec = grangeSpecMapper.findById(ordersItem.getProductSpecId());
                        ordersItem.setPrice(grangeSpec.getPrice());
                        //设置商品规格
                        ordersItem.setUnit(grangeSpec.getUnit());
                        //设置商品名称
                        GrangeItem grangeItem = grangeItemMapper.findFarmItemById(grangeSpec.getItemId());
                        ordersItem.setProductName(grangeItem.getName());
                        //设置商品id
                        //设置商品id
                        ordersItem.setProductItemId(grangeItem.getId());
                        //设置商品分类id
                        GrangeCategory grangeCategory = grangeCategoryMapper.findCategoryById(grangeItem.getCategoryId());
                        ordersItem.setCategoryName(grangeCategory.getName());
                        //设置商品缩略图
                        List<String> grangeImgs = grangeImgMapper.findGrangeBannerImgUrl(grangeSpec.getItemId());
                        if(grangeImgs.size()>0){
                            ordersItem.setProductUrl(grangeImgs.get(0));
                        }
                        //计算累加子订单总价
                        ordersSplitPrice=ordersSplitPrice.add(grangeSpec.getPrice().multiply(new BigDecimal(ordersItem.getQuantity().toString())));
                        break;
                    case 4:
                        ShopSpec shopSpec=shopSpecMapper.findById(ordersItem.getProductSpecId());
                        ordersItem.setPrice(shopSpec.getPrice());
                        //设置商品规格
                        ordersItem.setUnit(shopSpec.getUnit());
                        //设置商品名称
                        ShopItem shopItem = shopItemMapper.findShopItemById(shopSpec.getItemId());
                        ordersItem.setProductName(shopItem.getName());
                        //设置商品id
                        ordersItem.setProductItemId(shopItem.getId());
                        //设置商品分类名称
                        ShopCategory shopCategory = shopCategoryMapper.findCategoryById(shopItem.getCategoryId());
                        ordersItem.setCategoryName(shopCategory.getName());
                        //设置商品缩略图
                        List<String> shopImgs = shopImgMapper.findShopBannerImgUrlByItemId(shopSpec.getItemId());
                        if(shopImgs.size()>0){
                            ordersItem.setProductUrl(shopImgs.get(0));
                        }
                        //计算累加子订单总价
                        ordersSplitPrice=ordersSplitPrice.add(shopSpec.getPrice().multiply(new BigDecimal(ordersItem.getQuantity().toString())));
                        break;
                }
                //订单明细id
                ordersItem.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
                //为商品明细设置子订单id
                ordersItem.setOrdersSplitId(ordersSplit.getId());
                //设置订单明细创建时间
                ordersItem.setCreatedTime(new Date());
                //添加订单明细
                ordersMapper.addOrdersItem(ordersItem);
                //删除购物车
                if(ordersItem.getShoppingCartId()!=null){
                    shoppingCartMapper.deleteShoppingCartById(ordersItem.getShoppingCartId());
                }
            }
            //单个子订单消费金额小于minimums元 不能使用积分
            if(ordersSplitPrice.compareTo(new BigDecimal(minimums.toString()))==-1){
                ordersSplit.setUsedSorce(0);
            }
            //计算用户使用的积分
            score=score.add(new BigDecimal(ordersSplit.getUsedSorce()));
            //用户使用的积分
            BigDecimal scoreB = new BigDecimal(ordersSplit.getUsedSorce().toString());
            //积分兑换比例
            BigDecimal discountRateB=new BigDecimal(discountRate.toString());
            //计算子订单抵扣积分后的金额
            BigDecimal divide = scoreB.divide(discountRateB);
            ordersSplitPrice=ordersSplitPrice.subtract(divide);
            //计算子订单加上运费之后的价格
            ordersSplitPrice=ordersSplitPrice.add(ordersSplit.getFreight());
            //如果实付金额小于等于0   则付0元
            BigDecimal b = new BigDecimal("0");
            if(ordersSplitPrice.compareTo(b)==0||ordersSplitPrice.compareTo(b)==-1){
                ordersSplitPrice=b;
            }
            //添加子订单金额到数据库
            ordersMapper.addOrdersSplitRealPay(ordersSplitPrice,ordersSplit.getId());
            //累加总订单金额
            ordersPrice=ordersPrice.add(ordersSplitPrice);
        }
        //比较前端与后端计算的金额是否相等 A compareTo B   1  A>B   0  A=B   -1 A<B
        if(ordersPrice.compareTo(orders.getRealPay())==0){

        }else {
            throw new RuntimeException("前后端金额不同 前:"+orders.getRealPay().toString()+"   后:"+ordersPrice.toString());
        }

        //将总订单id存入redis中 并设置过期时间
        stringRedisTemplate.opsForValue().set(orders.getId(), " ");
        stringRedisTemplate.expire(orders.getId(), ordersExpirationTime, TimeUnit.SECONDS);
        if(new BigDecimal(user.getScore()).compareTo(score)==-1){
            throw new RuntimeException("用户积分不足 用户积分:"+user.getScore()+"   使用积分："+score);
        }else {
            //修改用户积分
            UserIdAndScore us = new UserIdAndScore();
            us.setUserId(user.getId());
            us.setScore(score.intValue());
            userMapper.updateUserScore(us);
        }

        OrdersIdAndRealPay ordersIdAndRealPay = new OrdersIdAndRealPay();
        ordersIdAndRealPay.setOrderId(orders.getId());
        ordersIdAndRealPay.setRealPay(orders.getRealPay());
        return ordersIdAndRealPay;
    }

    /**
     * 支付成功之后修改订单支付状态
     * @param ordersId
     */
    @Override
    public void updateState(String ordersId,Integer payWay) {
        //通过总订单id查询总订单信息
        Orders orders = ordersMapper.findOrdersByOrderId(ordersId);
        if(orders!=null&&orders.getState()!=2){
            //删除redis中的订单信息
            stringRedisTemplate.delete(ordersId);
            //修改订单总表支付状态 和支付时间
            StateAndOrdersId so = new StateAndOrdersId();
            so.setOrdersId(ordersId);
            so.setState(2);
            ordersMapper.updateOrdersState(so,payWay,new Date());
            //修改订单拆分表支付状态
            so.setState(1);
            ordersMapper.updateOrdersSplitStatus(so);
            //创建消息对象
            Message message = new Message();
            //设置消息属性
            //主键id
            message.setId(GenerateUUID.create());
            //消息对应的用户id
            message.setUserId(orders.getUserId());
            //消息体
            message.setContent("订单号为："+orders.getOrdersNum()+" 的订单已支付成功。");
            //消息创建时间
            message.setCreatedTime(new Date());
            //存入消息表
            messageMapper.sendMessage(message);
            //将用户在每家店使用的积分 增加给商家
            List<OrdersSplit> ordersSplits = ordersMapper.findAllSplitByOrdersId(ordersId);
            //给商家增加积分  只有农场农庄有积分
            for (OrdersSplit ordersSplit : ordersSplits) {
                switch (ordersSplit.getBelongType()){
                    case 2:
                        //增加农场商家积分
                        farmMapper.updateScoreById(ordersSplit.getMarketId(),ordersSplit.getUsedSorce());
                        break;
                    case 3:
                        //增加农庄商家积分
                        grangeMapper.updateScoreById(ordersSplit.getMarketId(),ordersSplit.getUsedSorce());
                        break;
                }
                //根据用户消费金额 给用户增加积分
                UserIdAndScore us = new UserIdAndScore();
                us.setUserId(orders.getUserId());
                //0-（子订单实付金额 减 运费 and 舍弃小数）= 用户应获得的积分
                us.setScore(0-(ordersSplit.getRealPlay().subtract(ordersSplit.getFreight()).setScale(0,BigDecimal.ROUND_DOWN).intValue()));
                userMapper.updateUserScore(us);
                //通过websocket向前端推送订单信息
                CompanyIdAndState companyIdAndState = new CompanyIdAndState();
                companyIdAndState.setState(1);
                companyIdAndState.setCompanyId(ordersSplit.getMarketId());
                List<OrdersSplit> ordersSplits1 = backGroundOrdersMapper.findOrdersByCompanyId(companyIdAndState);
                for (OrdersSplit split : ordersSplits1) {
                    Orders orders1 = ordersMapper.findOrdersByOrderId(split.getOrdersId());
                    split.setOrdersNum(orders1.getOrdersNum());
                }
                String s = JSONArray.toJSONString(ordersSplits1);
                webSocket.sendMsg(ordersSplit.getMarketId(),s);
            }
        }
    }

    /**
     * 根据条件和用户id查询订单
     * @param userId
     * @return
     */
    @Override
    public List<Orders> findOrdersByUserId(String userId, Integer ordersState, Integer ordersSplitStatus, Integer ordersSplitState) throws Exception {
        //通过userid查询未支付订单总表数据
        List<Orders> orders = ordersMapper.findOrdersByUserIdAndState(userId,ordersState,ordersSplitStatus,ordersSplitState);
        //设置订单的基本信息
        for (Orders order : orders) {
            //设置总订单的时间字符串
            order.setCreatedTimeStr(sf.format(order.getCreatedTime()));
            //查询子订单
            OrdersIdAndStateAndStatue oss = new OrdersIdAndStateAndStatue();
            oss.setOrdersId(order.getId());
            oss.setStatus(ordersSplitStatus);
            oss.setState(ordersSplitState);
            List<OrdersSplit> ordersSplits = ordersMapper.findOrdersSplitByOrdersId(oss);
            //查询子订单下的商品明细
            for (OrdersSplit ordersSplit : ordersSplits) {
                //设置子订单时间字符串
                ordersSplit.setCreatedTimeStr(sf.format(ordersSplit.getCreatedTime()));
                List<OrdersItem> ordersItems = ordersMapper.findOrdersItemByOrdersSplitId(ordersSplit.getId());
                //为子订单设置所属店铺信息
                for (OrdersItem ordersItem : ordersItems) {
                    //设置商品明细时间字符串
                    ordersItem.setCreatedTimeStr(sf.format(ordersItem.getCreatedTime()));
                    switch (ordersSplit.getBelongType()) {
                        case 1:
                              Store store = storeMapper.findStoreByIdToOrders(ordersSplit.getMarketId());
                            //为子订单设置店铺信息
                            ordersSplit.setMarketId(store.getId());  //店铺id
                            ordersSplit.setMarketName(store.getName());  //店铺名称
                            ordersSplit.setMarketUrl(storeMapper2.findAlbumCoverByMarketId(store.getId()));  //店铺头像
                            //商品数量*商品单价
                            ordersItem.setGoodsPrice(ordersItem.getPrice().multiply(new BigDecimal(ordersItem.getQuantity())));
                            break;
                        case 2:
                            Farm farm = farmMapper.findOneByIdToOrders(ordersSplit.getMarketId());
                            //为子订单设置店铺信息
                            ordersSplit.setMarketId(farm.getId());  //店铺id
                            ordersSplit.setMarketName(farm.getName());  //店铺名称
                            ordersSplit.setMarketUrl(albumMapper.findFarmCoverImg(farm.getId()));  //店铺头像
                            //商品数量*商品单价
                            ordersItem.setGoodsPrice(ordersItem.getPrice().multiply(new BigDecimal(ordersItem.getQuantity())));
                            break;
                        case 3:
                            Grange grange = grangeMapper.findByIdToOrders(ordersSplit.getMarketId());
                            //为子订单设置店铺信息
                            ordersSplit.setMarketId(grange.getId());  //店铺id
                            ordersSplit.setMarketName(grange.getName());  //店铺名称
                            ordersSplit.setMarketUrl(albumMapper.findGrangeCoverImg(grange.getId()));  //店铺头像
                            //商品数量*商品单价
                            ordersItem.setGoodsPrice(ordersItem.getPrice().multiply(new BigDecimal(ordersItem.getQuantity())));
                            break;
                        case 4:
                            Shop shop = shopMapper.findByIdToOrders(ordersSplit.getMarketId());
                            //为子订单设置店铺信息
                            ordersSplit.setMarketId(shop.getId());  //店铺id
                            ordersSplit.setMarketName(shop.getName());  //店铺名称
                            //商品数量*商品单价
                            ordersSplit.setMarketUrl(shopUrl);
                            ordersItem.setGoodsPrice(ordersItem.getPrice().multiply(new BigDecimal(ordersItem.getQuantity())));
                            break;
                    }
                }
                //为子订单设置商品明细
                ordersSplit.setOrdersItems(ordersItems);
                //通过子订单id查询商品评价情况
                ScoreUser scoreUser=ordersMapper.findScoreUser(ordersSplit.getId(),userId);
                if(scoreUser==null){
                    ordersSplit.setMarkState(-1);
                }else {
                    ordersSplit.setMarkState(scoreUser.getScore());
                }
                //是否提醒发货
                String twoOrdersSplitId=ordersSplit.getId()+ordersSplit.getId();
                Object o = stringRedisTemplate.opsForValue().get(twoOrdersSplitId);
                if(o==null){
                    ordersSplit.setRemindState(true);
                }else {
                    ordersSplit.setRemindState(false);
                }
            }
            //设置子订单
            order.setOrdersSplits(ordersSplits);
        }

        if(ordersState==1){
            //是查询未支付订单  设置截至时间
            for (Orders order : orders) {
                order.setExpirationTime(order.getCreatedTime().getTime()+ordersExpirationTime*1000l);
            }
        }

        return orders;
    }

    /**
     * 查询该用户所有订单
     * @param userId
     * @return
     */
    @Override
    public List<Orders> findAllOrdersByUserId(String userId, ParamBean paramBean) {
        //设置分页参数
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        //排序参数
        PageHelper.orderBy("created_time desc");
        //根据用户id查询所有的总订单
        List<Orders> orderss = ordersMapper.findAllOrdersByUserId(userId);
        for (Orders orders : orderss) {
            //设置分页参数
            PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
            //排序参数
            PageHelper.orderBy("created_time desc");
            //根据总订单id查询下面的所有子订单
            List<OrdersSplit> ordersSplits = ordersMapper.findAllSplitByOrdersId(orders.getId());
            for (OrdersSplit ordersSplit : ordersSplits) {
                //设置子订单基本信息
                //设置子订单时间字符串
                ordersSplit.setCreatedTimeStr(sf.format(ordersSplit.getCreatedTime()));
                //设置分页参数
                PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
                //排序参数
                PageHelper.orderBy("created_time desc");
                //查询子订单下的商品明细
                List<OrdersItem> ordersItems = ordersMapper.findOrdersItemByOrdersSplitId(ordersSplit.getId());
                //为子订单设置所属店铺信息
                for (OrdersItem ordersItem : ordersItems) {
                    //设置商品明细时间字符串
                    ordersItem.setCreatedTimeStr(sf.format(ordersItem.getCreatedTime()));
                    switch (ordersSplit.getBelongType()) {
                        case 1:
                            Store store = storeMapper.findStoreByIdToOrders(ordersSplit.getMarketId());
                            //为子订单设置店铺信息
                            ordersSplit.setMarketId(store.getId());  //店铺id
                            ordersSplit.setMarketName(store.getName());  //店铺名称
                            ordersSplit.setMarketUrl(storeMapper2.findAlbumCoverByMarketId(store.getId()));  //店铺头像
                            //商品单价*商品数量
                            ordersItem.setGoodsPrice(ordersItem.getPrice().multiply(new BigDecimal(ordersItem.getQuantity())));
                            break;
                        case 2:
                            Farm farm = farmMapper.findOneByIdToOrders(ordersSplit.getMarketId());
                            //为子订单设置店铺信息
                            ordersSplit.setMarketId(farm.getId());  //店铺id
                            ordersSplit.setMarketName(farm.getName());  //店铺名称
                            ordersSplit.setMarketUrl(albumMapper.findFarmCoverImg(farm.getId()));  //店铺头像
                            //商品单价*商品数量
                            ordersItem.setGoodsPrice(ordersItem.getPrice().multiply(new BigDecimal(ordersItem.getQuantity())));
                            break;
                        case 3:
                            Grange grange = grangeMapper.findByIdToOrders(ordersSplit.getMarketId());
                            //为子订单设置店铺信息
                            ordersSplit.setMarketId(grange.getId());  //店铺id
                            ordersSplit.setMarketName(grange.getName());  //店铺名称
                            ordersSplit.setMarketUrl(albumMapper.findGrangeCoverImg(grange.getId()));  //店铺头像
                            //商品单价*商品数量
                            ordersItem.setGoodsPrice(ordersItem.getPrice().multiply(new BigDecimal(ordersItem.getQuantity())));
                            break;
                        case 4:
                            Shop shop = shopMapper.findByIdToOrders(ordersSplit.getMarketId());
                            //为子订单设置店铺信息
                            ordersSplit.setMarketId(shop.getId());  //店铺id
                            ordersSplit.setMarketName(shop.getName());  //店铺名称
                            //商品单价*商品数量
                            ordersItem.setGoodsPrice(ordersItem.getPrice().multiply(new BigDecimal(ordersItem.getQuantity())));
                            break;
                    }
                }
                //为子订单设置商品明细
                ordersSplit.setOrdersItems(ordersItems);
            }
            //设置总订单下的子订单
            orders.setOrdersSplits(ordersSplits);
        }

        return orderss;
    }

    /**
     * 取消订单
     * @param ordersId
     */
    @Override
    public Boolean cancelOrders(String uid,String ordersId) throws Exception {
        //通过userid校验用户身份
        List<String> ordersIds = ordersMapper.findAllOrdersIdByUserId(uid);
        if(ordersIds.contains(ordersId)){
            //修改总订单状态
            StateAndOrdersId so = new StateAndOrdersId();
            so.setOrdersId(ordersId);
            so.setState(3);
            ordersMapper.updateOrdersState(so,null,null);
            //通过ordersId查询总订单信息
            Orders orders = ordersMapper.findOrdersByOrderId(ordersId);
            //通过总订单id查询子订单集合
            OrdersIdAndStateAndStatue oss = new OrdersIdAndStateAndStatue();
            oss.setOrdersId(orders.getId());
            oss.setState(1);
            oss.setStatus(1);
            List<OrdersSplit> ordersSplits = ordersMapper.findOrdersSplitByOrdersId(oss);
            //使用总积分初始化
            Integer score=0;
            for (OrdersSplit ordersSplit : ordersSplits) {
                if(ordersSplit.getUsedSorce()!=null){
                    score+=ordersSplit.getUsedSorce();
                }
            }
            //归还用户积分
            UserIdAndScore us = new UserIdAndScore();
            us.setUserId(orders.getUserId());
            us.setScore(0-score);
            userMapper.updateUserScore(us);
            return true;
        }
            return false;
    }

    /**
     * 给店铺评分
     * @param splitId  子订单id
     * @param score   分数
     * @throws Exception
     */
    @Override
    public void assessmentMarket(String splitId, Integer score) throws Exception {
        //构建ScoreUser对象
        ScoreUser scoreUser = new ScoreUser();
        //通过子订单id查询子订单
        OrdersSplit ordersSplit = ordersMapper.findOrdersSplitById(splitId);
        //通过总订单id查询总订单
        Orders orders = ordersMapper.findOrdersByOrderId(ordersSplit.getOrdersId());
        //设置主键id
        scoreUser.setId(GenerateUUID.create());
        //设置子订单id
        scoreUser.setOrdersSplitId(splitId);
        //设置评价分数
        scoreUser.setScore(score);
        //设置userId
        scoreUser.setUserId(orders.getUserId());
        //设置创建时间
        scoreUser.setCreatedTime(new Date());
        //添加评分
        ordersMapper.addScoreUser(scoreUser);
    }


    /**
     * 查询店铺的评分
     * @param marketId
     * @return
     */
    @Override
    public BigDecimal findScoreByMarketId(String marketId) {
        //评分集合初始化
        List<Integer> scores = new ArrayList<>();
        //查询这个店铺的所有子订单
        List<String> splitIds=ordersMapper.findSplitIdsByMarketId(marketId);
        //通过子订单查询评分信息
        for (String splitId : splitIds) {
            Integer score=ordersMapper.findScoreBySplitId(splitId);
            scores.add(score);
        }
        //总分初始化
        BigDecimal sumScore = new BigDecimal("0");
        //计算总分
        for (Integer score : scores) {
            sumScore=sumScore.add(new BigDecimal(score.toString()));
        }

        //计算平均分
        Integer scoresSize=new Integer(scores.size());
        if(scoresSize==0){
            return new BigDecimal("0");
        }
        BigDecimal avgScore = sumScore.divide(new BigDecimal(scoresSize.toString()),1,BigDecimal.ROUND_HALF_UP);
        return avgScore;
    }


    /**
     * 通过商品类型  商品id构建订单详细
     * @param falseOrders
     */
    public Orders findOrdersByGoods(List<FalseOrders> falseOrders){
        //店铺id集合初始化
        Map<Integer,List<String>> map=new HashMap<>();
        map.put(1,new ArrayList<>());
        map.put(2,new ArrayList<>());
        map.put(3,new ArrayList<>());
        map.put(4,new ArrayList<>());

        for (FalseOrders falseOrder : falseOrders) {
            switch (falseOrder.getIsInsider()){
                case 1:
                    //通过商品规格id查询商品规格
                    StoreSpec storeSpec = storeSpecMapper.findById(falseOrder.getGoodsSpecId());
                    //通过商品id查询商品
                    StoreItem storeItem = storeItemMapper.findStoreItemById(storeSpec.getItemId());
                    //通过商品分类id查询商品分类
                    StoreCategory storeCategory = storeCategoryMapper.findCategoryById(storeItem.getCategoryId());
                    //将店铺id添加到集合
                    if(!map.get(1).contains(storeCategory.getStoreId())){
                        map.get(1).add(storeCategory.getStoreId());
                    }
                    break;
                case 2:
                    //通过商品规格id查询商品规格
                    FarmSpec farmSpec = farmSpecMapper.findById(falseOrder.getGoodsSpecId());
                    //通过商品id查询商品
                    FarmItem farmItem = farmItemMapper.findFarmItemById(farmSpec.getItemId());
                    //通过商品分类id查询商品分类
                    FarmCategory farmCategory = farmCategoryMapper.findCategoryById(farmItem.getCategoryId());
                    //将店铺id添加到集合
                    if(!map.get(2).contains(farmCategory.getFarmId())){
                        map.get(2).add(farmCategory.getFarmId());
                    }
                    break;
                case 3:
                    //通过商品规格id查询商品规格
                    GrangeSpec grangeSpec = grangeSpecMapper.findById(falseOrder.getGoodsSpecId());
                    //通过商品id查询商品
                    GrangeItem grangeItem = grangeItemMapper.findFarmItemById(grangeSpec.getItemId());
                    //通过商品分类id查询商品分类
                    GrangeCategory grangeCategory = grangeCategoryMapper.findCategoryById(grangeItem.getCategoryId());
                    //将店铺id添加到集合
                    if(!map.get(3).contains(grangeCategory.getGrangeId())){
                        map.get(3).add(grangeCategory.getGrangeId());
                    }
                    break;
                case 4:
                    //自营商城只有一个  直接添加
                    if(map.get(4).contains(shopId)){
                        map.get(4).add(shopId);
                    }
                    break;
            }
        }
        //子订单对象集合初始化
        List<OrdersSplit> ordersSplits=new ArrayList<>();
        //为每个店铺创建一个ordersSplit对象
        for (Integer integer : map.keySet()) {
            for (String s : map.get(integer)) {
                OrdersSplit ordersSplit = new OrdersSplit();
                //设置店铺类型
                ordersSplit.setBelongType(integer);
                //设置店铺id
                ordersSplit.setMarketId(s);
                switch (integer){
                    case 1:
                        Store store = storeMapper.findStoreById(s);
                        //店铺名称
                        ordersSplit.setMarketName(store.getName());
                        //运费
                        ordersSplit.setFreight(store.getFreight());
                        break;
                    case 2:
                        FarmAndUser farmAndUser = farmMapper.findOneById(s);
                        //店铺名称
                        ordersSplit.setMarketName(farmAndUser.getName());
                        //运费
                        ordersSplit.setFreight(farmAndUser.getFreight());
                        break;
                    case 3:
                        Grange grange = grangeMapper.findById(s);
                        //店铺名称
                        ordersSplit.setMarketName(grange.getName());
                        //运费
                        ordersSplit.setFreight(grange.getFreight());
                        break;
                    case 4:
                        Shop shop = shopMapper.findById(s);
                        //店铺名称
                        ordersSplit.setMarketName(shop.getName());
                        //运费
                        ordersSplit.setFreight(shop.getFreight());
                        break;
                }
                ordersSplits.add(ordersSplit);
            }
        }
        //订单实际支付总金额初始化
        BigDecimal totalPrice = new BigDecimal("0");
        //添加子订单下的商品明细
        for (OrdersSplit ordersSplit : ordersSplits) {
            //商品明细集合初始化
            List<OrdersItem> ordersItems=new ArrayList<>();
            for (FalseOrders falseOrder : falseOrders) {
                switch (falseOrder.getIsInsider()){
                    case 1:
                        //通过商品规格id查询商品规格
                        StoreSpec storeSpec = storeSpecMapper.findById(falseOrder.getGoodsSpecId());
                        //通过商品id查商品
                        StoreItem storeItem = storeItemMapper.findStoreItemById(storeSpec.getItemId());
                        //通过分类id查分类
                        StoreCategory storeCategory = storeCategoryMapper.findCategoryById(storeItem.getCategoryId());
                        //将同一店铺的商品添加进集合
                        if(ordersSplit.getMarketId().equals(storeCategory.getStoreId())){
                            //构建ordesItem对象
                            OrdersItem ordersItem = new OrdersItem();
                            //设置属性
                            //商品名称
                            ordersItem.setProductName(storeItem.getName());
                            //商品单价
                            ordersItem.setPrice(storeSpec.getPrice());
                            //商品规格
                            ordersItem.setUnit(storeSpec.getUnit());
                            //通过商品id查询图片信息
                            List<String> storeImgs = storeImgMapper.findStoreBannerImgUrlByItemId(storeSpec.getItemId());
                            if(storeImgs.size()>0){
                                //商品图片
                                ordersItem.setProductUrl(storeImgs.get(0));
                            }
                            //商品数量
                            ordersItem.setQuantity(falseOrder.getQuantity());
                            //商品数量*商品单价
                            BigDecimal storePrice = storeSpec.getPrice().multiply(new BigDecimal(falseOrder.getQuantity().toString()));
                            totalPrice=totalPrice.add(storePrice).subtract(ordersSplit.getFreight());
                            ordersItem.setGoodsPrice(storePrice);
                            //商品id
                            ordersItem.setProductSpecId(storeSpec.getId());
                            //添加进集合
                            ordersItems.add(ordersItem);
                        }
                        break;
                    case 2:
                        //通过商品规格id查询商品规格
                        FarmSpec farmSpec = farmSpecMapper.findById(falseOrder.getGoodsSpecId());
                        //通过商品id查询商品
                        FarmItem farmItem = farmItemMapper.findFarmItemById(farmSpec.getItemId());
                        //通过分类id查询分类
                        FarmCategory farmCategory = farmCategoryMapper.findCategoryById(farmItem.getCategoryId());
                        //将同一店铺的商品加入集合
                        if(ordersSplit.getMarketId().equals(farmCategory.getFarmId())){
                            //构建ordesItem对象
                            OrdersItem ordersItem = new OrdersItem();
                            //设置属性
                            //商品名称
                            ordersItem.setProductName(farmItem.getName());
                            //商品单价
                            ordersItem.setPrice(farmSpec.getPrice());
                            //商品规格
                            ordersItem.setUnit(farmSpec.getUnit());
                            //通过商品id查询商品图片信息
                            List<String> farmImgs = farmImgMapper.findFarmBannerImgUrlByItemId(farmSpec.getItemId());
                            if(farmImgs.size()>0){
                                //设置商品图片
                                ordersItem.setProductUrl(farmImgs.get(0));
                            }
                            //商品数量
                            ordersItem.setQuantity(falseOrder.getQuantity());
                            //商品数量*商品单价
                            BigDecimal farmPrice = farmSpec.getPrice().multiply(new BigDecimal(falseOrder.getQuantity().toString()));
                            totalPrice=totalPrice.add(farmPrice).subtract(ordersSplit.getFreight());
                            ordersItem.setGoodsPrice(farmPrice);
                            //商品id
                            ordersItem.setProductSpecId(farmSpec.getId());
                            //添加进集合
                            ordersItems.add(ordersItem);
                        }
                        break;
                    case 3:
                        //通过商品规格id查询商品规格
                        GrangeSpec grangeSpec = grangeSpecMapper.findById(falseOrder.getGoodsSpecId());
                        //通过商品id查询商品
                        GrangeItem grangeItem = grangeItemMapper.findFarmItemById(grangeSpec.getItemId());
                        //通过分类id查询分类
                        GrangeCategory grangeCategory = grangeCategoryMapper.findCategoryById(grangeItem.getCategoryId());
                        //将同一店铺的商品加入集合
                        if(ordersSplit.getMarketId().equals(grangeCategory.getGrangeId())){
                            //构建ordesItem对象
                            OrdersItem ordersItem = new OrdersItem();
                            //设置属性
                            //商品名称
                            ordersItem.setProductName(grangeItem.getName());
                            //商品单价
                            ordersItem.setPrice(grangeSpec.getPrice());
                            //商品规格
                            ordersItem.setUnit(grangeSpec.getUnit());
                            //通过商品id查询商品图片信息
                            List<String> grangeImgs = grangeImgMapper.findGrangeBannerImgUrl(grangeSpec.getItemId());
                            if(grangeImgs.size()>0){
                                //设置商品图片
                                ordersItem.setProductUrl(grangeImgs.get(0));
                            }
                            //商品数量
                            ordersItem.setQuantity(falseOrder.getQuantity());
                            //商品数量*商品单价
                            BigDecimal grangePrice = grangeSpec.getPrice().multiply(new BigDecimal(falseOrder.getQuantity().toString()));
                            totalPrice=totalPrice.add(grangePrice).subtract(ordersSplit.getFreight());
                            ordersItem.setGoodsPrice(grangePrice);
                            //商品id
                            ordersItem.setProductSpecId(grangeSpec.getId());
                            //添加进集合
                            ordersItems.add(ordersItem);
                        }
                        break;
                    case 4:
                        //通过商品规格id查询商品规格
                        ShopSpec shopSpec = shopSpecMapper.findById(falseOrder.getGoodsSpecId());
                        //通过商品id查询商品
                        ShopItem shopItem = shopItemMapper.findShopItemById(shopSpec.getItemId());
                        //通过分类id查询分类
                        ShopCategory shopCategory = shopCategoryMapper.findCategoryById(shopItem.getCategoryId());
                        //将同一店铺的商品加入集合
                        if(ordersSplit.getMarketId().equals(shopCategory.getShopId())){
                            //构建ordesItem对象
                            OrdersItem ordersItem = new OrdersItem();
                            //设置属性
                            //商品名称
                            ordersItem.setProductName(shopItem.getName());
                            //商品单价
                            ordersItem.setPrice(shopSpec.getPrice());
                            //商品规格
                            ordersItem.setUnit(shopSpec.getUnit());
                            //通过商品id查询商品图片信息
                            List<String> shopImgs = shopImgMapper.findShopBannerImgUrlByItemId(shopSpec.getItemId());
                            if(shopImgs.size()>0){
                                //设置商品图片
                                ordersItem.setProductUrl(shopImgs.get(0));
                            }
                            //商品数量
                            ordersItem.setQuantity(falseOrder.getQuantity());
                            //商品数量*商品单价
                            BigDecimal shopPrice = shopSpec.getPrice().multiply(new BigDecimal(falseOrder.getQuantity().toString()));
                            totalPrice=totalPrice.add(shopPrice).subtract(ordersSplit.getFreight());
                            ordersItem.setGoodsPrice(shopPrice);
                            //商品id
                            ordersItem.setProductSpecId(shopSpec.getId());
                            //添加进集合
                            ordersItems.add(ordersItem);
                        }
                        break;
                }
            }
            ordersSplit.setOrdersItems(ordersItems);
        }
        Orders orders = new Orders();
        orders.setRealPay(totalPrice);
        orders.setOrdersSplits(ordersSplits);
        return orders;
    }


    /**
     * 确认收货
     * @param uid
     * @param ordersSplitId
     * @return
     * @throws Exception
     */
    @Override
    public Boolean acceptGoods(String uid, String ordersSplitId) throws Exception{
        //通过子订单id查询子订单
        OrdersSplit ordersSplit = ordersMapper.findOrdersSplitById(ordersSplitId);
        //校验用户身份
        //查询总订单的userId
        Orders orders = ordersMapper.findOrdersByOrderId(ordersSplit.getOrdersId());
        if(!orders.getUserId().equals(uid)){
            return false;
        }
        //修改子订单状态
        ordersMapper.updateOrdersSplitState(ordersSplitId,3);
        return true;
    }


    /**
     * 提醒发货
     * @param uid
     * @param ordersSplitId
     * @return
     * @throws Exception
     */
    @Override
    public String remindDeliverGoods(String uid,String ordersSplitId) throws Exception {
        //96BD2DDB6A0D4A0FBD476D3D305A7851
        String twoOrdersSplitId=ordersSplitId+ordersSplitId;
        Object o = stringRedisTemplate.opsForValue().get(twoOrdersSplitId);
        if(o==null){
            //通过子订单id查询子订单
            OrdersSplit ordersSplit = ordersMapper.findOrdersSplitById(ordersSplitId);
            //通过总订单id查询总订单
            Orders orders = ordersMapper.findOrdersByOrderId(ordersSplit.getOrdersId());
            //校验用户身份
            if(uid.equals(orders.getUserId())){
                //使用calender将子订单创建时间增加一天
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(ordersSplit.getCreatedTime());
                calendar.add(Calendar.DAY_OF_MONTH,1);
                Date time = calendar.getTime();
                //判断当前时间是否大于下单时间+24小时
                if(new Date().getTime()<time.getTime()){
                    //将子订单id存入redis 并设置过期时间
                    stringRedisTemplate.opsForValue().set(twoOrdersSplitId," ");
                    stringRedisTemplate.expire(twoOrdersSplitId,remindTime,TimeUnit.SECONDS);
                    return "商家正在备货，请耐心等待";
                }else {
                    //将子订单id存入redis 并设置过期时间
                    stringRedisTemplate.opsForValue().set(twoOrdersSplitId," ");
                    stringRedisTemplate.expire(twoOrdersSplitId,remindTime,TimeUnit.SECONDS);
                    //查询店铺联系电话
                    String phoneNum="";
                    switch (ordersSplit.getBelongType()){
                        case 1:
                            Store store = storeMapper.findStoreById(ordersSplit.getMarketId());
                            if(store!=null){
                                phoneNum=store.getTel();
                            }
                            break;
                        case 2:
                            Farm farm = farmMapper.findOneByIdToOrders(ordersSplit.getMarketId());
                            if(farm!=null){
                                phoneNum=farm.getContactNum();
                            }
                            break;
                        case 3:
                            Grange grange = grangeMapper.findByIdToOrders(ordersSplit.getMarketId());
                            if(grange!=null){
                                phoneNum=grange.getContactNum();
                            }
                            break;
                        case 4:

                            break;
                    }
                    //向商家发送短信 提醒发货
                    if(!"".equals(phoneNum)&&phoneNum!=null){
                        SendMessage.sendMsg(phoneNum, orders.getOrdersNum());
                    }
                    return "已提醒卖家发货，请耐心等待";
                }
            }else {
                return "滚";
            }
        }else {
            return "请稍后再试";
        }
    }
}
