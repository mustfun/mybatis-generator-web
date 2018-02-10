package com.github.mustfun.generator.service;

import com.github.mustfun.generator.model.po.City;

/**
 * Created by dengzhiyuan on 2017/4/6.
 */
public interface CityService {
    City getOne(Integer id);

    Integer addOneCity(City city);

    City saveAndGet(City city);
}
