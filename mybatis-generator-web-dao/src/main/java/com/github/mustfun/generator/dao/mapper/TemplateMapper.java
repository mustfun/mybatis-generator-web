package com.github.mustfun.generator.dao.mapper;

import com.github.mustfun.generator.model.po.Template;
import com.github.mustfun.generator.model.po.Template;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TemplateMapper {

    int deleteByPrimaryKey(Integer id);


    int insertSelective(Template record);

    Template selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(Template record);


    List<Template> queryList();
}