package com.rysh.module.clientTag.service;

import com.rysh.module.clientTag.beans.ClientTag;

import java.util.List;

public interface ClientTagService {

    List<ClientTag> findAllTag() throws Exception;
}
