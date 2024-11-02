package com.example.mapper;

import com.example.entity.SaleBack;
import java.util.List;

/**
 * 操作sale_back相关数据接口
 */
public interface SaleBackMapper {

    /**
     * 新增
     */
    int insert(SaleBack saleBack);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(SaleBack saleBack);

    /**
     * 根据ID查询
     */
    SaleBack selectById(Integer id);

    /**
     * 查询所有
     */
    List<SaleBack> selectAll(SaleBack saleBack);

}