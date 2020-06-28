package com.rysh.module.farm.service.impl;

import com.rysh.module.farm.mapper.TagItemMapper;
import com.rysh.module.farm.service.TagEntityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TagEntityServiceImpl implements TagEntityService {
    @Autowired
    private TagItemMapper mapper;

    @Override
    public int addTag(String name) {
        return 0;
    }
}
