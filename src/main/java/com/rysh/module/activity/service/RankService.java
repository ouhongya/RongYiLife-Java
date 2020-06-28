package com.rysh.module.activity.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.activity.beans.ClientRankResponse;
import com.rysh.module.activity.beans.Rank;
import com.rysh.module.activity.beans.RankParam;
import com.rysh.module.commonService.beans.ParamBean;

public interface RankService {
    void addRank(RankParam rank, String login);

    /**
     * 查询所有的排行纪录
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.activity.beans.Rank>
     * @author Hsiang Sun
     * @date 2019/10/18 16:05
     */
    PageInfo<Rank> allRank(ParamBean paramBean);

    /**
     * 根据排行榜id查询排行榜详情
     * @param id
     * @return java.util.List<com.rysh.module.activity.beans.RankItem>
     * @author Hsiang Sun
     * @date 2019/10/18 10:48
     */
    ClientRankResponse getRankItemByRankId(String id);
}
