package com.github.mustfun.generator.model.po;

import com.alibaba.druid.support.json.JSONUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/4/13
 * @since 1.0
 */
@Setter
@Getter
public class DbConfigPo {
    private String address;
    private String dbName;
    private String userName;
    private String password;

    @Override
    public String toString() {
        return "{" +
                "address='" + address + '\'' +
                ", dbName='" + dbName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
