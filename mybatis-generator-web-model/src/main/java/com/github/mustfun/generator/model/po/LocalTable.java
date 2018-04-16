package com.github.mustfun.generator.model.po;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/4/16
 * @since 1.0
 */
@Setter
@Getter
public class LocalTable {
    private Integer id;
    private String name;

    private List<LocalColumn> columnList;
}
