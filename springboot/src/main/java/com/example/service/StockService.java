package com.example.service;

import com.example.entity.Account;
import com.example.entity.Goods;
import com.example.entity.Stock;
import com.example.mapper.StockMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品进货业务处理
 **/
@Service
public class StockService {

    @Resource
    private StockMapper stockMapper;

    @Resource
    private GoodsService goodsService;

    /**
     * 新增
     */
    @Transactional
    public void add(Stock stock) {
        Account currentUser = TokenUtils.getCurrentUser();
        stock.setUser(currentUser.getName());
        stock.setTotal(stock.getPrice() * stock.getNum());

        //查询进货的商品信息
        Goods goods = goodsService.selectById(stock.getGoodsId());
        goods.setNum(goods.getNum() + stock.getNum());
        goodsService.updateById(goods);

        stockMapper.insert(stock);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        stockMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            stockMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Stock stock) {
        stock.setTotal(stock.getPrice() * stock.getNum());
        stockMapper.updateById(stock);
    }

    /**
     * 根据ID查询
     */
    public Stock selectById(Integer id) {
        return stockMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Stock> selectAll(Stock stock) {
        return stockMapper.selectAll(stock);
    }

    /**
     * 分页查询
     */
    public PageInfo<Stock> selectPage(Stock stock, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Stock> list = stockMapper.selectAll(stock);
        return PageInfo.of(list);
    }

}