package com.github.mustfun.generator.model.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/5/16
 * @since 1.0
 */

@Setter
@Getter
public class Template {

    private Integer id;
    private String tepName;
    private String tepDesc;
    private String tepContent;
    private String createBy;
    private Integer type;
    private Date createTime;
}
