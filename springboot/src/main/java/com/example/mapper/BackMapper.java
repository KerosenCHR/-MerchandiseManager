package com.example.mapper;

import com.example.entity.Back;
import java.util.List;

/**
 * 操作back相关数据接口
 */
public interface BackMapper {

    /**
     * 新增
     */
    int insert(Back back);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Back back);

    /**
     * 根据ID查询
     */
    Back selectById(Integer id);

    /**
     * 查询所有
     */
    List<Back> selectAll(Back back);

}