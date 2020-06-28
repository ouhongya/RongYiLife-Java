package com.rysh.module.utils;


import java.util.*;

public class WxSign {

    private static String Key = "192006250b4c09247ec02edce69f6a2d";

    public static void main(String[] args) {
        SortedMap<String,String> params;
    }

    public static String sign(SortedMap<String,String> params){

        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String k = entry.getKey();
            String v = entry.getValue();
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + Key);

        return sb.toString();
    }


}
