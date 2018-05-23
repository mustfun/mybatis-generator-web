package com.github.mustfun.generator.service.impl;

import com.github.mustfun.generator.dao.mapper.TemplateMapper;
import com.github.mustfun.generator.model.po.Template;
import com.github.mustfun.generator.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by itar on 2017/4/6.
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateMapper dbSourceMapper;


    @Override
    public Template getOne(Integer id) {
        return dbSourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean saveTemplate(Template dbSourcePo) {
        return dbSourceMapper.insertSelective(dbSourcePo)==1;
    }

    @Override
    public List<Template> queryList() {
        return dbSourceMapper.queryList();
    }

    @Override
    public Boolean deleteTemplate(Integer id) {
        return dbSourceMapper.deleteByPrimaryKey(id)==1;
    }

    @Override
    public Boolean updateTemplate(Template template) {
        return dbSourceMapper.updateByPrimaryKeySelective(template)==1;
    }
}
