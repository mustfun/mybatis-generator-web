package com.github.mustfun.generator.support.config;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created by dengzhiyuan on 2017/4/10.
 */
@Configuration
@EnableConfigurationProperties({DruidConfig.class,SlaveDruidConfig.class})
//这个就相当于把DruidConfig也注入了，那边没有注入
public class AbstractDataSourceConfig {



    @Autowired
    private Slf4jLogFilter slf4jLogFilter;

    public DruidDataSource initDataBase(BaseDruidConfig druidConfig) throws Exception{

        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(druidConfig.getUrl());
        datasource.setUsername(druidConfig.getUsername());
        datasource.setPassword(druidConfig.getPassword());
        datasource.setDriverClassName(druidConfig.getDriverClassName());
        datasource.setInitialSize(druidConfig.getInitialSize());
        datasource.setMinIdle(druidConfig.getMinIdle());
        datasource.setMaxActive(druidConfig.getMaxActive());
        datasource.setMaxWait(druidConfig.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(druidConfig.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(druidConfig.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(druidConfig.getValidationQuery());
        /*
        datasource.setTestWhileIdle(druidConfig.get);
        datasource.setTestOnBorrow(druidConfig.gette);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(druidConfig.getpo);
        datasource.setMaxOpenPreparedStatements(druidConfig.);
        */
        //设置druid监控
        datasource.setFilters(druidConfig.getFilters());
        //设置druid日志
        datasource.setProxyFilters(Arrays.asList(slf4jLogFilter));

        datasource.setMaxPoolPreparedStatementPerConnectionSize(druidConfig.getMaxPoolPreparedStatementPerConnectionSize());
        datasource.setConnectionProperties(druidConfig.getConnectionProperties());
        datasource.setConnectionProperties(druidConfig.getConnectionProperties());
        return datasource;
    }

}
