package com.github.mustfun.generator.biz.config;

import com.alibaba.fastjson.JSON;
import com.github.mustfun.generator.model.constants.FileConstants;
import com.github.mustfun.generator.model.po.DbConfigPo;
import com.github.mustfun.generator.model.po.DbSourcePo;
import com.github.mustfun.generator.service.DbSourceService;
import com.github.mustfun.generator.service.ExtApiService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/4/16
 * @since 1.0
 */
@Component
public class InitDataBase {

    private static final Logger LOG = LoggerFactory.getLogger(InitDataBase.class);

    @Autowired
    private ExtApiService extApiService;

    @Autowired
    private DbSourceService dbSourceService;

    @PostConstruct
    public void initDataBase(){
        List<DbSourcePo> dbSourcePos = dbSourceService.queryList();
        for (DbSourcePo dbSourcePo : dbSourcePos) {
            extApiService.initDb(dbSourcePo);
        }
    }
}
