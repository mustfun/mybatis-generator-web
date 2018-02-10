package com.github.mustfun.generator.service.impl;

import com.github.mustfun.generator.dao.mapper.CityMapper;
import com.github.mustfun.generator.model.po.City;
import com.github.mustfun.generator.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dengzhiyuan on 2017/4/6.
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public City getOne(Integer id) {
        //强制切换路由为主库
//        HintManager hintManager = HintManager.getInstance();
//        hintManager.setMasterRouteOnly();

        /**
         * 没有上面这句话时候会去读从库，加了之后会去读主库
         * ，sharding-jdbc会自动选择去读取从库还是主库
         */

        return cityMapper.selectByPrimaryKey(id);
    }

    /**
     * 注意在这里测试一下spring 事务管理 和  sharding-jdbc结合得怎么样
     * @param city
     * @return
     */
    @Override
    @Transactional
    public Integer addOneCity(City city) {
        int insert = cityMapper.insert(city);
        //int i=1/0; 测试事务也成功啦！啦啦啦啦啦
        return insert;
    }

    /**
     * 测试一下写库和读库的读写情况
     * @param city
     * @return
     */
    @Override
    public City saveAndGet(City city) {
        int id=cityMapper.insert(city);
        return  cityMapper.selectByPrimaryKey(2);
        //实际测试sharding-jdbc在写完主库之后，会去从库读
    }
}
