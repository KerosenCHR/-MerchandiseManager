package com.example.mapper;

import com.example.entity.Logs;
import java.util.List;

/**
 * 操作logs相关数据接口
 */
public interface LogsMapper {

    /**
     * 新增
     */
    int insert(Logs logs);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Logs logs);

    /**
     * 根据ID查询
     */
    Logs selectById(Integer id);

    /**
     * 查询所有
     */
    List<Logs> selectAll(Logs logs);

}