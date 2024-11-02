package com.example.service;

import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Account;
import com.example.entity.Goods;
import com.example.entity.SaleBack;
import com.example.exception.CustomException;
import com.example.mapper.SaleBackMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 商品销售业务处理
 **/
@Service
public class SaleBackService {

    @Resource
    private SaleBackMapper saleBackMapper;

    /**
     * 新增
     */
    public void add(SaleBack saleBack) {
        saleBackMapper.insert(saleBack);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        saleBackMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            saleBackMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(SaleBack saleBack) {
        saleBackMapper.updateById(saleBack);
    }

    /**
     * 根据ID查询
     */
    public SaleBack selectById(Integer id) {
        return saleBackMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<SaleBack> selectAll(SaleBack saleBack) {
        return saleBackMapper.selectAll(saleBack);
    }

    /**
     * 分页查询
     */
    public PageInfo<SaleBack> selectPage(SaleBack saleBack, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SaleBack> list = saleBackMapper.selectAll(saleBack);
        return PageInfo.of(list);
    }

}