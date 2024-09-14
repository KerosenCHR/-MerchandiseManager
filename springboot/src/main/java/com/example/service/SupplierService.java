package com.example.service;

import com.example.entity.Supplier;
import com.example.mapper.SupplierMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 供应商表业务处理
 **/
@Service
public class SupplierService {

    @Resource
    private SupplierMapper supplierMapper;

    /**
     * 新增
     */
    public void add(Supplier supplier) {
        supplierMapper.insert(supplier);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        supplierMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            supplierMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Supplier supplier) {
        supplierMapper.updateById(supplier);
    }

    /**
     * 根据ID查询
     */
    public Supplier selectById(Integer id) {
        return supplierMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Supplier> selectAll(Supplier supplier) {
        return supplierMapper.selectAll(supplier);
    }

    /**
     * 分页查询
     */
    public PageInfo<Supplier> selectPage(Supplier supplier, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Supplier> list = supplierMapper.selectAll(supplier);
        return PageInfo.of(list);
    }

}