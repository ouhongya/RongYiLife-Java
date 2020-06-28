package com.rysh.module.farm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.commonService.service.ImageUploadService;
import com.rysh.module.farm.beans.*;
import com.rysh.module.farm.mapper.AlbumMapper;
import com.rysh.module.farm.mapper.FarmMapper;
import com.rysh.module.farm.mapper.TagMapper;
import com.rysh.module.farm.service.FarmService;
import com.rysh.module.serverSystem.beans.Owner;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.serverSystem.mapper.StoreMapper;
import com.rysh.module.utils.GenerateUUID;
import com.rysh.module.utils.ToNullUtil;
import com.rysh.system.UserType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Log4j2
@Service
public class FarmServiceImpl implements FarmService {
    @Autowired
    private FarmMapper farmMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private ImageUploadService imageUploadService;

    /*
     * 添加农场
     * @param farm
     * @return int
     * @author HsiangSun
     * @date 2019/9/3 11:11
     */
    public int addFarm(Farm farm){
        farm.setCreatedTime(new Date());
        farm.setScore(0);
        farm.setStatus(0);//新添加农场但是没有发布到客户端
        return farmMapper.addFarm(farm);
    }



    /*
     * 查询所有的农场
     * @param
     * @return java.util.List<com.rysh.module.farm.beans.Farm>
     * @author HsiangSun
     * @date 2019/9/3 11:11
     */
    public PageInfo<FarmAndUser> getAllFarm(ParamBean param) {
        PageHelper.startPage(param.getPage(),param.getSize());
        String orderBy=param.getSortByFiled() + " "+param.getSortByOrder();
        PageHelper.orderBy(orderBy);
        List<FarmAndUser> all = farmMapper.findAll();
        for (FarmAndUser farmAndUser : all) {
            String id = farmAndUser.getId();
            List<User> userList = accountMapper.findUserByStoreId(id);
            for (User user : userList) {
                Owner owner = new Owner();
                String trueName = user.getTrueName();
                String username = user.getUsername();
                owner.setOwnerUsername(username);
                owner.setOwnerName(trueName);
                farmAndUser.getOwners().add(owner);
            }
        }
        return new PageInfo<>(all);
    }

    /*
     * 根据Id 查询农场
     * @param
     * @return java.util.List<com.rysh.module.farm.beans.Farm>
     * @author HsiangSun
     * @date 2019/9/5 20:38
     */
    @Transactional
    public FarmAndUser farmById(String id) {
        //查询账户名
        FarmAndUser farmAndUser = farmMapper.findOneById(id);
        List<User> userList = accountMapper.findUserByStoreId(id);
        for (User user : userList) {
            Owner owner = new Owner();
            String trueName = user.getTrueName();
            String username = user.getUsername();
            owner.setOwnerUsername(username);
            owner.setOwnerName(trueName);
            farmAndUser.getOwners().add(owner);
        }
        return farmAndUser;
    }

   /*
    * 更新农场信息
    * @param farm
    * @return int
    * @author Hsiang Sun
    * @date 2019/9/6 10:49
    */
    public int updateFarm(Farm farm) {
        farm.setLastedUpdateTime(new Timestamp(System.currentTimeMillis()));
        return farmMapper.updateById(farm);
    }

    /*
     * 删除农场
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/18 15:07
     */
    public int deletFarm(String id) {
        //断开农场与用户的绑定
        storeMapper.updateCompany(id);
        return farmMapper.updateStateById(id);
    }

  /**
   * 农场搜索
   * @param param
   * @return com.github.pagehelper.PageInfo<com.rysh.module.farm.beans.FarmAndUser>
   * @author Hsiang Sun
   * @date 2019/9/19 15:35
   */
    @Transactional
    public PageInfo<FarmAndUser> search(ParamBean param) {
        PageHelper.startPage(param.getPage(),param.getSize());
        String orderBy=param.getSortByFiled() + " "+param.getSortByOrder();
        PageHelper.orderBy(orderBy);
        List<FarmAndUser> farmAndUserList = farmMapper.findByCondition(param.getSearch());
        for (FarmAndUser farmAndUser : farmAndUserList) {
            String id = farmAndUser.getId();
            List<User> userList = accountMapper.findUserByStoreId(id);
            for (User user : userList) {
                Owner owner = new Owner();
                String trueName = user.getTrueName();
                String username = user.getUsername();
                owner.setOwnerUsername(username);
                owner.setOwnerName(trueName);
                farmAndUser.getOwners().add(owner);
            }
        }
        return new PageInfo<>(farmAndUserList);
    }

