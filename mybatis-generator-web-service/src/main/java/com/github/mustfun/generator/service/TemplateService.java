package com.github.mustfun.generator.service;

import com.github.mustfun.generator.model.po.DbSourcePo;
import com.github.mustfun.generator.model.po.Template;

import java.util.List;

/**
 * Created by itar on 2017/4/6.
 */
public interface TemplateService {
    Template getOne(Integer id);

    Boolean saveTemplate(Template dbSourcePo);

    List<Template> queryList();

    Boolean deleteTemplate(Integer id);
}
