package com.rysh.module.clientSearch.service.impl;

import com.github.pagehelper.PageHelper;
import com.rysh.module.clientLoginRegister.beans.User;
import com.rysh.module.clientLoginRegister.mapper.LoginAndRegisterMapper;
import com.rysh.module.clientSearch.beans.*;
import com.rysh.module.clientSearch.mapper.SearchMapper;
import com.rysh.module.clientSearch.service.SearchService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.commonService.beans.ShopBean;
import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.City;
import com.rysh.module.community.beans.Community;
import com.rysh.module.community.mapper.CommunityMapper;
import com.rysh.module.farm.beans.FarmAndUser;
import com.rysh.module.farm.beans.FarmCategory;
import com.rysh.module.farm.beans.FarmItem;
import com.rysh.module.farm.beans.FarmSpec;
import com.rysh.module.farm.mapper.*;
import com.rysh.module.grange.beans.Grange;
import com.rysh.module.grange.beans.GrangeCategory;
import com.rysh.module.grange.beans.GrangeItem;
import com.rysh.module.grange.beans.GrangeSpec;
import com.rysh.module.grange.mapper.*;
import com.rysh.module.serverCompanyRanking.beans.CompanyRankF;
import com.rysh.module.serverCompanyRanking.mapper.CompanyRankingMapper;
import com.rysh.module.serverSystem.beans.Store;
import com.rysh.module.serverSystem.beans.StoreCommunity;
import com.rysh.module.serverSystem.mapper.StoreMapper;
import com.rysh.module.shop.beans.Shop;
import com.rysh.module.shop.beans.ShopCategory;
import com.rysh.module.shop.beans.ShopItem;
import com.rysh.module.shop.beans.ShopSpec;
import com.rysh.module.shop.mapper.ShopCategoryMapper;
import com.rysh.module.shop.mapper.ShopImgMapper;
import com.rysh.module.shop.mapper.ShopMapper;
import com.rysh.module.shop.mapper.ShopSpecMapper;
import com.rysh.module.store.beans.StoreCategory;
import com.rysh.module.store.beans.StoreItem;
import com.rysh.module.store.beans.StoreSpec;
import com.rysh.module.store.beans.StoreTag;
import com.rysh.module.store.mapper.*;
import com.rysh.module.utils.GetSalesScoreUtils;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    private StoreCategoryMapper storeCategoryMapper;

    @Autowired
    private StoreSpecMapper storeSpecMapper;

    @Autowired
    private StoreImgMapper storeImgMapper;

    @Autowired
    private StoreItemMapper storeItemMapper;

    @Autowired
    private StoreMapper2 storeMapper2;

    @Autowired
    private FarmCategoryMapper farmCategoryMapper;

    @Autowired
    private FarmSpecMapper farmSpecMapper;

    @Autowired
    private FarmImgMapper farmImgMapper;

    @Autowired
    private FarmItemMapper farmItemMapper;

    @Autowired
    private GrangeCategoryMapper grangeCategoryMapper;

    @Autowired
    private GrangeSpecMapper grangeSpecMapper;

    @Autowired
    private GrangeImgMapper grangeImgMapper;

    @Autowired
    private GrangeItemMapper grangeItemMapper;

    @Autowired
    private ShopSpecMapper shopSpecMapper;

    @Autowired
    private ShopCategoryMapper shopCategoryMapper;

    @Autowired
    private ShopImgMapper shopImgMapper;

    @Autowired
    private GetSalesScoreUtils getSalesScoreUtils;

    @Autowired
    private LoginAndRegisterMapper loginAndRegisterMapper;

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private AlbumMapper albumMapper;
    //自营商城id
    @Value("${result.tagId}")
    private String shopId;

    @Autowired
    private StoreMapper storeMapper;

    //社区便民分类
    @Value("${ConvenientCommunity}")
    private List<String> convenientCommunity;

    //自营商城发货地址
    @Value("${result.selfDefaultAddress}")
    private String selfDefaultAddress;

    @Value("${result.selfHeadUrl}")
    private String selfHeadUrl;


    @Autowired
    private CompanyRankingMapper companyRankingMapper;

    @Autowired
    private FarmMapper farmMapper;

    @Autowired
    private GrangeMapper grangeMapper;

    @Autowired
    private ShopMapper shopMapper;



    /**
     * 农场农庄搜索
     *
     * @param paramBean 分页参数  搜索条件
     * @param state     1评分正序  2评分倒序   3销量正序  4销量倒序
     * @return
     */
    @Override
    public List<ShopBean> freeSearch(ParamBean paramBean, String cityId, String areaId, String tagId, Integer state, Integer belongType) throws Exception {
        //设置动态表名
        String tableName = "";
        if (belongType == 2) {
            tableName = "farm";
        }
        if (belongType == 3) {
            tableName = "grange";
        }
        //设置分页参数
        PageHelper.startPage(paramBean.getPage(), paramBean.getSize());
        //通过条件搜索农场
        List<ShopBean> shopBeans = searchMapper.freeSearch(paramBean.getSearch(), cityId, areaId, state, tagId, tableName);
        //给农场添加评分和销量
        for (ShopBean shopBean : shopBeans) {
            if (shopBean.getMark() == 0) {
                //设置店铺评分
                BigDecimal marketScore = getSalesScoreUtils.getScoreByMarketId(shopBean.getId());
                shopBean.setMark(marketScore.doubleValue());
            }
            if (shopBean.getSales() == 0) {
                //设置店铺销量
                Integer sales = getSalesScoreUtils.getMarketSales(shopBean.getId());
                shopBean.setSales(sales);
            }
            if(belongType==2){
                //设置农场封面图
                String farmCover=albumMapper.findFarmCoverImg(shopBean.getId());
                if(farmCover!=null){
                    shopBean.setCover(farmCover);
                }
                //设置农场相册图
                List<String> farmImgs=albumMapper.findFarmImgNotCover(shopBean.getId());
//                farmImgs.add(farmCover);
                shopBean.setImages(farmImgs);
            }
            if(belongType==3){
                //设置农庄封面图
                String grangeCover=albumMapper.findGrangeCoverImg(shopBean.getId());
                if(grangeCover!=null){
                    shopBean.setCover(grangeCover);
                }
                //设置农庄相册图
                List<String> grangeImgs=albumMapper.findGrangeImgNotCover(shopBean.getId());
//                grangeImgs.add(grangeCover);
                shopBean.setImages(grangeImgs);
            }
        }
        return shopBeans;
    }


    /**店内商品搜索
     * @param marketId  店铺id
     * @param belongType   店铺类型 1商铺  2农场  3农庄
     * @return
     */
    @Override
    public SuperProductItem goodsFreeSearch(ParamBean paramBean,String marketId, Integer belongType,Integer state,String categoryId) throws Exception {
        //商品集合初始化
        List<ProductItem> productItems = new ArrayList<>();
        if(belongType==1){
            //分页参数
            PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
            //查询商铺商品信息
            List<StoreItem> storeItems=searchMapper.searchStoreItem(paramBean.getSearch(),marketId,state,categoryId);

            for (StoreItem storeItem : storeItems) {
                //商品对象初始化
                ProductItem productItem = new ProductItem();
                //通过商品id查询商品规格信息
//                StoreSpec storeSpec = storeSpecMapper.findStoreSpecByItemId(storeItem.getId());
                List<StoreSpec> storeSpecs = storeSpecMapper.findStoreSpecsByItemId(storeItem.getId());
                for (StoreSpec storeSpec : storeSpecs) {
                    //商品规格和单价集合
                    ProductSpec productSpec = new ProductSpec();
                    productSpec.setId(storeSpec.getId());
                    productSpec.setName(storeSpec.getUnit());
                    productSpec.setPrice(storeSpec.getPrice());
                    productItem.getUnits().add(productSpec);
                }
                //通过分类id查询分类信息
                StoreCategory storeCategory = storeCategoryMapper.findCategoryById(storeItem.getCategoryId());
                //通过商品id查询商品图片
                List<String> storeImgs = storeImgMapper.findStoreBannerImgUrlByItemId(storeItem.getId());
                //查询商品缩略图
                String storeCoverImg = storeMapper2.findStoreItemCover(storeItem.getId());
                if(storeCoverImg!=null){
                    productItem.setCoverUrl(storeCoverImg);
                }
                String storeDetailImgUrl = storeImgMapper.findStoreDetailImgUrl(storeItem.getId());
                if(storeImgs.size()>0){
                    //设置商品属性
                    //图片url
                    productItem.setUrls(storeImgs);
                    productItem.setDetailUrl(storeDetailImgUrl);
                }
                //分类id
                productItem.setCategoryId(storeCategory.getId());
                //分类名称
                productItem.setCategoryName(storeCategory.getName());
                //商品创建时间
                productItem.setCreatedTimeStr(sf.format(storeItem.getCreatedTime()));
                //商品描述
                productItem.setDescription(storeItem.getDescription());
                //查询商铺信息
                Store store = storeMapper.findStoreById(marketId);
                //发货地址
                productItem.setAddress(store.getAddress());
                //商品id
                productItem.setItemId(storeItem.getId());
                //商品名称
                productItem.setName(storeItem.getName());
                //商品销量
                productItem.setSales(getSalesScoreUtils.getGoodsSales(storeItem.getId()));
                productItems.add(productItem);

            }
        }
        if (belongType == 2) {
                //分页参数
                PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
                //查询农场商品信息
                List<FarmItem> farmItems=searchMapper.searchFarmItem(paramBean.getSearch(),marketId,state,categoryId);

                for (FarmItem farmItem : farmItems) {
                    //商品对象初始化
                    ProductItem productItem = new ProductItem();
                    //通过商品id查询商品规格信息
//                    FarmSpec farmSpec = farmSpecMapper.findFarmSpecByItemId(farmItem.getId());
                    List<FarmSpec> farmSpecs = farmSpecMapper.findFarmSpecsByItemId(farmItem.getId());
                    for (FarmSpec farmSpec : farmSpecs) {
                        //商品规格和单价集合
                        ProductSpec productSpec = new ProductSpec();
                        productSpec.setId(farmSpec.getId());
                        productSpec.setName(farmSpec.getUnit());
                        productSpec.setPrice(farmSpec.getPrice());
                        productItem.getUnits().add(productSpec);
                    }
                    //通过分类id查询分类信息
                    FarmCategory farmCategory = farmCategoryMapper.findCategoryById(farmItem.getCategoryId());
                    //通过商品id查询商品图片
                    List<String> farmImgs = farmImgMapper.findFarmBannerImgUrlByItemId(farmItem.getId());
                    String farmDetailImgUrl = farmImgMapper.findFarmDetailImgUrl(farmItem.getId());
                    if(farmImgs.size()>0){
                        //设置商品属性
                        //图片url
                        productItem.setCoverUrl(farmImgs.get(0));
                        productItem.setUrls(farmImgs);
                        productItem.setDetailUrl(farmDetailImgUrl);
                    }

                    //分类id
                    productItem.setCategoryId(farmCategory.getId());
                    //分类名称
                    productItem.setCategoryName(farmCategory.getName());
                    //商品创建时间
                    productItem.setCreatedTimeStr(sf.format(farmItem.getCreatedTime()));
                    //商品描述
                    productItem.setDescription(farmItem.getDescription());
                    //商品id
                    productItem.setItemId(farmItem.getId());
                    //商品名称
                    productItem.setName(farmItem.getName());
                    //商品销量
                    productItem.setSales(getSalesScoreUtils.getGoodsSales(farmItem.getId()));
                    productItems.add(productItem);
                }
            }

        if (belongType == 3) {
            //分页参数
            PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
            //查询农庄商品信息
            List<GrangeItem> grangeItems = searchMapper.searchGrangeItem(paramBean.getSearch(), marketId,state,categoryId);

            for (GrangeItem grangeItem : grangeItems) {
                    //商品对象初始化
                    ProductItem productItem = new ProductItem();
                    //通过商品id查询商品规格信息
 //                   GrangeSpec grangeSpec = grangeSpecMapper.findFarmSpecByItemId(grangeItem.getId());
                    List<GrangeSpec> grangeSpecs = grangeSpecMapper.findFarmSpecsByItemId(grangeItem.getId());
                for (GrangeSpec grangeSpec : grangeSpecs) {
                    //商品规格和单价集合
                    ProductSpec productSpec = new ProductSpec();
                    productSpec.setId(grangeSpec.getId());
                    productSpec.setName(grangeSpec.getUnit());
                    productSpec.setPrice(grangeSpec.getPrice());
                    productItem.getUnits().add(productSpec);
                }
                    //通过分类id查询分类信息
                    GrangeCategory grangeCategory = grangeCategoryMapper.findCategoryById(grangeItem.getCategoryId());
                    //通过商品id查询商品图片
                    List<String> grangeImgs = grangeImgMapper.findGrangeBannerImgUrl(grangeItem.getId());
                    String detailImgUrl = grangeImgMapper.findGrangeDetailImgUrl(grangeItem.getId());
                if(grangeImgs.size()>0){
                        //设置商品属性
                        //商品图片
                        productItem.setCoverUrl(grangeImgs.get(0));
                        productItem.setUrls(grangeImgs);
                        productItem.setDetailUrl(detailImgUrl);
                    }
                    //分类id
                    productItem.setCategoryId(grangeCategory.getId());
                    //分类名称
                    productItem.setCategoryName(grangeCategory.getName());
                    //商品创建时间
                    productItem.setCreatedTimeStr(sf.format(grangeItem.getCreatedTime()));
                    //商品描述
                    productItem.setDescription(grangeItem.getDescription());
                    //商品id
                    productItem.setItemId(grangeItem.getId());
                    //商品名称
                    productItem.setName(grangeItem.getName());
                    //商品销量
                    productItem.setSales(getSalesScoreUtils.getGoodsSales(grangeItem.getId()));
                    productItems.add(productItem);
                }

        }
        SuperProductItem superProductItem = new SuperProductItem();
        superProductItem.setCategories(findAllCategoryByMarketId(belongType,marketId));
        superProductItem.setProductItems(productItems);
        return superProductItem;
    }

    /**
     * 查詢商品詳情
     * @param goodsId  商品id
     * @param belongType  商品類型  2农场 3农庄
     * @return
     */
    @Override
    public ProductItem findGoodsDetailByGoodsId(String goodsId, Integer belongType) throws Exception {
            //初始化商品纖細對象
        ProductItem productItem=new ProductItem();
        if(belongType==1){
            //通过商品id查询商品详细信息
            StoreItem storeItem = storeItemMapper.findStoreItemById(goodsId);
            //通过分类id查询商品分类信息
            StoreCategory storeCategory = storeCategoryMapper.findCategoryById(goodsId);
            //通过商品id查询商品规格信息
            List<StoreSpec> storeSpecs = storeSpecMapper.findStoreSpecsByItemId(goodsId);
            for (StoreSpec storeSpec : storeSpecs) {
                //商品规格和单价集合
                ProductSpec productSpec = new ProductSpec();
                productSpec.setId(storeSpec.getId());
                productSpec.setName(storeSpec.getUnit());
                productSpec.setPrice(storeSpec.getPrice());
                productItem.getUnits().add(productSpec);
            }
            //通过商品id查询商品图片信息
            List<String> storeBannerImgUrls = storeImgMapper.findStoreBannerImgUrlByItemId(goodsId);
            String storeDetailImgUrl = storeImgMapper.findStoreDetailImgUrl(goodsId);
            //設置商品詳細信息
            //商品分類id
            productItem.setCategoryId(storeCategory.getId());
            //商品分類名稱
            productItem.setCategoryName(storeCategory.getName());
            //商品創建時間
            productItem.setCreatedTimeStr(sf.format(storeItem.getCreatedTime()));
            //商品描述
            productItem.setDescription(storeItem.getDescription());
            //商品詳情圖
            productItem.setDetailUrl(storeDetailImgUrl);
            //商品id
            productItem.setItemId(storeItem.getId());
            //商品名稱
            productItem.setName(storeItem.getName());
            //商品銷量
            productItem.setSales(getSalesScoreUtils.getGoodsSales(goodsId));
            //商品banner圖
            productItem.setUrls(storeBannerImgUrls);
        }
            if(belongType==2){
                //通過商品id查詢商品詳細信息
                FarmItem farmItem = farmItemMapper.findFarmItemById(goodsId);
                //通過分类id查詢商品分類信息
                FarmCategory farmCategory = farmCategoryMapper.findCategoryById(farmItem.getCategoryId());
                //通過商品id查詢商品規格信息
                List<FarmSpec> farmSpecs = farmSpecMapper.findFarmSpecsByItemId(goodsId);
                for (FarmSpec farmSpec : farmSpecs) {
                    //商品规格和单价集合
                    ProductSpec productSpec = new ProductSpec();
                    productSpec.setId(farmSpec.getId());
                    productSpec.setName(farmSpec.getUnit());
                    productSpec.setPrice(farmSpec.getPrice());
                    productItem.getUnits().add(productSpec);
                }
                //通過商品id查詢商品圖片信息
                List<String> farmBannerImgUrls = farmImgMapper.findFarmBannerImgUrlByItemId(goodsId);
                String farmDetailImgUrl = farmImgMapper.findFarmDetailImgUrl(goodsId);
                //設置商品詳細信息
                //商品分類id
                productItem.setCategoryId(farmCategory.getId());
                //商品分類名稱
                productItem.setCategoryName(farmCategory.getName());
                //商品創建時間
                productItem.setCreatedTimeStr(sf.format(farmItem.getCreatedTime()));
                //商品描述
                productItem.setDescription(farmItem.getDescription());
                //商品詳情圖
                productItem.setDetailUrl(farmDetailImgUrl);
                //商品id
                productItem.setItemId(farmItem.getId());
                //商品名稱
                productItem.setName(farmItem.getName());
                //商品銷量
                productItem.setSales(getSalesScoreUtils.getGoodsSales(goodsId));
                //商品banner圖
                productItem.setUrls(farmBannerImgUrls);
            }
            if(belongType==3){
                //通過商品id查詢商品詳細信息
                GrangeItem grangeItem = grangeItemMapper.findFarmItemById(goodsId);
                //通過分类id查詢商品分類信息
                GrangeCategory grangeCategory = grangeCategoryMapper.findCategoryById(grangeItem.getCategoryId());
                //通過商品id查詢商品規格信息
                List<GrangeSpec> grangeSpecs = grangeSpecMapper.findFarmSpecsByItemId(goodsId);
                for (GrangeSpec grangeSpec : grangeSpecs) {
                    //商品规格和单价集合
                    ProductSpec productSpec = new ProductSpec();
                    productSpec.setId(grangeSpec.getId());
                    productSpec.setName(grangeSpec.getUnit());
                    productSpec.setPrice(grangeSpec.getPrice());
                    productItem.getUnits().add(productSpec);
                }
                //通過商品id查詢商品圖片信息
                List<String> grangeBannerImgUrls = grangeImgMapper.findGrangeBannerImgUrl(goodsId);
                String grangeDetailImgUrl = grangeImgMapper.findGrangeDetailImgUrl(goodsId);
                //設置商品詳細信息
                //商品分類id
                productItem.setCategoryId(grangeCategory.getId());
                //商品分類名稱
                productItem.setCategoryName(grangeCategory.getName());
                //商品創建時間
                productItem.setCreatedTimeStr(sf.format(grangeItem.getCreatedTime()));
                //商品描述
                productItem.setDescription(grangeItem.getDescription());
                //商品詳情圖
                productItem.setDetailUrl(grangeDetailImgUrl);
                //商品id
                productItem.setItemId(grangeItem.getId());
                //商品名稱
                productItem.setName(grangeItem.getName());
                //商品銷量
                productItem.setSales(getSalesScoreUtils.getGoodsSales(goodsId));
                //商品banner圖
                productItem.setUrls(grangeBannerImgUrls);
            }
        return productItem;
    }


    /**
     * 查询自营商城商品
     * @param paramBean
     * @return
     */
    @Override
    public List<ProductItem> searchShopGoods(ParamBean paramBean,Integer state,String categoryId) throws Exception {
        //初始化商品对象集合
        List<ProductItem> productItems=new ArrayList<>();
        //查询自营商城商品
        List<ShopItem> shopItems=searchMapper.searchShopItem(paramBean,state,categoryId);
        //
        for (ShopItem shopItem : shopItems) {
            //初始化商品对象
            ProductItem productItem=new ProductItem();
            //通过商品id查询规格信息
            //ShopSpec shopSpec = shopSpecMapper.findShopSpecByItemId(shopItem.getId());
            List<ShopSpec> shopSpecs = shopSpecMapper.findShopSpecsByItemId(shopItem.getId());
            for (ShopSpec shopSpec : shopSpecs) {
                //商品规格和单价集合
                ProductSpec productSpec = new ProductSpec();
                productSpec.setId(shopSpec.getId());
                productSpec.setName(shopSpec.getUnit());
                productSpec.setPrice(shopSpec.getPrice());
                productItem.getUnits().add(productSpec);
            }
            //通过分类id查询分类信息
            ShopCategory shopCategory = shopCategoryMapper.findCategoryById(shopItem.getCategoryId());
            //通过商品id查询图片信息
            List<String> shopBannerImgUrls = shopImgMapper.findShopBannerImgUrlByItemId(shopItem.getId());
            if(shopBannerImgUrls.size()>0){
                //商品banner图片
                productItem.setCoverUrl(shopBannerImgUrls.get(0));
                //设置商品banner图
                productItem.setUrls(shopBannerImgUrls);
            }
            //设置商铺详情图
            String shopDetailImgUrl = shopImgMapper.findShopDetailImgUrl(shopItem.getId());
            if(shopDetailImgUrl!=null){
                productItem.setDetailUrl(shopDetailImgUrl);
            }
            //运费
            Shop shop = shopMapper.findById(shopId);
            productItem.setFreight(shop.getFreight());
            productItem.setBelongType(4);
            //商品销量
            productItem.setSales(getSalesScoreUtils.getGoodsSales(shopItem.getId()));
            //商品名称
            productItem.setName(shopItem.getName());
            //商品id
            productItem.setItemId(shopItem.getId());
            //商品描述
            productItem.setDescription(shopItem.getDescription());
            //商品创建时间
            productItem.setCreatedTimeStr(sf.format(shopItem.getCreatedTime()));
            //商品分类名称
            productItem.setCategoryName(shopCategory.getName());
            //分类id
            productItem.setCategoryId(shopCategory.getId());
            //发货地址
            productItem.setAddress(selfDefaultAddress);

            productItems.add(productItem);
        }


        return productItems;
    }


    /**
     * 查询周边商铺
     * @param uid  用户id
     * @param paramBean 分页参数  搜索条件
     * @param state 1评分正序  2评分倒序   3销量正序  4销量倒序
     * @return
     */
    @Override
    public List<ShopBean> searchStore(String uid, ParamBean paramBean, Integer state,String tagId) throws Exception {
        //商铺对象集合初始化
        List<ShopBean> ShopBeans=new ArrayList<>();
        //通过uid查询当前登陆用户信息
        User user = loginAndRegisterMapper.findNowUser(uid);
        //通过用户所绑定的小区id查询周边商铺
        List<Store> stores=searchMapper.searchStore(user.getCommunityId(),paramBean.getSearch(),state,tagId);
        //

        for (Store store : stores) {
            //创建商铺对象
            ShopBean shopBean = new ShopBean();
            //通过商铺绑定的小区id查询小区信息
            //给商品设置绑定的社区名称  区域id  城市id
            //查询商铺绑定的所有小区
            List<String> communityIds=storeMapper.findCommunityIdByStoreId(store.getId());
            for (String communityId : communityIds) {
                Community community = communityMapper.findCommunityById(communityId);
                //给商铺设置绑定的社区名称  区域id  城市id
                StoreCommunity storeCommunity = new StoreCommunity();
                storeCommunity.setCommunityId(community.getId());
                storeCommunity.setCommunityName(community.getName());
                //通过小区id查询区域
                Area area = storeMapper.findAreaByAreaId(community.getAreaId());
                storeCommunity.setAreaId(area.getId());
                //通过区域id查城市
                City city = storeMapper.findCityByCityId(area.getCityId());
                storeCommunity.setCityId(city.getId());
                store.getStoreCommunities().add(storeCommunity);
            }
            //设置商铺信息
            //商铺地址
            shopBean.setAddress(store.getAddress());
            //商铺创建时间
            shopBean.setCreatedTime(store.getCreatedTime());
            //商铺id
            shopBean.setId(store.getId());
            //商铺名称
            shopBean.setName(store.getName());
            //商铺联系电话
            shopBean.setContactNum(store.getTel());
            //商铺运费
            shopBean.setFreight(store.getFreight());
            //商铺评分
            shopBean.setMark(getSalesScoreUtils.getScoreByMarketId(store.getId()).doubleValue());
            //商铺销量
            shopBean.setSales(getSalesScoreUtils.getMarketSales(store.getId()));
            //商铺封面图
            String storeCover = storeMapper2.findAlbumCoverByMarketId(store.getId());
            if(storeCover!=null){
                shopBean.setCover(storeCover);
            }
            //商铺相册图
            List<String> storeImgs=storeMapper2.findAlbumNotCoverByMarketId(store.getId());
            if(storeImgs.size()>0){
                shopBean.setImages(storeImgs);
            }
            //商铺描述
            shopBean.setIntro(store.getIntro());
            ShopBeans.add(shopBean);
        }
        return ShopBeans;
    }


    /**
     * 查询自营商城banner图和分类
     * @return
     * @throws Exception
     */
    @Override
    public BannerAndCategory getNfsjBaseInfos() throws Exception {
        //查询所有自营商城banner图
        List<Banner> banners=searchMapper.findAllShopBanner();
        //查询所有自营商城分类
        List<Type> types=searchMapper.findAllShopCategory(shopId);

        BannerAndCategory bannerAndCategory = new BannerAndCategory();
        bannerAndCategory.setBanner(banners);
        bannerAndCategory.setTypeList(types);
        return bannerAndCategory;
    }


    /**
     * 查询社区便民分类
     * @return
     * @throws Exception
     */
    @Override
    public List<Type> getConvenientCommunityType() throws Exception {
        //添加社服小站
        List<Type> types=new ArrayList<>();
        types.add(searchMapper.findConvenientCommunityType());
        //添加微社区和周边商铺
        Integer a=0;
        for (String s : convenientCommunity) {
            Type type = new Type();
            //构建id
            a++;
            //设置id
            type.setId(a.toString());
            //设置分类名称
            type.setName(s);

            types.add(type);
        }



        return types;

    }

    @Override
    public List<StoreTag> findStoreTag(String uid) throws Exception {
        //查询userId查询用户绑定的小区id
        User user = loginAndRegisterMapper.findNowUser(uid);
        //通过小区id查询用户应该看到的 周边商铺的标签
        List<StoreTag> storeTags=searchMapper.findStoreTagByCommunityId(user.getCommunityId());
        return storeTags;
    }


    /**
     * 首页搜索
     * @param search
     * @param uid
     * @return
     */
    @Override
    public List<ProductItem> searchAllGoods(String search,String uid) {
        //通过uid查询用户绑定的小区id
        User user = loginAndRegisterMapper.findNowUser(uid);
        if(user!=null){
            List<ProductItem> productItems=searchMapper.searchAllGoods(search,user.getCommunityId());
            //添加商品其他信息
            for (ProductItem productItem : productItems) {
                //销量
                Integer goodsSales = getSalesScoreUtils.getGoodsSales(productItem.getItemId());
                productItem.setSales(goodsSales);
                //分类
                FarmCategory farmCategory = farmCategoryMapper.findCategoryById(productItem.getCategoryId());
                if(farmCategory!=null){
                    productItem.setCategoryId(farmCategory.getId());
                    productItem.setCategoryName(farmCategory.getName());
                    //查询店铺信息
                    FarmAndUser farmAndUser = farmMapper.findOneById(farmCategory.getFarmId());
                    if(farmAndUser!=null){
                        productItem.setBelongType(1);
                        productItem.setMarketId(farmAndUser.getId());
                        productItem.setMarketName(farmAndUser.getName());
                        productItem.setAddress(farmAndUser.getAddress());
                        productItem.setFreight(farmAndUser.getFreight());
                        //查询农场头像
                        String farmCoverImg = albumMapper.findFarmCoverImg(farmAndUser.getId());
                        if(farmCoverImg!=null){
                            productItem.setMarketCover(farmCoverImg);
                        }
                    }
                }else {
                    GrangeCategory grangeCategory = grangeCategoryMapper.findCategoryById(productItem.getCategoryId());
                    if(grangeCategory!=null){
                        productItem.setCategoryId(grangeCategory.getId());
                        productItem.setCategoryName(grangeCategory.getName());
                        //查询店铺信息
                        Grange grange = grangeMapper.findById(grangeCategory.getGrangeId());
                        if(grange!=null){
                            productItem.setBelongType(2);
                            productItem.setMarketId(grange.getId());
                            productItem.setMarketName(grange.getName());
                            productItem.setAddress(grange.getAddress());
                            productItem.setFreight(grange.getFreight());
                            //销量
                            Integer marketSales = getSalesScoreUtils.getMarketSales(grange.getId());
                            productItem.setSales(marketSales);
                            //查询农庄头像
                            String grangeCoverImg = albumMapper.findGrangeCoverImg(grange.getId());
                            if(grangeCoverImg!=null){
                                productItem.setMarketCover(grangeCoverImg);
                            }
                        }
                    }else {
                        StoreCategory storeCategory = storeCategoryMapper.findCategoryById(productItem.getCategoryId());
                        if(storeCategory!=null){
                            productItem.setCategoryId(storeCategory.getId());
                            productItem.setCategoryName(storeCategory.getName());
                            //查询店铺信息
                            Store store = storeMapper.findStoreById(storeCategory.getStoreId());
                            if(store!=null){
                                productItem.setMarketId(store.getId());
                                productItem.setMarketName(store.getName());
                                productItem.setAddress(store.getAddress());
                                productItem.setFreight(store.getFreight());
                                //查询商铺头像
                                String storeCoverImg = storeMapper2.findAlbumCoverByMarketId(store.getId());
                                if(storeCoverImg!=null){
                                    productItem.setMarketCover(storeCoverImg);
                                }
                            }
                        }else {
                            ShopCategory shopCategory = shopCategoryMapper.findCategoryById(productItem.getCategoryId());
                            if(shopCategory!=null){
                                productItem.setCategoryId(shopCategory.getId());
                                productItem.setCategoryName(shopCategory.getName());
                                //查询店铺信息
                                Shop shop = shopMapper.findById(shopId);
                                if(shop!=null){
                                    productItem.setMarketId(shopId);
                                    productItem.setMarketName(shop.getName());
                                    productItem.setAddress(selfDefaultAddress);
                                    productItem.setFreight(shop.getFreight());
                                    //设置自营商城头像
                                    productItem.setMarketCover(selfHeadUrl);
                                }
                            }
                        }
                    }

                }

                //规格
                List<FarmSpec> farmSpecs = farmSpecMapper.findFarmSpecsByItemId(productItem.getItemId());

                if(farmSpecs!=null&&farmSpecs.size()>0){
                    for (FarmSpec farmSpec : farmSpecs) {
                        ProductSpec productSpec = new ProductSpec();
                        productSpec.setId(farmSpec.getId());
                        productSpec.setName(farmSpec.getUnit());
                        productSpec.setPrice(farmSpec.getPrice());
                        productItem.getUnits().add(productSpec);
                    }
                }else {
                    List<GrangeSpec> grangeSpecs = grangeSpecMapper.findFarmSpecsByItemId(productItem.getItemId());
                    if(grangeSpecs!=null&&grangeSpecs.size()>0){
                        for (GrangeSpec grangeSpec : grangeSpecs) {
                            ProductSpec productSpec = new ProductSpec();
                            productSpec.setId(grangeSpec.getId());
                            productSpec.setName(grangeSpec.getUnit());
                            productSpec.setPrice(grangeSpec.getPrice());
                            productItem.getUnits().add(productSpec);
                        }
                    }else {
                        List<StoreSpec> storeSpecs = storeSpecMapper.findStoreSpecsByItemId(productItem.getItemId());
                        if(storeSpecs!=null&&storeSpecs.size()>0){
                            for (StoreSpec storeSpec : storeSpecs) {
                                ProductSpec productSpec = new ProductSpec();
                                productSpec.setId(storeSpec.getId());
                                productSpec.setName(storeSpec.getUnit());
                                productSpec.setPrice(storeSpec.getPrice());
                                productItem.getUnits().add(productSpec);
                            }
                        }else {
                            List<ShopSpec> shopSpecs = shopSpecMapper.findShopSpecsByItemId(productItem.getItemId());
                            if(shopSpecs!=null&&shopSpecs.size()>0){
                                for (ShopSpec shopSpec : shopSpecs) {
                                    ProductSpec productSpec = new ProductSpec();
                                    productSpec.setId(shopSpec.getId());
                                    productSpec.setName(shopSpec.getUnit());
                                    productSpec.setPrice(shopSpec.getPrice());
                                    productItem.getUnits().add(productSpec);
                                }
                            }
                        }
                    }
                }
                //商品缩略图 详情图 banner图
                List<String> farmImgUrls = farmImgMapper.findFarmBannerImgUrlByItemId(productItem.getItemId());
                if(farmImgUrls!=null&&farmImgUrls.size()>0){
                    productItem.setCoverUrl(farmImgUrls.get(0));
                    productItem.setUrls(farmImgUrls);
                    String farmDetailImgUrl = farmImgMapper.findFarmDetailImgUrl(productItem.getItemId());
                    if(farmDetailImgUrl!=null){
                        productItem.setDetailUrl(farmDetailImgUrl);
                    }
                }else {
                    List<String> grangeImgUrls = grangeImgMapper.findGrangeBannerImgUrl(productItem.getItemId());
                    if(grangeImgUrls!=null&&grangeImgUrls.size()>0){
                        productItem.setCoverUrl(grangeImgUrls.get(0));
                        productItem.setUrls(grangeImgUrls);
                        String grangeDetailImgUrl = grangeImgMapper.findGrangeDetailImgUrl(productItem.getItemId());
                        if(grangeDetailImgUrl!=null){
                            productItem.setDetailUrl(grangeDetailImgUrl);
                        }
                    }else {
                        List<String> storeImgUrls = storeImgMapper.findStoreBannerImgUrlByItemId(productItem.getItemId());
                        if(storeImgUrls!=null&&storeImgUrls.size()>0){
                            productItem.setCoverUrl(storeImgUrls.get(0));
                            productItem.setUrls(storeImgUrls);
                            String storeDetailImgUrl = storeImgMapper.findStoreDetailImgUrl(productItem.getItemId());
                            if(storeDetailImgUrl!=null){
                                productItem.setDetailUrl(storeDetailImgUrl);
                            }
                        }else {
                            List<String> shopImgUrls = shopImgMapper.findShopBannerImgUrlByItemId(productItem.getItemId());
                            if(shopImgUrls!=null&&shopImgUrls.size()>0){
                                productItem.setCoverUrl(shopImgUrls.get(0));
                                productItem.setUrls(shopImgUrls);
                                String shopDetailImgUrl = shopImgMapper.findShopDetailImgUrl(productItem.getItemId());
                                if(shopDetailImgUrl!=null){
                                    productItem.setDetailUrl(shopDetailImgUrl);
                                }
                            }
                        }
                    }
                }
            }
           return productItems;
        }else {
            return null;
        }
    }


    /**
     * 查询公益之星
     * @return
     */
    @Override
    public CompanyRankF findCompanyRanking() {
        //查询最新的公益之星排行榜
        CompanyRankF companyRankF=companyRankingMapper.findNewestRanking();
        companyRankF.setCompanyRankSs(companyRankingMapper.findCompanyRankingItemByrankingCompanyId(companyRankF.getId()));
        return companyRankF;
    }


    /**
     * 查询店铺所有商品分类
     * @param bylongType
     * @param marketId
     * @return
     */
    private List<Category> findAllCategoryByMarketId(Integer bylongType,String marketId){
        //初始化商品集合
        List<Category> categories=new ArrayList<>();

        if(bylongType==1){
            //通过商铺id查询商铺分类
            //List<StoreCategory> storeCategories=storeCategoryMapper.findAllAvailableCategory(marketId);
            List<StoreCategory> storeCategories=storeCategoryMapper.findStoreCategory(marketId);
            for (StoreCategory storeCategory : storeCategories) {
                //构建分类对象
                Category category = new Category();
                //设置分类id
                category.setId(storeCategory.getId());
                //设置分类名称
                category.setCategoryName(storeCategory.getName());

                categories.add(category);
            }
        }
        if(bylongType==2){
            //通过农场id查询农场分类
            //List<FarmCategory> farmCategories = farmCategoryMapper.findAllAvailableCategory(marketId);
            List<FarmCategory> farmCategories = farmCategoryMapper.findFarmCategory(marketId);
            for (FarmCategory farmCategory : farmCategories) {
                //构建分类对象
                Category category = new Category();
                //设置分类id
                category.setId(farmCategory.getId());
                //设置分类名称
                category.setCategoryName(farmCategory.getName());

                categories.add(category);
            }
        }
        if(bylongType==3){
            //通过农庄id查询农庄分类
            //List<GrangeCategory> grangeCategories = grangeCategoryMapper.findAllAvailableCategory(marketId);
            List<GrangeCategory> grangeCategories = grangeCategoryMapper.findGrangeCategory(marketId);
            for (GrangeCategory grangeCategory : grangeCategories) {
                //构建分类对象
                Category category = new Category();
                //设置分类id
                category.setId(grangeCategory.getId());
                //设置分类名称
                category.setCategoryName(grangeCategory.getName());

                categories.add(category);
            }
        }
        if(bylongType==4){
            //通过自营id查询自营商城分类
            //List<ShopCategory> shopCategories = shopCategoryMapper.findAllAvailableCategory(marketId);
            List<ShopCategory> shopCategories = shopCategoryMapper.findShopCategory(marketId);
            for (ShopCategory shopCategory : shopCategories) {
                //构建分类对象
                Category category = new Category();
                //设置分类id
                category.setId(shopCategory.getId());
                //设置分类名称
                category.setCategoryName(shopCategory.getName());

                categories.add(category);
            }
        }
        return categories;
    }
}
