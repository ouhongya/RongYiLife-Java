package com.rysh.module.grange.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.commonService.service.ImageUploadService;
import com.rysh.module.farm.beans.DisplayInfo;
import com.rysh.module.farm.beans.FarmAlbum;
import com.rysh.module.farm.beans.Tag;
import com.rysh.module.farm.beans.TagEntity;
import com.rysh.module.farm.mapper.AlbumMapper;
import com.rysh.module.farm.mapper.TagMapper;
import com.rysh.module.grange.beans.Grange;
import com.rysh.module.grange.beans.GrangeAlbum;
import com.rysh.module.grange.beans.GrangeInfo;
import com.rysh.module.grange.mapper.GrangeMapper;
import com.rysh.module.grange.service.GrangeService;
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

import java.util.Date;
import java.util.List;
@Service
@Log4j2
public class GrangeServiceImpl implements GrangeService {

    @Autowired
    private GrangeMapper mapper;

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

    /**
     * 新增农庄
     * @param grange
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/19 16:45
     */
    public int addNewGrange(Grange grange){
        grange.setScore(0);
        grange.setCreatedTime(new Date());
        grange.setStatus(0);//未上架到客户端
        return mapper.insert(grange);
    }

    /**
     * 根据ID查询农庄
     * @param id
     * @return com.rysh.module.grange.beans.Grange
     * @author Hsiang Sun
     * @date 2019/9/19 16:45
     */
    public GrangeInfo grangeById(String id) {
        GrangeInfo grange = mapper.findGrangeInfoById(id);
        List<User> userList = accountMapper.findUserByStoreId(id);
        for (User user : userList) {
            Owner owner = new Owner();
            String trueName = user.getTrueName();
            String username = user.getUsername();
            owner.setOwnerUsername(username);
            owner.setOwnerName(trueName);
            grange.getOwners().add(owner);
        }
        return grange;
    }

    /**
     * 更新农庄信息
     * @param grange
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/19 16:45
     */
    public int updateGrange(Grange grange) {
        return mapper.update(grange);
    }

    /**
     * 删除农庄
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/9/19 16:46
     */
    public int deletGrange(String id) {
        storeMapper.updateCompany(id);
        return mapper.updateStatusById(id);
    }

    /**
     * 查询农庄的所有信息与农庄搜索
     * @param param
     * @return com.github.pagehelper.PageInfo<com.rysh.module.grange.beans.Grange>
     * @author Hsiang Sun
     * @date 2019/9/19 16:46
     */
    @Transactional
    public PageInfo<Grange> search(ParamBean param) {
        PageHelper.startPage(param.getPage(),param.getSize());
        PageHelper.orderBy(param.getSortByFiled() + " "+param.getSortByOrder());
        List<Grange> farmAndUserList = mapper.findByCondition(param.getSearch());
        return getUserInfo(farmAndUserList);
    }

    @Override
    @Transactional
    public DisplayInfo grangeInfo(String login,String itemId) {
        DisplayInfo info = new DisplayInfo();
        //查询用户基本信息
        User loginUser = accountMapper.findUserByUsername(login);
        String s = ToNullUtil.toNull(itemId);
        //查询农场基本信息
        Grange farm = mapper.findGrangeByLogin(login,s);
        List<User> users = accountMapper.findUserByStoreId(farm.getId());
        List<Tag> tags = tagMapper.findTagByFarmId(farm.getId());
        info.setId(farm.getId());
        info.setAccount(users);
        info.setAddress(farm.getAddress());
        info.setName(farm.getName());
        info.setScore(Long.valueOf(farm.getScore()));
        info.setTrueName(loginUser.getTrueName());
        info.setContactNum(farm.getContactNum());
        info.setIntro(farm.getIntro());
        info.setTags(tags);
        info.setFreight(farm.getFreight());
        info.setStatus(farm.getStatus());
        return info;
    }

    @Override
    public Grange getGrangeByLogin(String login) {
        return mapper.findGrangeByLogin(login,null);
    }