    /**
     * 查询当前登录用户的农场基本信息
     * @param login
     * @return com.rysh.module.farm.beans.FarmInfo
     * @author Hsiang Sun
     * @date 2019/9/25 15:18
     */
    @Transactional
    public DisplayInfo farmInfo(String login,String itemId) {
        //DisplayInfo displayInfo = farmMapper.findDisplayInfo(login);
        DisplayInfo info = new DisplayInfo();
        //查询用户基本信息
        User loginUser = accountMapper.findUserById(login);
        String s  = ToNullUtil.toNull(itemId);
        Farm farm = farmMapper.findFarmByLogin(login,s);
        List<Tag> tags = tagMapper.findTagByFarmId(farm.getId());
        List<User> users = accountMapper.findUserByStoreId(farm.getId());
        info.setId(farm.getId());
        info.setAccount(users);
        info.setAddress(farm.getAddress());
        info.setName(farm.getName());
        info.setScore(Long.valueOf(farm.getScore()));
        info.setTrueName(loginUser.getTrueName());
        info.setIntro(farm.getIntro());
        info.setContactNum(farm.getContactNum());
        info.setTags(tags);
        info.setFreight(farm.getFreight());
        info.setStatus(farm.getStatus());
        return info;
    }

    /**
     * 根据当前登录用户查询他所有的 农场
     * @param login
     * @return com.rysh.module.farm.beans.Farm
     * @author Hsiang Sun
     * @date 2019/9/25 16:40
     */
    public Farm getFarmByLogin(String login){
        return farmMapper.findFarmByLogin(login,null);
    }

    @Override
    public void updateFarmInfo(FarmAndUser farm) {
        String entityId = farm.getId();
        //先删除已经存在的标签
        tagMapper.deleteTagEntityByEntityId(entityId);
        List<Tag> tags = farm.getTags();
        for (Tag tag : tags) {
            String tagId = tag.getId();
            TagEntity tagEntity = new TagEntity();
            tagEntity.setBelongType(2);
            tagEntity.setCreatedTime(new Date());
            tagEntity.setEntityId(entityId);
            tagEntity.setId(GenerateUUID.create());
            tagEntity.setTagId(tagId);
            //添加新的tag entity
            tagMapper.addTagEntity(tagEntity);
        }
        farmMapper.updateFarmInfo(farm);
    }

    @Override
    public void addFarmAlbum(List<FarmAlbum> farmAlbums,String itemId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = accountMapper.findUserByUsername(login);

        if (user.getIsInsider() == UserType.农场账户.typeCode()){
            String companyId = user.getCompanyId();
            //查询当前用户相册信息
            List<FarmAlbum> albums = albumMapper.findFarmAlbumById(companyId);
            if (albums.size() > 9){
                log.error("当前用户["+user.getTrueName()+"]相册已满");
                return;
            }

            for (FarmAlbum farmAlbum : farmAlbums) {
                String url = farmAlbum.getUrl();
                int isCover = farmAlbum.getIsCover();

                FarmAlbum album = new FarmAlbum();
                album.setId(GenerateUUID.create());
                album.setUrl(url);
                album.setIsCover(isCover);
                album.setFarmId(companyId);
                album.setCreatedTime(new Date());
                album.setStatus(1);
                albumMapper.addFarmAlbum(album);
            }
        }else if (user.getIsInsider() == UserType.内部账户.typeCode()){
            //查询当前用户相册信息
            List<FarmAlbum> albums = albumMapper.findFarmAlbumById(itemId);
            if (albums.size() > 9){
                log.error("当前用户["+user.getTrueName()+"]相册已满");
                return;
            }
            for (FarmAlbum farmAlbum : farmAlbums) {
                String url = farmAlbum.getUrl();
                int isCover = farmAlbum.getIsCover();

                FarmAlbum album = new FarmAlbum();
                album.setId(GenerateUUID.create());
                album.setUrl(url);
                album.setIsCover(isCover);
                album.setFarmId(itemId);
                album.setCreatedTime(new Date());
                album.setStatus(1);
                albumMapper.addFarmAlbum(album);
            }
        }else {
            log.error("当前用户【{}】添加农场相册失败",login);
        }
    }

