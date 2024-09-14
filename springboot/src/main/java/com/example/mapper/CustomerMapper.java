package com.example.mapper;

import com.example.entity.Customer;
import java.util.List;

/**
 * 操作customer相关数据接口
 */
public interface CustomerMapper {

    /**
     * 新增
     */
    int insert(Customer customer);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Customer customer);

    /**
     * 根据ID查询
     */
    Customer selectById(Integer id);

    /**
     * 查询所有
     */
    List<Customer> selectAll(Customer customer);

}