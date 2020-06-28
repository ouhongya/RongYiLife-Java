package com.rysh.module.clientTag.service.impl;

import com.rysh.module.clientTag.beans.ClientTag;
import com.rysh.module.clientTag.mapper.ClientTagMapper;
import com.rysh.module.clientTag.service.ClientTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ClientClientTagServiceImpl implements ClientTagService {

    @Autowired
    private ClientTagMapper clientTagMapper;
    @Override
    public List<ClientTag> findAllTag() throws Exception {
        return clientTagMapper.findAllTag();
    }
}
