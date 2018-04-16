package com.github.mustfun.generator.model.po;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/4/16
 * @since 1.0
 */
@Setter
@Getter
public class LocalColumn {
    private String name;
    private String type;
    private Integer size;
    private Boolean nullable;
    private Integer position;
}
