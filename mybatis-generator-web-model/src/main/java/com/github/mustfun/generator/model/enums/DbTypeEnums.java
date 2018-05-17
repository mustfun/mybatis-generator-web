package com.github.mustfun.generator.model.enums;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/5/17
 * @since 1.0
 */
public enum DbTypeEnums {
    MYSQL(1,"MYSQL数据库");

    private Integer code;
    private String  mgs;

    DbTypeEnums(Integer code, String mgs) {
        this.code = code;
        this.mgs = mgs;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMgs() {
        return mgs;
    }

    public void setMgs(String mgs) {
        this.mgs = mgs;
    }
}
