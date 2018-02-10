package com.github.mustfun.generator.biz.facade.impl;

import com.github.mustfun.generator.biz.facade.CityController;
import com.github.mustfun.generator.model.po.City;
import com.github.mustfun.generator.service.CityService;
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
    private CityService cityService;

    @Override
    @RequestMapping(value = "getCity",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<City> getCity(Integer id) {
        City one = cityService.getOne(id);
        BaseResult<City> baseResult=new BaseResult<>();
        baseResult.setData(one);
        return baseResult;
    }

    @Override
    @RequestMapping(value = "addOneCity",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Integer> addOneCity(City id) {
        Integer integer = cityService.addOneCity(id);
        BaseResult<Integer> baseResult=new BaseResult<>();
        baseResult.setData(integer);
        return baseResult;
    }

    @Override
    @RequestMapping(value = "saveAndGet",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<City> saveAndGet(City id) {
        BaseResult<City> baseResult=new BaseResult<>();
        baseResult.setData(cityService.saveAndGet(id));
        return baseResult;
    }
}
