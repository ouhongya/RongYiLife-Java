package com.rysh.module.clientShoppingCart.service.impl;

import com.rysh.module.clientShoppingCart.beans.ShoppingCart;
import com.rysh.module.clientShoppingCart.mapper.ShoppingCartMapper;
import com.rysh.module.clientShoppingCart.service.ShoppingCartService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.*;
import com.rysh.module.farm.mapper.*;
import com.rysh.module.grange.beans.Grange;
import com.rysh.module.grange.beans.GrangeCategory;
import com.rysh.module.grange.beans.GrangeItem;
import com.rysh.module.grange.beans.GrangeSpec;
import com.rysh.module.grange.mapper.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private StoreMapper2 storeMapper2;

    @Autowired
    private StoreItemMapper storeItemMapper;

    @Autowired
    private StoreSpecMapper storeSpecMapper;

    @Autowired
    private StoreImgMapper storeImgMapper;

    @Autowired
    private StoreCategoryMapper storeCategoryMapper;

    @Autowired
    private FarmMapper farmMapper;

    @Autowired
    private FarmItemMapper farmItemMapper;

    @Autowired
    private FarmSpecMapper farmSpecMapper;

    @Autowired
    private FarmImgMapper farmImgMapper;

    @Autowired
    private FarmCategoryMapper farmCategoryMapper;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private GrangeMapper grangeMapper;

    @Autowired
    private GrangeItemMapper grangeItemMapper;

    @Autowired
    private GrangeSpecMapper grangeSpecMapper;

    @Autowired
    private GrangeImgMapper grangeImgMapper;

    @Autowired
    private GrangeCategoryMapper grangeCategoryMapper;

    @Autowired
    private ShopImgMapper shopImgMapper;

    @Autowired
    private ShopSpecMapper shopSpecMapper;

    @Autowired
    private ShopItemMapper shopItemMapper;

    @Autowired
    private ShopCategoryMapper shopCategoryMapper;

    @Autowired
    private ShopMapper shopMapper;

    //自营商城id
    @Value("${result.tagId}")
    private String shopId;


    /**
     * 添加购物车
     * @param shoppingCart  购物车bean
     * @throws Exception
     */
    public String addShoppingCart(ShoppingCart shoppingCart) throws Exception {
        //判断商品在购物车中是否存在
        List<ShoppingCart> shoppingCarts=shoppingCartMapper.findShoppingCartByUserId(shoppingCart.getUserId());
        for (ShoppingCart cart : shoppingCarts) {
            //必须用户id  商品规格id与数据库的相等
            if(shoppingCart.getUserId().equals(cart.getUserId())&&shoppingCart.getUnitId().equals(cart.getUnitId())){
                //查询商品
                //商品重复  添加商品数量
                cart.setGoodsNum(shoppingCart.getGoodsNum()+cart.getGoodsNum());
                shoppingCartMapper.updateShoppcart(cart);
                return cart.getId();
            }
        }
        //购物车中没有这件商品  添加
        shoppingCart.setId(UUID.randomUUID().toString().replace("-","").toUpperCase());
        shoppingCart.setCreatedTime(new Date());
        //设置该商品的店铺id
        switch (shoppingCart.getIsInsider()){
            case 1:
                StoreSpec storeSpec = storeSpecMapper.findById(shoppingCart.getUnitId());
                StoreItem storeItem = storeItemMapper.findStoreItemById(storeSpec.getItemId());
                StoreCategory storeCategory = storeCategoryMapper.findCategoryById(storeItem.getCategoryId());
                shoppingCart.setMarketId(storeCategory.getStoreId());
                break;
            case 2:
                //通过商品id查询商品基本信息
                FarmSpec farmSpec = farmSpecMapper.findById(shoppingCart.getUnitId());
                FarmItem farm = farmItemMapper.findFarmItemById(farmSpec.getItemId());
                //通过商品基本信息中的分类id查询店铺id
                FarmCategory farmCategory = farmCategoryMapper.findCategoryById(farm.getCategoryId());
                //为购物车设置店铺id
                shoppingCart.setMarketId(farmCategory.getFarmId());
                break;
            case 3:
                GrangeSpec grangeSpec = grangeSpecMapper.findById(shoppingCart.getUnitId());
                GrangeItem grangeItem = grangeItemMapper.findFarmItemById(grangeSpec.getItemId());
                GrangeCategory grangeCategory = grangeCategoryMapper.findCategoryById(grangeItem.getCategoryId());
                shoppingCart.setMarketId(grangeCategory.getGrangeId());
                break;
            case 4:
                shoppingCart.setMarketId(shopId);
                break;
        }
        shoppingCartMapper.addShoppingCart(shoppingCart);
        return shoppingCart.getId();
    }

    /**
     * 清空购物车
     * @param userId  用户id
     * @throws Exception
     */
    public void emptyShoppingCartByUserId(String userId) throws Exception {
        shoppingCartMapper.emptyShoppingCartByUserId(userId);
    }

    /**
     * 删除购物车中的单个商品
     * @param id   购物车id
     * @throws Exception
     */
    public Boolean deleteShoppingCartById(String id,String uid) throws Exception {
        ShoppingCart shoppingCart=shoppingCartMapper.findShoppingCartById(id);
        //校验身份
        if(shoppingCart.getUserId().equals(uid)){
            //删除单个购物车商品
            shoppingCartMapper.deleteShoppingCartById(id);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 查询所有购物车
     * @return
     */
    public List<ShoppingCart> findAllShoppingCart(String userId, ParamBean paramBean) throws Exception {
        //通过用户id查询用户购物车
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.findShoppingCartByUserId(userId);

        for (ShoppingCart shoppingCart : shoppingCarts) {
            //根据isInsider查询店铺信息和商品信息
            switch (shoppingCart.getIsInsider()){
                case 1:
                    //查询周边商铺信息
                    Store store = storeMapper.findStoreById(shoppingCart.getMarketId());
                    if(store!=null){
                        //设置商铺名称
                        shoppingCart.setMarketName(store.getName());
                        //查询商铺封面图
                        String storeAlbumCoverImgUrl = storeMapper2.findAlbumCoverByMarketId(store.getId());
                        shoppingCart.setMarketUrl(storeAlbumCoverImgUrl);
                        //设置运费
                        shoppingCart.setFreight(store.getFreight());
                    }
                    //查询商铺商品规格信息
                    StoreSpec storeSpec = storeSpecMapper.findById(shoppingCart.getUnitId());
                    if(storeSpec!=null){
                        //设置商铺商品单价
                        shoppingCart.setGoodsPrice(storeSpec.getPrice());
                        //设置商铺商品总价
                        shoppingCart.setTotalPrice(storeSpec.getPrice().multiply(new BigDecimal(shoppingCart.getGoodsNum().toString())));
                        //设置商铺商品规格
                        shoppingCart.setGoodsUnit(storeSpec.getUnit());
                    }
                    //查询商铺商品信息
                    StoreItem storeItem = storeItemMapper.findStoreItemByIdPlus(storeSpec.getItemId());
                    if(storeItem!=null){
                        //设置商铺商品名称
                        shoppingCart.setGoodsName(storeItem.getName());
                    }
                    //查询商铺商品图片信息
                    List<String> storeImgs = storeImgMapper.findStoreBannerImgUrlByItemId(storeSpec.getItemId());
                    if(storeImgs.size()>0){
                        //设置商品图片
                        shoppingCart.setGoodsImgUrl(storeImgs.get(0));
                    }
                    break;
                case 2:
                    //查询农场信息
                    FarmAndUser farm = farmMapper.findOneById(shoppingCart.getMarketId());
                    if(farm!=null){
                        //设置农场名称
                        shoppingCart.setMarketName(farm.getName());
                        //查询农场封面图
                        String farmCoverImg = albumMapper.findFarmCoverImg(farm.getId());
                        shoppingCart.setMarketUrl(farmCoverImg);
                        //设置运费
                        shoppingCart.setFreight(farm.getFreight());
                    }

                    //查询农场商品规格信息
                    FarmSpec farmSpec = farmSpecMapper.findById(shoppingCart.getUnitId());
                    if(farmSpec!=null){
                        //设置农场商品单价
                        shoppingCart.setGoodsPrice(farmSpec.getPrice());
                        //设置商铺商品总价
                        shoppingCart.setTotalPrice(farmSpec.getPrice().multiply(new BigDecimal(shoppingCart.getGoodsNum().toString())));
                        //设置农场商品规格
                        shoppingCart.setGoodsUnit(farmSpec.getUnit());
                    }
                    //查询农场商品信息
                    FarmItem farmItem = farmItemMapper.findFarmItemByIdPlus(farmSpec.getItemId());
                    if(farmItem!=null){
                        //设置商品名称
                        shoppingCart.setGoodsName(farmItem.getName());
                    }
                    //查询农场图片信息
                    List<String> farmImgs = farmImgMapper.findFarmBannerImgUrlByItemId(farmSpec.getItemId());
                    if(farmImgs.size()>0){
                        //设置农场商品图片
                        shoppingCart.setGoodsImgUrl(farmImgs.get(0));
                    }
                    break;
                case 3:
                    //查询农庄信息
                    Grange grange = grangeMapper.findById(shoppingCart.getMarketId());
                    if(grange!=null){
                        //设置农庄名称
                        shoppingCart.setMarketName(grange.getName());
                        //查询农庄封面图
                        String grangeCoverImg = albumMapper.findGrangeCoverImg(grange.getId());
                        shoppingCart.setMarketUrl(grangeCoverImg);
                        //设置运费
                        shoppingCart.setFreight(grange.getFreight());
                    }
                    //查询农庄商品规格信息
                    GrangeSpec grangeSpec = grangeSpecMapper.findById(shoppingCart.getUnitId());
                    if(grangeSpec!=null){
                        //设置农庄商品单价
                        shoppingCart.setGoodsPrice(grangeSpec.getPrice());
                        //设置商铺商品总价
                        shoppingCart.setTotalPrice(grangeSpec.getPrice().multiply(new BigDecimal(shoppingCart.getGoodsNum().toString())));
                        //设置农庄商品规格
                        shoppingCart.setGoodsUnit(grangeSpec.getUnit());
                    }
                    //查询农庄商品信息
                    GrangeItem grangeItem = grangeItemMapper.findGrangeItemByIdPlus(grangeSpec.getItemId());
                    if(grangeItem!=null){
                        //设置农庄商品名称
                        shoppingCart.setGoodsName(grangeItem.getName());
                    }
                    //查询农庄图片信息
                    List<String> grangeImgs = grangeImgMapper.findGrangeBannerImgUrl(grangeSpec.getItemId());
                    if(grangeImgs.size()>0){
                        //设置农庄商品图片
                        shoppingCart.setGoodsImgUrl(grangeImgs.get(0));
                    }
                    break;
                case 4:
                    Shop shop = shopMapper.findById(shopId);
                    //查询自营商城规格信息
                    ShopSpec shopSpec = shopSpecMapper.findById(shoppingCart.getUnitId());
                    if(shopSpec!=null){
                        //设置自营商城商品单价
                        shoppingCart.setGoodsPrice(shopSpec.getPrice());
                        //设置商铺商品总价
                        shoppingCart.setTotalPrice(shopSpec.getPrice().multiply(new BigDecimal(shoppingCart.getGoodsNum().toString())));
                        //设置自营商城商品规格
                        shoppingCart.setGoodsUnit(shopSpec.getUnit());
                        //设置运费
                        shoppingCart.setFreight(shop.getFreight());
                    }
                    //查询自营商城商品信息
                    ShopItem shopItem = shopItemMapper.findShopItemByIdPlus(shopSpec.getItemId());
                    if(shopItem!=null){
                        //设置商品名称
                        shoppingCart.setGoodsName(shopItem.getName());

                        ShopCategory shopCategory = shopCategoryMapper.findCategoryById(shopItem.getCategoryId());
                        //设置自营商城名称
                        shoppingCart.setMarketName(shopCategory.getName());
                    }
                    //查询自营商城图片信息
                    List<String> shopImgs = shopImgMapper.findShopBannerImgUrlByItemId(shopSpec.getItemId());
                    if(shopImgs.size()>0){
                        //设置自营商城商品图片
                        shoppingCart.setGoodsImgUrl(shopImgs.get(0));
                    }
                    break;
            }
        }
        for (int i = 0; i < shoppingCarts.size(); i++) {
            if(shoppingCarts.get(i).getGoodsName()==null){
                shoppingCarts.remove(i);
                i--;
            }
        }
       return shoppingCarts;
    }
}
