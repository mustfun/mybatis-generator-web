package com.github.mustfun.generator.biz.mq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dengzhiyuan on 2017/4/6.
 */
@Configuration
@ConfigurationProperties(prefix = "rocketMq")
public class MqConfig {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
