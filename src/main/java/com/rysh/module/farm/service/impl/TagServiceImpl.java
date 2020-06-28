package com.rysh.module.farm.service.impl;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.Tag;
import com.rysh.module.farm.beans.TagResponse;
import com.rysh.module.farm.mapper.TagMapper;
import com.rysh.module.farm.service.TagService;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.utils.GenerateUUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper mapper;

    @Autowired
    private AccountMapper accountMapper;


    @Override
    public int addTag(String tagName, String operator) {

        //todo 先判断是否已经存在
        List<Tag> isExist = mapper.findTagByName(tagName);
        if (isExist.size() != 0){
            log.error("当前标签["+tagName+"]已存在");
            return 0;
        }

        Tag tag = new Tag();
        tag.setId(GenerateUUID.create());
        tag.setName(tagName);
        tag.setOperator(accountMapper.findUserByUsername(operator).getTrueName());
        tag.setCreatedTime(new Date());
        tag.setStatus(1);
        return mapper.addTag(tag);
    }

    @Override
    public PageInfo<TagResponse> allTag(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<TagResponse> responseList = mapper.findAllTag();
        return new PageInfo<>(responseList);
    }

    @Override
    public void deleteTag(String id) {
        //先判断当前标签是否已经被挂载？
        int count = mapper.findTagEntityNumById(id);
        if (count > 0){
            log.error("当前标签"+id+"已经被挂载不能被删除");
            throw new RuntimeException("当前标签"+id+"已经被挂载不能被删除");
        }
        mapper.deleteTag(id);
    }

    @Override
    public void updateTag(Tag tag,String login) {
        tag.setOperator(accountMapper.findUserByUsername(login).getTrueName());
        mapper.updateTag(tag);
    }
}