    @Override
    public List<FarmAlbum> getAllAlbum(String itemId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = accountMapper.findUserByUsername(login);
        String companyId = user.getCompanyId();
        Integer type = user.getIsInsider();
        if (type == UserType.内部账户.typeCode()){
            return albumMapper.findAllFarmAlbum(itemId);
        }else if (type == UserType.农场账户.typeCode()){
            return albumMapper.findAllFarmAlbum(companyId);
        }else {
            log.error("当前用户 {} 试图查看 {} 相册",user.getId(),itemId);
            return null;
        }
    }

    @Override
    public void updateAlbum(List<FarmAlbum> farmAlbums,String itemId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = accountMapper.findUserByUsername(login);
        String companyId = user.getCompanyId();
        Integer type = user.getIsInsider();
        if (type == UserType.农场账户.typeCode()){
            //删除之前的相册信息
            albumMapper.deleteFarmAlbum(companyId);
            addFarmAlbum(farmAlbums,companyId);
        }else if(type == UserType.内部账户.typeCode()) {
            //删除之前的相册信息
            log.warn("当前用户【{}】 在 {} 更改了 {} 农场相册",login,new Date(),itemId);
            albumMapper.deleteFarmAlbum(itemId);
            addFarmAlbum(farmAlbums,itemId);
        }else {
            log.error("当前用户【{}】试图修改 {} 农场相册",login,itemId);
        }
    }

    @Override
    public int applyToClient(String id,String operation)  {
        if ("up".equals(operation)){
            //1.首先检查是否已经有封面图片
            Integer i = farmMapper.hasCoverImg(id);
            if (i == 0){
                log.error("农场{}于{}上市失败 因为没有上传封面图",id,new Date());
                return -1;
            }
            //2.检查info里面的内容是否为空
            DisplayInfo displayInfo = farmMapper.findDisplayInfoById(id);
            if (displayInfo.getIntro() == null || displayInfo.getContactNum() == null){
                log.error("农场{}于{}上市失败 因为没有填写联系方式与简介",id,new Date());
                return -2;
            }
        }
        //进行修改
        return farmMapper.updateStatus(id,operation);
    }

    @Override
    public FarmAlbum addBannerAlbum(MultipartFile file, String id) {
        //查询当前用户相册的数量
        int count = farmMapper.findImgCountById(id);
        if(count >=  10 ){
            throw new RuntimeException("当前商铺"+id+"相册数量已经超过限制");
        }
        String resultId = addAlbum(file, id, 0);
        return farmMapper.findAlbumById(resultId);
    }

    @Override
    @Transactional
    public FarmAlbum addCoverAlbum(MultipartFile file, String id) {
        //先查询是否已经存在封面图
        FarmAlbum cover = farmMapper.findAlbumCover(id);
        if (cover != null){
            //先删除之前的图片
            farmMapper.deleteAlbumById(cover.getId());
            String resultId = addAlbum(file, id, 1);
            return farmMapper.findAlbumById(resultId);
        }else {
            String resultId = addAlbum(file, id, 1);
            return farmMapper.findAlbumById(resultId);
        }
    }

    /**
     *
     * @param file
     * @param id
     * @param type 0:banner 1:cover
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/5 11:17
     */
    private String addAlbum(MultipartFile file,String id,int type){
        String imgUrl = imageUploadService.upload(file);
        FarmAlbum album = new FarmAlbum();
        String ID = GenerateUUID.create();
        album.setId(ID);
        album.setUrl(imgUrl);
        album.setIsCover(type);//添加banner
        album.setFarmId(id);
        album.setCreatedTime(new Date());
        album.setStatus(1);
        farmMapper.addAlbum(album);
        return ID;
    }

    @Override
    public void deleteAlbum(String id) {
        String storeId = farmMapper.findfarmIdByAlbumId(id);
        int count  = farmMapper.findImgCountById(storeId);
        if (count <= 1){
            throw new RuntimeException("当前相册"+id+"至少需要一张图片");
        }
        farmMapper.deleteAlbumById(id);
    }

    @Override
    public List<FarmAlbum> allStoreAlbum(String id) {
        id = ToNullUtil.toNull(id);
        if (id == null){
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = accountMapper.findUserByUsername(name);
            id = user.getCompanyId();
        }
        return farmMapper.findAllAlbum(id);
    }

    @Override
    public void updateSort(String id, int sortValue) {
        farmMapper.updateSort(id,sortValue);
    }
}
