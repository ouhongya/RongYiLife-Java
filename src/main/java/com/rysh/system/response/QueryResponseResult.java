package com.rysh.system.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QueryResponseResult extends ResponseResult {

    QueryResult queryResult;

    public QueryResponseResult(ResultCode resultCode, QueryResult queryResult){
        super(resultCode);
        this.queryResult = queryResult;
    }

    public QueryResponseResult(ResultCode resultCode){
        super(resultCode);
    }

}
