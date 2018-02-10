package com.github.mustfun.generator.support.transcation;

import com.github.mustfun.generator.support.config.MasterSlaveDataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by dengzhiyuan on 2017/4/26.
 * @EnableTransactionManagement  启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
 * {@link //docs.spring.io/spring/docs/current/javadoc-api/org/springframework/transaction/annotation/EnableTransactionManagement.html}
 */
@Configuration
@EnableTransactionManagement
//@AutoConfigureAfter(MasterSlaveDataSourceConfig.class)
public class TransactionConfig {

    Logger LOG= LoggerFactory.getLogger(TransactionConfig.class);

    @Autowired
    @Qualifier(value = "dataSource")
    private DataSource dataSource;

    @Bean
    public PlatformTransactionManager txManager(){
        LOG.info("启用我们自己的事务管理器");
        return new DataSourceTransactionManager(dataSource);
    }

}
