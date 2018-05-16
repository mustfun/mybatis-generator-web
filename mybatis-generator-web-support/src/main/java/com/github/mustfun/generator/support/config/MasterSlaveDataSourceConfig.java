package com.github.mustfun.generator.support.config;

import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by dengzhiyuan on 2017/4/25.
 */
@Configuration
@AutoConfigureAfter(value = {MasterDataSourceConfig.class,SlaveDataSourceConfig.class})
public class MasterSlaveDataSourceConfig {

    /**
     * @return 读写分离配置，本配置不涉及到分库分表
     */
    //@Autowired   //这个注解是根据类型进行装配的，我们这类要根据名称进行装配，所以只能用@Resource
    @Resource
    private DataSource masterWriteDataSource;

    @Resource
    private DataSource slaveWriteDataSource;


    /**
     * sharding-jdbc不支持sqllite
     * @return
     *//*
    @Bean(name = "dataSource")
    public DataSource dataSource(){

        // 构建读写分离数据源, 读写分离数据源实现了DataSource接口, 可直接当做数据源处理. masterDataSource, slaveDataSource0, slaveDataSource1等为使用DBCP等连接池配置的真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("masterDataSource", masterWriteDataSource);
        dataSourceMap.put("slaveDataSource0", slaveWriteDataSource);

        // 构建读写分离配置
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration();
        masterSlaveRuleConfig.setName("ms_ds_config");
        masterSlaveRuleConfig.setMasterDataSourceName("masterDataSource");
        masterSlaveRuleConfig.getSlaveDataSourceNames().add("slaveDataSource0");


        DataSource dataSource = null;
        try {
            dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRuleConfig,new HashMap<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }*/
}

