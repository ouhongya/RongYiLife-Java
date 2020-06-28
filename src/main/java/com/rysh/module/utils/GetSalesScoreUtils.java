package com.rysh.module.utils;

import com.rysh.module.clientOrders.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetSalesScoreUtils {
    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * 获取商品的销量
     * @param goodsId   商品id
     * @return
     */
    public Integer getGoodsSales(String goodsId){
        //根据商品信息获取销量
        Integer goodsSales = ordersMapper.findItemCountByGoodsId(goodsId);
        return goodsSales;
    }

    /**
     * 查询店铺的销量
     * @param marketId   店铺id
     * @return
     */
    public Integer getMarketSales(String marketId){
            Integer marketSales=ordersMapper.findSplitCountByMarketId(marketId);
            return marketSales;
    }

    /**
     * 根据店铺id获取评分
     * @param marketId
     * @return
     */
    public BigDecimal getScoreByMarketId(String marketId) {
        //评分集合初始化
        List<Integer> scores = new ArrayList<>();
        //查询这个店铺的所有子订单
        List<String> splitIds=ordersMapper.findSplitIdsByMarketId(marketId);
        //通过子订单查询评分信息
        for (String splitId : splitIds) {
            Integer score=ordersMapper.findScoreBySplitId(splitId);
            if(score==null){
                score=0;
            }
            scores.add(score);
        }
        //总分初始化
        BigDecimal sumScore = new BigDecimal("0");
        //计算总分
        for (Integer score : scores) {
            sumScore=sumScore.add(new BigDecimal(score.toString()));
        }

        //计算平均分
        Integer scoresSize=new Integer(scores.size());
        if(scoresSize==0){
            return new BigDecimal("0");
        }
        BigDecimal avgScore = sumScore.divide(new BigDecimal(scoresSize.toString()),1,BigDecimal.ROUND_HALF_UP);
        return avgScore;
    }
}
