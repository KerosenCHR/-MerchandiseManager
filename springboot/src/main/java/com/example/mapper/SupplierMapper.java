package com.example.mapper;

import com.example.entity.Supplier;
import java.util.List;

/**
 * 操作supplier相关数据接口+
 * 
 */
public interface   SupplierMapper {

    /**
     * 新增
     */
    int insert(Supplier supplier);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Supplier supplier);

    /**
     * 根据ID查询
     */
    Supplier selectById(Integer id);

    /**
     * 查询所有
     */
    List<Supplier> selectAll(Supplier supplier);

}