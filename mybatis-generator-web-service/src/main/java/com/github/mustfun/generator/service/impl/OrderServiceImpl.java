package com.github.mustfun.generator.service.impl;

import com.github.mustfun.generator.dao.mapper.OrderMapper;
import com.github.mustfun.generator.model.po.Order;
import com.github.mustfun.generator.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dengzhiyuan on 2017/4/6.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order getOne(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> selectByIdList(List<Integer> ids) {
        return orderMapper.selectByIdList(ids);
    }

}
