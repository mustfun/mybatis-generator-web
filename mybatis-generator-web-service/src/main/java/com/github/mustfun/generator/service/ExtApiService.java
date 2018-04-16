package com.github.mustfun.generator.service;

import com.github.mustfun.generator.model.po.DbConfigPo;
import com.github.mustfun.generator.support.result.BaseResult;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/4/13
 * @since 1.0
 */
public interface ExtApiService {
    BaseResult<Long> saveDbConfig(DbConfigPo configPo);
}
