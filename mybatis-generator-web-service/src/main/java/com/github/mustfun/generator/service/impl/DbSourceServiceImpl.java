package com.github.mustfun.generator.service.impl;

import com.github.mustfun.generator.dao.mapper.DbSourceMapper;
import com.github.mustfun.generator.model.po.DbSourcePo;
import com.github.mustfun.generator.service.DbSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dengzhiyuan on 2017/4/6.
 */
@Service
public class DbSourceServiceImpl implements DbSourceService {

    @Autowired
    private DbSourceMapper dbSourceMapper;


    @Override
    public DbSourcePo getOne(Integer id) {
        return dbSourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean saveDbConfig(DbSourcePo dbSourcePo) {
        return dbSourceMapper.insertSelective(dbSourcePo)==1;
    }
}
