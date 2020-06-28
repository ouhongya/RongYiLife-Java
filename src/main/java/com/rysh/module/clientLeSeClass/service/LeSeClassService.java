package com.rysh.module.clientLeSeClass.service;

import com.rysh.module.clientLeSeClass.beans.LeSe;
import com.rysh.module.clientLeSeClass.beans.SuperLeSe;
import com.rysh.module.garbage.beans.Garbage;

import java.util.List;

public interface LeSeClassService {
    LeSe findLeSeCategoryByToken(String uid) throws Exception;

    List<Garbage> findLeSeByCategoryId(String categoryId) throws Exception;

    List<SuperLeSe> searchLeSe(String search,String uid);
}
