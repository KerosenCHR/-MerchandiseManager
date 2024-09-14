package com.example.controller;

import com.example.common.Result;
import com.example.entity.Stock;
import com.example.service.StockService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 商品进货前端操作接口
 **/
@RestController
@RequestMapping("/stock")
public class StockController {

    @Resource
    private StockService stockService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Stock stock) {
        stockService.add(stock);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        stockService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        stockService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Stock stock) {
        stockService.updateById(stock);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Stock stock = stockService.selectById(id);
        return Result.success(stock);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Stock stock) {
        List<Stock> list = stockService.selectAll(stock);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Stock stock,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Stock> page = stockService.selectPage(stock, pageNum, pageSize);
        return Result.success(page);
    }

}