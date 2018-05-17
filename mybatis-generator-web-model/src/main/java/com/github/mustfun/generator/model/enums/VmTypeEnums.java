package com.github.mustfun.generator.model.enums;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/5/17
 * @since 1.0
 */
public enum  VmTypeEnums {
    DAO(1,"DAO层"),
    CONTROLLER(2,"Controller层"),
    SERVICE(3,"Service层"),
    MAPPER(4,"Mapper文件"),
    MODEL(5,"Model层");

    private Integer code;
    private String  mgs;

    VmTypeEnums(Integer code, String mgs) {
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