    //获取每一个农庄Id去查询所属用户
    private PageInfo<Grange> getUserInfo(List<Grange> farmAndUserList) {
        for (Grange farmAndUser : farmAndUserList) {
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

    @Override
    public void updateGrangeInfo(GrangeInfo grangeInfo) {
        String entityId = grangeInfo.getId();
        //先删除已经存在的标签
        tagMapper.deleteTagEntityByEntityId(entityId);
        List<Tag> tags = grangeInfo.getTags();
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
        mapper.updateGrangeInfo(grangeInfo);
    }

    @Override
    public void addFarmAlbum(List<GrangeAlbum> farmAlbums,String itemId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = accountMapper.findUserByUsername(login);
        if (user.getIsInsider() == UserType.农庄账户.typeCode()){
            String companyId = user.getCompanyId();
            //查询当前用户相册信息
            List<GrangeAlbum> albums = albumMapper.findGrangeAlbumById(companyId);
            if (albums.size() > 9){
                log.error("当前用户["+user.getTrueName()+"]相册已满");
                return;
            }

            for (GrangeAlbum farmAlbum : farmAlbums) {
                String url = farmAlbum.getUrl();
                int isCover = farmAlbum.getIsCover();

                GrangeAlbum album = new GrangeAlbum();
                album.setId(GenerateUUID.create());
                album.setUrl(url);
                album.setIsCover(isCover);
                album.setGrangeId(companyId);
                album.setCreatedTime(new Date());
                album.setStatus(1);
                albumMapper.addGrangeAlbum(album);
            }
        }else if (user.getIsInsider() == UserType.内部账户.typeCode()){
            //查询当前用户相册信息
            List<GrangeAlbum> albums = albumMapper.findGrangeAlbumById(itemId);
            if (albums.size() > 9){
                log.error("当前用户["+user.getTrueName()+"]相册已满");
                return;
            }

            for (GrangeAlbum farmAlbum : farmAlbums) {
                String url = farmAlbum.getUrl();
                int isCover = farmAlbum.getIsCover();
                GrangeAlbum album = new GrangeAlbum();
                album.setId(GenerateUUID.create());
                album.setUrl(url);
                album.setIsCover(isCover);
                album.setGrangeId(itemId);
                album.setCreatedTime(new Date());
                album.setStatus(1);
                albumMapper.addGrangeAlbum(album);
                log.warn("管理员【{}】于 {} 为农庄{}添加了相册",login,new Date(),itemId);
            }
        }else {
                log.error("当前用户【{}】试图为农庄{}添加相册",login,itemId);
        }
    }

    @Override
    public List<GrangeAlbum> getAllAlbum(String itemId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = accountMapper.findUserByUsername(login);
        String companyId = user.getCompanyId();
        Integer type = user.getIsInsider();
        if (type == UserType.内部账户.typeCode()){
            return albumMapper.findAllGrangeAlbum(itemId);
        }else if (type == UserType.农庄账户.typeCode()){
            return albumMapper.findAllGrangeAlbum(companyId);
        }else {
            log.error("当前用户 {} 试图查看他人相册",user.getId());
            return null;
        }
    }

    @Override
    public void updateAlbum(List<GrangeAlbum> farmAlbums,String itemId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = accountMapper.findUserByUsername(login);

        if (user.getIsInsider() == UserType.农庄账户.typeCode()){
            String companyId = user.getCompanyId();
            //删除之前的相册信息
            albumMapper.deleteGrangeAlbum(companyId);
            addFarmAlbum(farmAlbums,companyId);
        }else if (user.getIsInsider() == UserType.内部账户.typeCode()){
            //删除之前的相册信息
            albumMapper.deleteGrangeAlbum(itemId);
            addFarmAlbum(farmAlbums,itemId);
            log.warn("当前用户【{}】 在 {} 更改了 {} 农庄相册",login,new Date(),itemId);
        }else {
            log.error("当前用户【{}】试图修改 {} 农庄相册",login,itemId);
        }
    }

    @Override
    public int applyToClient(String id,String operation) {
       if ("up".equals(operation)){
           //1.首先检查是否已经有封面图片
           Integer i = mapper.hasCoverImg(id);
           if (i == 0){
               log.error("农场{}于{}上市失败 因为没有上传封面图",id,new Date());
               return -1;
           }
           //2.检查info里面的内容是否为空
           DisplayInfo displayInfo = mapper.findDisplayInfoById(id);
           if (displayInfo.getIntro() == null || displayInfo.getContactNum() == null){
               log.error("农场{}于{}上市失败 因为没有填写联系方式与简介",id,new Date());
               return -2;
           }
       }

        //进行修改
        return mapper.updateStatus(id,operation);
    }

    @Override
    public GrangeAlbum addBannerAlbum(MultipartFile file, String id) {
        //查询当前用户相册的数量
        int count = mapper.findImgCountById(id);
        if(count >=  10 ){
            throw new RuntimeException("当前商铺"+id+"相册数量已经超过限制");
        }
        String resultId = addAlbum(file, id, 0);
        return mapper.findAlbumById(resultId);
    }

    @Override
    public GrangeAlbum addCoverAlbum(MultipartFile file, String id) {
        //先查询是否已经存在封面图
        GrangeAlbum cover = mapper.findAlbumCover(id);
        if (cover != null){
            //先删除之前的图片
            mapper.deleteAlbumById(cover.getId());
            String resultId = addAlbum(file, id, 1);
            return mapper.findAlbumById(resultId);
        }else {
            String resultId = addAlbum(file, id, 1);
            return mapper.findAlbumById(resultId);
        }
    }

    @Override
    public void deleteAlbum(String id) {
        String storeId = mapper.findStoreIdByAlbumId(id);
        int count  = mapper.findImgCountById(storeId);
        if (count <= 1){
            throw new RuntimeException("当前相册"+id+"至少需要一张图片");
        }
        mapper.deleteAlbumById(id);
    }

    @Override
    public List<GrangeAlbum> allStoreAlbum(String id) {
        id = ToNullUtil.toNull(id);
        if (id == null){
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = accountMapper.findUserByUsername(name);
            id = user.getCompanyId();
        }
        return mapper.findAllAlbum(id);
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
        GrangeAlbum album = new GrangeAlbum();
        String ID = GenerateUUID.create();
        album.setId(ID);
        album.setUrl(imgUrl);
        album.setIsCover(type);//添加banner
        album.setGrangeId(id);
        album.setCreatedTime(new Date());
        album.setStatus(1);
        mapper.addAlbum(album);
        return ID;
    }

    @Override
    public void updateSort(String id, int sortValue) {
        mapper.updateSort(id,sortValue);
    }
}
