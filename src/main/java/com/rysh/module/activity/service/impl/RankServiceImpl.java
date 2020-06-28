package com.rysh.module.activity.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.activity.beans.*;
import com.rysh.module.activity.mapper.RankMapper;
import com.rysh.module.activity.service.RankService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.utils.GenerateUUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class RankServiceImpl implements RankService {

    @Autowired
    private RankMapper mapper;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional
    public void addRank(RankParam rankParam, String login) {
        User user = accountMapper.findUserByUsername(login);
        String operator = user.getTrueName();

        //先清洗排行榜里面的数据
        expiredRankData(operator);

        //3.向主表新加数据
        String ID = GenerateUUID.create();

        Rank rank = new Rank();
        rank.setId(ID);
        rank.setCreatedTime(new Date());
        rank.setOperator(operator);
        rank.setIntro(rankParam.getIntroduction());
        rank.setUrl(rankParam.getUrl());

        mapper.addRank(rank);
        //4.向rankItem表中添加数据
        List<RankInfo> arr = rankParam.getArr();
        for (RankInfo rankInfo : arr) {
            String rankInfoId = UUID.randomUUID().toString().replace("-", "");
            RankItem rankItem = new RankItem();
            rankItem.setId(rankInfoId);
            rankItem.setSequence(rankInfo.getOrders());
            rankItem.setName(rankInfo.getName());
            rankItem.setUrl(rankInfo.getUrl());
            rankItem.setRankingUserId(ID);
            rankItem.setCreatedTime(new Date());
            rankItem.setStatus(1);//important!!!

            mapper.addRankItem(rankItem);
        }
    }

    /**
     * 清洗排行榜里面的数据
     * @param operator
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/18 10:15
     */
    @Transactional
    void expiredRankData(String operator){
        try {
            //1.先将主表里面的数据设置为过期
            mapper.expiredRank(operator);
            //2.将附表里面的数据设置为过期
            mapper.expiredRankItem();
        } catch (Exception e) {
            log.error("排行榜在清洗数据时遇到错误"+e);
        }
    }

    @Override
    public PageInfo<Rank> allRank(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<Rank> rankList = mapper.findAllRank();
        return new PageInfo<>(rankList);
    }

    @Override
    public ClientRankResponse getRankItemByRankId(String id) {
        List<ClientRank> clientRank = mapper.findRankItemByRankId(id);
        String banner = null;
        String introduction = null;
        List<ClientRankUserInfo> clientRankUserInfos = new ArrayList<>();
        for (ClientRank rank : clientRank) {
            banner = rank.getBanner();
            introduction = rank.getIntroduction();
            ClientRankUserInfo userInfo = new ClientRankUserInfo();
            userInfo.setNo(rank.getNo());
            userInfo.setAvatar(rank.getAvatar());
            userInfo.setName(rank.getName());
            clientRankUserInfos.add(userInfo);
        }
        ClientRankResponse clientRankResponse = new ClientRankResponse();
        clientRankResponse.setBanner(banner);
        clientRankResponse.setIntroduction(introduction);
        clientRankResponse.setDetail(clientRankUserInfos);
        return clientRankResponse;
    }
}
