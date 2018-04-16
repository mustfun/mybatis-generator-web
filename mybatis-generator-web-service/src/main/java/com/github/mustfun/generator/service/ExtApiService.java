package com.github.mustfun.generator.service;

import com.github.mustfun.generator.model.po.DbConfigPo;
import com.github.mustfun.generator.model.po.LocalTable;
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
    BaseResult<Long> saveDbConfig(DbConfigPo configPo);

    List<LocalTable> getTables(Connection connection);

    void initDb(DbConfigPo dbConfigPos);

    BaseResult<Long> generateCode(String tableNames);
}
