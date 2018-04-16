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
    private String address;
    private String dbName;
    private String userName;
    private String password;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"address\":\"")
                .append(address).append('\"');
        sb.append(",\"dbName\":\"")
                .append(dbName).append('\"');
        sb.append(",\"userName\":\"")
                .append(userName).append('\"');
        sb.append(",\"password\":\"")
                .append(password).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
