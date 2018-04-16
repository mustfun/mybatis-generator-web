package com.github.mustfun.generator.model.po;

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
    private Long id;
    private String address;
    private String dbName;
    private String userName;
    private String password;
}
