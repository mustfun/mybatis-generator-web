package com.github.mustfun.generator.support.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by dengzhiyuan on 2017/4/10.
 */
//@Configuration  本分支sharding-jdbc-4测试分库分表，读写分离先去除
@EnableConfigurationProperties(SlaveDruidConfig.class)
public class SlaveDataSourceConfig extends AbstractDataSourceConfig{

    private static final Logger logger= LoggerFactory.getLogger(SlaveDataSourceConfig.class);


    @Autowired
    private SlaveDruidConfig slaveDruidConfig;


    /**
     * 这个可以自己注入，也可以让spring帮助我们注入,自己可以注入多个
     */
    @Bean(name="masterReadDataSource", initMethod = "init", destroyMethod = "close") //也可以为master
    public DataSource masterReadDataSource() throws Exception{
        logger.info("master 读数据库 datasource正在初始化中...");
        return initDataBase(slaveDruidConfig);
    }

    @Bean(name="slaveReadDataSource", initMethod = "init", destroyMethod = "close") //也可以为master
    public DataSource slaveReadDataSource() throws Exception{
        logger.info("slave 读数据库 datasource正在初始化中...");
        return initDataBase(slaveDruidConfig);
    }
}
