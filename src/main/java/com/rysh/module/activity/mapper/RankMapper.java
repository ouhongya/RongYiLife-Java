package com.rysh.module.activity.mapper;

import com.rysh.module.activity.beans.ClientRank;
import com.rysh.module.activity.beans.Rank;
import com.rysh.module.activity.beans.RankItem;

import java.util.List;

public interface RankMapper {
    /**
     * 添加排行榜外层表
     * @param rank
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/17 10:58
     */
    void addRank(Rank rank);

    /**
     * 添加排行榜内层表
     * @param rankItem
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/17 10:58
     */
    void addRankItem(RankItem rankItem);

    /**
     * 将排行榜里面的数据过期
     * @param
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/18 10:01
     */
    void expiredRank(String operator);

    /**
     * 将排行榜详情表里面的信息设置过期
     * @param
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/18 10:08
     */
    void expiredRankItem();

    /**
     * 查询所有的排行榜信息
     * @param
     * @return java.util.List<com.rysh.module.activity.beans.Rank>
     * @author Hsiang Sun
     * @date 2019/10/18 10:36
     */
    List<Rank> findAllRank();

    /**
     * 根据排行榜Id查询排行榜详情
     * @param id
     * @return java.util.List<com.rysh.module.activity.beans.RankItem>
     * @author Hsiang Sun
     * @date 2019/10/18 10:49
     */
    List<ClientRank> findRankItemByRankId(String id);
}
