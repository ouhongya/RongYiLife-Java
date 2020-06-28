package com.rysh.module.serverOrders.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.clientLoginRegister.beans.User;
import com.rysh.module.clientMessage.beans.Message;
import com.rysh.module.clientMessage.mapper.MessageMapper;
import com.rysh.module.clientOrders.beans.Orders;
import com.rysh.module.clientOrders.beans.OrdersItem;
import com.rysh.module.clientOrders.beans.OrdersSplit;
import com.rysh.module.clientOrders.mapper.OrdersMapper;
import com.rysh.module.clientShoppingAddress.beans.ShoppingAddress;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.FarmItem;
import com.rysh.module.farm.beans.FarmSpec;
import com.rysh.module.farm.mapper.*;
import com.rysh.module.grange.beans.GrangeItem;
import com.rysh.module.grange.beans.GrangeSpec;
import com.rysh.module.grange.mapper.*;
import com.rysh.module.mobileUser.mapper.UserMapper;
import com.rysh.module.serverOrders.beans.*;
import com.rysh.module.serverOrders.mapper.BackGroundOrdersMapper;
import com.rysh.module.serverOrders.service.BackGroundOrdersService;
import com.rysh.module.shop.beans.ShopItem;
import com.rysh.module.shop.beans.ShopSpec;
import com.rysh.module.shop.mapper.*;
import com.rysh.module.store.beans.StoreItem;
import com.rysh.module.store.beans.StoreSpec;
import com.rysh.module.store.mapper.StoreImgMapper;
import com.rysh.module.store.mapper.StoreItemMapper;
import com.rysh.module.store.mapper.StoreSpecMapper;
import com.rysh.module.utils.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Transactional
@Service
public class BackGroundOrdersServiceImpl implements BackGroundOrdersService {

    @Autowired
    private BackGroundOrdersMapper backGroundOrdersMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private FarmImgMapper farmImgMapper;

    @Autowired
    private FarmSpecMapper farmSpecMapper;

    @Autowired
    private FarmItemMapper farmItemMapper;

    @Autowired
    private GrangeItemMapper grangeItemMapper;

    @Autowired
    private GrangeImgMapper grangeImgMapper;

    @Autowired
    private GrangeSpecMapper grangeSpecMapper;

    @Autowired
    private ShopItemMapper shopItemMapper;

    @Autowired
    private ShopImgMapper shopImgMapper;

    @Autowired
    private ShopSpecMapper shopSpecMapper;

    @Autowired
    private StoreItemMapper storeItemMapper;

    @Autowired
    private StoreSpecMapper storeSpecMapper;

    @Autowired
    private StoreImgMapper storeImgMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageMapper messageMapper;

    private final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${courier}")
    private List<String> couriers;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //自动确认收货时间
    @Value("${result.confirmReceiptTime}")
    private Long confirmReceiptTime;

