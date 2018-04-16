package com.github.mustfun.generator.biz.config;

import com.alibaba.fastjson.JSON;
import com.github.mustfun.generator.model.constants.FileConstants;
import com.github.mustfun.generator.model.po.DbConfigPo;
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

    @PostConstruct
    public void initDataBase(){
        try {
            List<String> strings = IOUtils.readLines(new FileInputStream(FileConstants.TEMP_DB_CONFIG_DB), "UTF-8");
            for (String string : strings) {
                DbConfigPo dbConfigPos = JSON.parseObject(string, DbConfigPo.class);
                extApiService.initDb(dbConfigPos);
            }
            LOG.info("数据库初始化完成....");
        } catch (IOException e) {
            LOG.error("{}",e);
        }
    }
}
