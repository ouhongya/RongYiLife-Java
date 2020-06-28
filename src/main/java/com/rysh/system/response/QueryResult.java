package com.rysh.system.response;


import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class QueryResult<T> {
   // private List<T> data;
    private Object data;
}