    /**
     * 后台根据条件查询订单
     *
     * @param totalState 0状态查询  1模糊订单号  2模糊手机号  3时间区间查询
     * @param companyId  店铺id
     * @param state      订单状态  0全部  1已支付  2已发货 3交易成功
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<OrdersSplit> findOrdersByCompanyId(Integer totalState, String companyId, Integer state, ParamBean paramBean) throws Exception {
        //订单集合初始化
        PageInfo<OrdersSplit> pageInfo = new PageInfo<>();
        if (totalState == 0) {
            //如果state为0  则查所有订单
            if (state == 0) {
                PageHelper.startPage(paramBean.getPage(), paramBean.getSize());
                List<OrdersSplit> ordersSplits = backGroundOrdersMapper.findAllOrdersByCompanyId(companyId);
                //查询订单号
                for (OrdersSplit ordersSplit : ordersSplits) {
                    Orders orders = ordersMapper.findOrdersByOrderId(ordersSplit.getOrdersId());
                    //设置订单号
                    ordersSplit.setOrdersNum(orders.getOrdersNum());
                }
                pageInfo = new PageInfo<>(ordersSplits);
            } else {
                CompanyIdAndState cs = new CompanyIdAndState();
                cs.setCompanyId(companyId);
                //根据状态码查询店铺相关订单
                cs.setState(state);
                //设置分页参数
                PageHelper.startPage(paramBean.getPage(), paramBean.getSize());
                //通过状态和店铺id查询订单
                List<OrdersSplit> ordersSplits = backGroundOrdersMapper.findOrdersByCompanyId(cs);
                //遍历子订单集合 查询订单号
                for (OrdersSplit ordersSplit : ordersSplits) {
                    Orders orders = ordersMapper.findOrdersByOrderId(ordersSplit.getOrdersId());
                    //设置订单号
                    ordersSplit.setOrdersNum(orders.getOrdersNum());
                }
                pageInfo = new PageInfo<>(ordersSplits);
            }
        } else if (totalState == 3) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            //从search中截取起始时间和结束时间
            String timeStr = paramBean.getSearch();
            String[] times = timeStr.split("/");
            //获取起始时间和结束时间
            String startTime = times[0];
            String endTime = times[1];
            //忽略时分秒
            Calendar calendar = Calendar.getInstance();
//            //起始时间String转data
//            Date startData = sf.parse(startTime);
//            //利用Calendar 将起始时间加一天
//            calendar.setTime(startData);
//            //将起始时间增加一天
//            calendar.add(Calendar.DAY_OF_MONTH,1);
//            //获取增加一天之后的时间
//            startData = calendar.getTime();
//            //将增加一天后的时间转为String
//            startTime = sf.format(startData);
//            //清空calendar
//            calendar.clear();
            //结束时间String转data
            Date endDate = sf.parse(endTime);
            //利用Calendar 将结束时间加一天
            //设置结束时间
            calendar.setTime(endDate);
            //将结束时间增加一天
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            //获取增加一天之后的时间
            endDate = calendar.getTime();
            //将增加一天后的时间转为String
            endTime = sf.format(endDate);
            PageHelper.startPage(paramBean.getPage(), paramBean.getSize());
            List<OrdersSplit> ordersSplits = backGroundOrdersMapper.findOrdersByCreatedTime(companyId, startTime, endTime);
            //遍历子订单集合 查询订单号
            for (OrdersSplit ordersSplit : ordersSplits) {
                Orders orders = ordersMapper.findOrdersByOrderId(ordersSplit.getOrdersId());
                //设置订单号
                ordersSplit.setOrdersNum(orders.getOrdersNum());
            }
            pageInfo = new PageInfo<>(ordersSplits);
        } else {
            PageHelper.startPage(paramBean.getPage(), paramBean.getSize());
            List<OrdersSplit> ordersSplits = backGroundOrdersMapper.findOrdersByLikeAgainByCompanyId(companyId, totalState, paramBean.getSearch());
            //遍历子订单集合 查询订单号
            for (OrdersSplit ordersSplit : ordersSplits) {
                Orders orders = ordersMapper.findOrdersByOrderId(ordersSplit.getOrdersId());
                //设置订单号
                ordersSplit.setOrdersNum(orders.getOrdersNum());
                //设置支付时间
                ordersSplit.setPayTime(orders.getPayTime());
            }
            pageInfo = new PageInfo<>(ordersSplits);
        }
        return pageInfo;
    }


    /**
     * 通过id查询子订单信息
     *
     * @param splitId
     * @return
     */
    @Override
    public ImIronMan findOrdersSplitById(String splitId) {
        //钢铁侠初始化（子订单组合对象）
        ImIronMan imIronMan = new ImIronMan();
        //通过id查询子订单
        OrdersSplit ordersSplit = ordersMapper.findOrdersSplitById(splitId);
        //设置钢铁侠基本信息
        //设置订单状态
        imIronMan.setState(ordersSplit.getState());
        //发货人信息初始化
        SendUser sendUser = new SendUser();
        //设置下单时间
        sendUser.setCreatedTimeStr(sf.format(ordersSplit.getCreatedTime()));
        //设置物流编号
        sendUser.setCourierNum(ordersSplit.getCourierNum());
        //查询总订单
        Orders orders = ordersMapper.findOrdersByOrderId(ordersSplit.getOrdersId());
        //通过总订单的userId查询用户信息
        User user = userMapper.findById(orders.getUserId());
        //设置发货人名称
        sendUser.setName(user.getName());
        //设置快递方式
        sendUser.setCourier(ordersSplit.getCourier());
        //设置邮政编码
        sendUser.setZipCode(orders.getZipCode());
        //设置订单编号
        sendUser.setOrderNum(orders.getOrdersNum());
        //设置支付方式
        sendUser.setPayWay(orders.getPayWay());
        //为钢铁侠设置发货人信息
        imIronMan.setSendUser(sendUser);
        //查询子订单下的所有商品
        List<OrdersItem> ordersItems = ordersMapper.findOrdersItemByOrdersSplitId(ordersSplit.getId());
        //商品合计金额初始化
        BigDecimal price = new BigDecimal("0");
        //设置商品详细信息
        for (OrdersItem ordersItem : ordersItems) {
            //商品订单总价初始化
            BigDecimal goodsPrice = new BigDecimal("0");
            switch (ordersSplit.getBelongType()) {
                //不同类型的店铺调用不同的mapper
                case 1:
                    StoreItem storeItem = storeItemMapper.findStoreItemById(ordersItem.getProductItemId());
                    StoreSpec storeSpec = storeSpecMapper.findStoreSpecByItemId(ordersItem.getProductItemId());
                    List<String> storeImgs = storeImgMapper.findStoreBannerImgUrlByItemId(ordersItem.getProductItemId());
                    //设置商品基本信息
                    ordersItem.setProductName(storeItem.getName());
                    ordersItem.setPrice(storeSpec.getPrice());
                    ordersItem.setUnit(storeSpec.getUnit());
                    goodsPrice = new BigDecimal(ordersItem.getQuantity().toString()).multiply(storeSpec.getPrice()).setScale(2, BigDecimal.ROUND_UP);
                    price = price.add(goodsPrice);
                    ordersItem.setGoodsPrice(goodsPrice);
                    if (storeImgs.size() > 0) {
                        ordersItem.setProductUrl(storeImgs.get(0));
                    }
                    break;
                case 2:
                    //通过商品id查询商品信息
                    FarmItem farmItem = farmItemMapper.findFarmItemById(ordersItem.getProductItemId());
                    //通过商品id查询商品规格信息
                    FarmSpec farmSpec = farmSpecMapper.findFarmSpecByItemId(ordersItem.getProductItemId());
                    //通过商品id查询商品图片信息
                    List<String> farmImgs = farmImgMapper.findFarmBannerImgUrlByItemId(ordersItem.getProductItemId());
                    //设置商品基本信息
                    ordersItem.setProductName(farmItem.getName());
                    ordersItem.setPrice(farmSpec.getPrice());
                    ordersItem.setUnit(farmSpec.getUnit());
                    goodsPrice = new BigDecimal(ordersItem.getQuantity().toString()).multiply(farmSpec.getPrice()).setScale(2, BigDecimal.ROUND_UP);
                    price = price.add(goodsPrice);
                    ordersItem.setGoodsPrice(goodsPrice);
                    if (farmImgs.size() > 0) {
                        ordersItem.setProductUrl(farmImgs.get(0));
                    }
                    break;
                case 3:
                    GrangeItem grangeItem = grangeItemMapper.findFarmItemById(ordersItem.getProductItemId());
                    GrangeSpec grangeSpec = grangeSpecMapper.findFarmSpecByItemId(ordersItem.getProductItemId());
                    List<String> grangeImgs = grangeImgMapper.findGrangeBannerImgUrl(ordersItem.getProductItemId());
                    //设置商品基本信息
                    ordersItem.setProductName(grangeItem.getName());
                    ordersItem.setPrice(grangeSpec.getPrice());
                    ordersItem.setUnit(grangeSpec.getUnit());
                    goodsPrice = new BigDecimal(ordersItem.getQuantity().toString()).multiply(grangeSpec.getPrice()).setScale(2, BigDecimal.ROUND_UP);
                    price = price.add(goodsPrice);
                    ordersItem.setGoodsPrice(goodsPrice);
                    if (grangeImgs.size() > 0) {
                        ordersItem.setProductUrl(grangeImgs.get(0));
                    }
                    break;
                case 4:
                    ShopItem shopItem = shopItemMapper.findShopItemById(ordersItem.getProductItemId());
                    ShopSpec shopSpec = shopSpecMapper.findShopSpecByItemId(ordersItem.getProductItemId());
                    List<String> shopImgs = shopImgMapper.findShopBannerImgUrlByItemId(ordersItem.getProductItemId());
                    //设置商品基本信息
                    ordersItem.setProductName(shopItem.getName());
                    ordersItem.setPrice(shopSpec.getPrice());
                    ordersItem.setUnit(shopSpec.getUnit());
                    goodsPrice = new BigDecimal(ordersItem.getQuantity().toString()).multiply(shopSpec.getPrice()).setScale(2, BigDecimal.ROUND_UP);
                    price = price.add(goodsPrice);
                    ordersItem.setGoodsPrice(goodsPrice);
                    if (shopImgs.size() > 0) {
                        ordersItem.setProductUrl(shopImgs.get(0));
                    }

                    break;
            }
            //添加商品信息
            imIronMan.getOrdersItems().add(ordersItem);
        }
        //初始化收货地址
        ShoppingAddress shoppingAddress = new ShoppingAddress();
        shoppingAddress.setAddress(orders.getAddress());
        shoppingAddress.setName(orders.getName());
        shoppingAddress.setPhone(orders.getPhone());
        shoppingAddress.setZipCode(orders.getZipCode());
        //为钢铁侠设置收货信息
        imIronMan.setShoppingAddress(shoppingAddress);
        //金额信息初始化
        TotalMoney totalMoney = new TotalMoney();
        //设置所有商品总金额
        totalMoney.setGoodsMoney(price);
        //设置运费
        BigDecimal freight = ordersSplit.getFreight();
        totalMoney.setFreight(freight);
        //设置积分抵扣金额
        BigDecimal usedScore = new BigDecimal(ordersSplit.getUsedSorce());   //使用的积分
        BigDecimal discountRate = new BigDecimal(ordersSplit.getDiscountRate()); //积分抵扣比例
        //计算积分抵扣金额
        BigDecimal scoreMoney = usedScore.divide(discountRate);
        //设置积分抵扣金额
        totalMoney.setScoreMoney(scoreMoney);
        //设置实际支付金额
        totalMoney.setRealMoney(price.add(freight).subtract(scoreMoney));
        //为钢铁侠设置金额信息
        imIronMan.setTotalMoney(totalMoney);
        return imIronMan;
    }

