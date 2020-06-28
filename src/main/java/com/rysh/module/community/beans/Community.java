package com.rysh.module.community.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Community {
    //主键
    private String id;
    //社区名称
    private String name;
    //创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp createdTime;
    //最后更新时间
    private Timestamp lastedUpdateTime;
    //关联的区域名称
    private String areaId;
    //社区地理位置
    private String address;
    //社区状态  1=启用  -1=软删除
    private Integer status;
}
