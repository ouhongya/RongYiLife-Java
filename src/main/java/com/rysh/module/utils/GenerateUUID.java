package com.rysh.module.utils;

import java.util.UUID;

public class GenerateUUID {
    public static String create(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
}
