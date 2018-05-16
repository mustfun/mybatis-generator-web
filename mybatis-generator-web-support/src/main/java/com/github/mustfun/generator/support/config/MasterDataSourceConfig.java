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
 * Created by dengzhiyuan on 2017/4/6.
 */
@Configuration  //本分支sharding-jdbc-4测试分库分表，读写分离先去除
@EnableConfigurationProperties(DruidConfig.class)  //这个就相当于把DruidConfig也注入了，那边没有注入
public class MasterDataSourceConfig extends AbstractDataSourceConfig{

    private static final Logger logger= LoggerFactory.getLogger(MasterDataSourceConfig.class);

    @Autowired
    private DruidConfig druidConfig;


    /**
     * 这个可以自己注入，也可以让spring帮助我们注入,自己可以注入多个
     */
    @Bean(name="dataSource", initMethod = "init", destroyMethod = "close") //也可以为master
    @Primary  //Spring优先选择被该注解所标记的数据源            //放在这里是因为在初始化的时候必须要指定一个，不然很多地方会注入失败
    public DataSource dataSource() throws Exception{
        logger.info("master 写数据库 DataSource正在初始化........");
        return initDataBase(druidConfig);
    }

}
