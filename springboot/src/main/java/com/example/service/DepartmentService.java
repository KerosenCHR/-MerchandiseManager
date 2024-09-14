package com.example.service;

import com.example.entity.Department;
import com.example.mapper.DepartmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    /**
     * 新增
     */
    public void add(Department department) {
        departmentMapper.insert(department);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        departmentMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            departmentMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Department department) {
        departmentMapper.updateById(department);
    }

    /**
     * 根据ID查询
     */
    public Department selectById(Integer id) {
        return departmentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Department> selectAll(Department department) {
        return departmentMapper.selectAll(department);
    }

    /**
     * 分页查询
     */
    public PageInfo<Department> selectPage(Department department, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Department> list = departmentMapper.selectAll(department);
        return PageInfo.of(list);
    }

    /**
     * 查询树形结构
     */
    public List<Department> selectTree() {
        Department params = new Department();
        params.setLevel(1);  // 先查询出一级节点
        List<Department> level1DepartmentList = this.selectAll(params);
        for (Department level1 : level1DepartmentList) {
            Integer level1Id = level1.getId();
            Department params1 = new Department();
            params1.setPid(level1Id);  // 再查询2级节点
            List<Department> leve2DepartmentList = this.selectAll(params1);
            level1.setChildren(leve2DepartmentList);  // 设置一级节点的子节点
            for (Department level2 : leve2DepartmentList) {
                Integer level2Id = level2.getId();
                Department params2 = new Department();
                params2.setPid(level2Id);  // 查询三级节点
                List<Department> leve3DepartmentList = this.selectAll(params2);
                level2.setChildren(leve3DepartmentList);   // 设置儿级节点的子节点
            }
        }
        return level1DepartmentList;
    }
}