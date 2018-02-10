package com.github.mustfun.generator.support.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by dengzhiyuan on 2017/4/5.
 */
@ConfigurationProperties(prefix = "database.slave.druid") //这个注解只是把属性设置进去，还达不到注入要求，要么加一个@configuration，要么加个@Component
public class SlaveDruidConfig extends BaseDruidConfig{

    private Logger logger = LoggerFactory.getLogger(getClass());

    public SlaveDruidConfig(){
        logger.info("druid正在初始化中====={slave数据库}",getMaxActive());
    }

}
