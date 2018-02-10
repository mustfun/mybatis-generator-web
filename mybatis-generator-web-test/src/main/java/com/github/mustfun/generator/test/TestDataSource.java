package com.github.mustfun.generator.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by dengzhiyuan on 2017/4/6.
 * 没成功..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.itar.soa")
@EnableAutoConfiguration
public class TestDataSource {

    @Autowired
    private DruidDataSource dataSource;


    @Test
    public void source(){
        System.out.println(dataSource.getName());
    }
}
