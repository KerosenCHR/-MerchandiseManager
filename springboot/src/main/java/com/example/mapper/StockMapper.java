package com.example.mapper;

import com.example.entity.Stock;
import java.util.List;

/**
 * 操作stock相关数据接口
 */
public interface StockMapper {

    /**
     * 新增
     */
    int insert(Stock stock);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Stock stock);

    /**
     * 根据ID查询
     */
    Stock selectById(Integer id);

    /**
     * 查询所有
     */
    List<Stock> selectAll(Stock stock);

}