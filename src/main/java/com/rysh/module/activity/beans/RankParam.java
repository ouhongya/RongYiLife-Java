package com.rysh.module.activity.beans;

import lombok.Data;

import java.util.List;

@Data

/**
 * 新加rank时接收参数的类
 * @author Hsiang Sun
 * @date 2019/10/17 10:11
 */
public class RankParam {
    private String url;
    private String Introduction;
    private List<RankInfo> arr;
}