    /**
     * 修改子订单状态
     *
     * @throws Exception
     */
    @Override
    public void updateOrdersSplitState(List<Delivers> delivers) throws Exception {
        for (Delivers deliver : delivers) {
            //修改订单状态
            ordersMapper.updateOrdersSplitState(deliver.getSplitId(), 2);
            //添加订单快递方式和物流单号
            ordersMapper.updatecourierDataBySplitId(deliver.getSplitId(), deliver.getShopAddressResult());
            //构建消息对象
            Message message = new Message();
            //设置消息属性
            //id主键
            message.setId(GenerateUUID.create());
            //通过子订单id查询子订单信息
            OrdersSplit ordersSplit = ordersMapper.findOrdersSplitById(deliver.getSplitId());
            //通过总订单id查询总订单
            Orders orders = ordersMapper.findOrdersByOrderId(ordersSplit.getOrdersId());
            //设置消息体
            message.setContent("订单号为：" + orders.getOrdersNum() + " 的商品已经发货。");
            //设置消息对应的用户id
            message.setUserId(orders.getUserId());
            //设置消息创建时间
            message.setCreatedTime(new Date());
            //存入消息表
            messageMapper.sendMessage(message);
            //将子订单号存入redis 设置自动确认收货时间
            stringRedisTemplate.opsForValue().set(deliver.getSplitId() + "2", " ");
            stringRedisTemplate.expire(deliver.getSplitId() + "2", confirmReceiptTime, TimeUnit.SECONDS);
        }
    }


