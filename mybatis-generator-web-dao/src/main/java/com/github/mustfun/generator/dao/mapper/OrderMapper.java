package com.github.mustfun.generator.dao.mapper;

import com.github.mustfun.generator.model.po.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper //这个注解会被注册为一个实现类，但是需要sqlSessionFactory，但是这里的sqlSessionFactory我们没有给他指认，所以会是默认的，我们需要给他分库后的
public interface OrderMapper {

    Order selectByPrimaryKey(@Param("id") Integer id);

    List<Order> selectByIdList(List<Integer> ids);


}