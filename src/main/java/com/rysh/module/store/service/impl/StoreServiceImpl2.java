package com.rysh.module.store.service.impl;
import java.util.Date;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.commonService.service.ImageUploadService;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.store.beans.*;
import com.rysh.module.store.mapper.StoreMapper2;
import com.rysh.module.store.service.StoreService2;
import com.rysh.module.utils.GenerateUUID;
import com.rysh.module.utils.ToNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StoreServiceImpl2 implements StoreService2 {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private StoreMapper2 mapper;

    @Autowired
    private ImageUploadService imageUploadService;

    @Override
    @Transactional
    public StoreInfo grangeInfo(String id) {
        String companyId = id;
        String mayNull = ToNullUtil.toNull(id);
        if (mayNull == null){
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = accountMapper.findUserByUsername(login);
            companyId = user.getCompanyId();
        }
        return mapper.findStoreInfoByCompanyId(companyId);
    }

    @Override
    public PageInfo<Store> search(ParamBean param) {
        PageHelper.startPage(param.getPage(),param.getSize());
        PageHelper.orderBy(param.getSortByFiled() + " "+param.getSortByOrder());
        List<Store> farmAndUserList = mapper.findBySearch(param.getSearch());
        return getUserInfo(farmAndUserList);
    }

    @Override
    public Store getStoreByLogin(String login) {
        return mapper.findStoreByLogin(login);
    }

    //获取每一个商铺Id去查询所属用户
    private PageInfo<Store> getUserInfo(List<Store> farmAndUserList) {
        for (Store farmAndUser : farmAndUserList) {
            String id = farmAndUser.getId();
            List<User> userList = accountMapper.findUserByStoreId(id);
            /*for (User user : userList) {
                Owner owner = new Owner();
                String trueName = user.getTrueName();
                String username = user.getUsername();
                owner.setOwnerUsername(username);
                owner.setOwnerName(trueName);
                farmAndUser.getOwners().add(owner);
            }*/
        }
        return new PageInfo<>(farmAndUserList);
    }

    @Override
    public StoreInfo storeById(String id) {
        id = ToNullUtil.toNull(id);
        if (id == null){
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = accountMapper.findUserByUsername(name);
            id = user.getCompanyId();
        }
        return mapper.findStoreInfoByCompanyId(id);
    }

    @Override
    @Transactional
    public void updateStoreInfo(StoreInfo storeInfo) {
        String address = storeInfo.getAddress();
        String contactNum = storeInfo.getContactNum();
        BigDecimal freight = storeInfo.getFreight();
        String intro = storeInfo.getIntro();
        String id = storeInfo.getId();

        Store store = new Store();
        store.setAddress(address);
        store.setContactNum(contactNum);
        store.setFreight(freight);
        store.setIntro(intro);
        store.setId(id);
        //更新商铺
        mapper.updateStore(store);
        //先删除标签
        mapper.deleteTag(id);
        List<StoreTag> tags = storeInfo.getTags();
        for (StoreTag tag : tags) {
            String tagId = tag.getId();
            StoreTagEntity entity = new StoreTagEntity();
            entity.setId(GenerateUUID.create());
            entity.setStoreId(id);
            entity.setStoreTagId(tagId);
            //更新商铺标签
            mapper.updateStoreTagEntity(entity);
        }
    }

    @Override
    public StoreAlbum addBannerAlbum(MultipartFile file,String id) {
        //查询当前用户相册的数量
        int count = mapper.findImgCountById(id);
        if(count >=  10 ){
            throw new RuntimeException("当前商铺"+id+"相册数量已经超过限制");
        }
        String resultId = addAlbum(file, id, 0);
        return mapper.findAlbumById(resultId);
    }

    @Override
    @Transactional
    public StoreAlbum addCoverAlbum(MultipartFile file, String id) {
        //先查询是否已经存在封面图
        StoreAlbum cover = mapper.findAlbumCover(id);
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
        StoreAlbum album = new StoreAlbum();
        String ID = GenerateUUID.create();
        album.setId(ID);
        album.setUrl(imgUrl);
        album.setIsCover(type);//添加banner
        album.setStoreId(id);
        album.setCreatedTime(new Date());
        album.setStatus(1);
        mapper.addAlbum(album);
        return ID;
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
    public List<StoreAlbum> allStoreAlbum(String id) {
        id = ToNullUtil.toNull(id);
        if (id == null){
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = accountMapper.findUserByUsername(name);
            id = user.getCompanyId();
        }
        return mapper.findAllAlbum(id);
    }

    @Override
    public void applyToClient(String operation, String id) {
        mapper.applyToClient(operation,id);
    }
}
