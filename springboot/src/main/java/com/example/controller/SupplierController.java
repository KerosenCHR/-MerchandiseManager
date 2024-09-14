package com.example.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Supplier;
import com.example.service.SupplierService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 供应商表前端操作接口
 **/
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Resource
    private SupplierService supplierService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Supplier supplier) {
        supplierService.add(supplier);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        supplierService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        supplierService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Supplier supplier) {
        supplierService.updateById(supplier);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Supplier supplier = supplierService.selectById(id);
        return Result.success(supplier);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Supplier supplier) {
        List<Supplier> list = supplierService.selectAll(supplier);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Supplier supplier,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Supplier> page = supplierService.selectPage(supplier, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 批量导出数据
     */
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);

        List<Supplier> list = supplierService.selectAll(null);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("供应商信息表", "UTF-8") + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.flush();
        writer.close();
        outputStream.close();
    }

    /**
     * 批量导入
     */
    @PostMapping("/import")
    public Result importData(MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<Supplier> list = reader.readAll(Supplier.class);
        // 写入数据到数据库
        try {
            for (Supplier supplier : list) {
                supplierService.add(supplier);
            }
        } catch (Exception e) {
            return Result.error(ResultCodeEnum.DATA_IMPORT_ERROR);
        }
        return Result.success();
    }

}