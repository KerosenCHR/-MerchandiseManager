package com.example.controller;

import com.example.common.Result;
import com.example.entity.Back;
import com.example.service.BackService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 商品退货前端操作接口
 **/
@RestController
@RequestMapping("/back")
public class BackController {

    @Resource
    private BackService backService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Back back) {
        backService.add(back);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        backService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        backService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Back back) {
        backService.updateById(back);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Back back = backService.selectById(id);
        return Result.success(back);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Back back) {
        List<Back> list = backService.selectAll(back);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Back back,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Back> page = backService.selectPage(back, pageNum, pageSize);
        return Result.success(page);
    }

}