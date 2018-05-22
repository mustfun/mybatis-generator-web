package com.github.mustfun.generator.service;

import com.github.mustfun.generator.model.po.DbSourcePo;
import com.github.mustfun.generator.model.po.LocalTable;
import com.github.mustfun.generator.model.po.Template;
import com.github.mustfun.generator.support.result.BaseResult;

import java.sql.Connection;
import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/4/13
 * @since 1.0
 */
public interface ExtApiService {
    BaseResult<Long> saveDbConfig(DbSourcePo dbSourcePo);

    List<LocalTable> getTables(Connection connection);

    void initDb(DbSourcePo dbConfigPos);

    byte[] generateCode(List<String> tableNames, String packageName, String address, List<String> vmList);

    BaseResult<Long> saveTemplate(Template dbSourcePo);

    BaseResult<Boolean> deleteTemplate(Integer dbSourcePo);
}
