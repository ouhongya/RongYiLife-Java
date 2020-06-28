package com.rysh.module.clientLeSeClass.beans;

import com.rysh.module.garbage.beans.GarbageCategory;
import lombok.Data;

import java.util.List;

@Data
public class LeSe {
    private String id;
    private String name;
    private List<GarbageCategory> garbageCategories;
}