    /**
     * 通过子订单id查询收货信息
     *
     * @param ids
     * @return
     */
    @Override
    public SuperHero findShopAddressBySplitId(List<String> ids) throws Exception {
        //收货信息集合初始化
        List<ShopAddressResult> shopAddressResults = new ArrayList<>();
        for (String id : ids) {
            //通过子订单id查询子订单
            OrdersSplit ordersSplit = ordersMapper.findOrdersSplitById(id);
            //通过总订单id查询总订单
            Orders orders = ordersMapper.findOrdersByOrderId(ordersSplit.getOrdersId());
            //构建shopAddressResult对象
            ShopAddressResult shopAddressResult = new ShopAddressResult();
            //设置收货地址
            shopAddressResult.setAddress(orders.getAddress());
            //设置收货人姓名
            shopAddressResult.setName(orders.getName());
            //设置订单号
            shopAddressResult.setOrdersNum(orders.getOrdersNum());
            //设置收货人电话
            shopAddressResult.setPhone(orders.getPhone());
            //设置邮政编码
            shopAddressResult.setZipCode(orders.getZipCode());
            shopAddressResults.add(shopAddressResult);

        }
        //构建收货人信息  快递方式集合组合类
        SuperHero superHero = new SuperHero();
        superHero.setShopAddressResults(shopAddressResults);
        superHero.setCouriers(couriers);
        return superHero;
    }


}
