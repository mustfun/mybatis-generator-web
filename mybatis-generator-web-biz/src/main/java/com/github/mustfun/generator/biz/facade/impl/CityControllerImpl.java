package com.github.mustfun.generator.biz.facade.impl;

import com.github.mustfun.generator.biz.facade.CityController;
import com.github.mustfun.generator.model.po.City;
import com.github.mustfun.generator.service.DbSourceService;
import com.github.mustfun.generator.support.result.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/5/4
 * @since 1.0
 */
@RestController
@RequestMapping("/city")
public class CityControllerImpl implements CityController {

    @Autowired
    private DbSourceService dbSourceService;

}
