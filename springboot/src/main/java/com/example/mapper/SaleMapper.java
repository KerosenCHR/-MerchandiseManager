package com.example.mapper;

import com.example.entity.Sale;
import java.util.List;

/**
 * 操作sale相关数据接口
 */
public interface SaleMapper {

    /**
     * 新增
     */
    int insert(Sale sale);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Sale sale);

    /**
     * 根据ID查询
     */
    Sale selectById(Integer id);

    /**
     * 查询所有
     */
    List<Sale> selectAll(Sale sale);

}