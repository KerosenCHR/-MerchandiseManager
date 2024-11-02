package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Logs;
import com.example.mapper.LogsMapper;
import com.example.utils.IpUtils;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 业务处理
 **/
@Service
public class LogsService implements InitializingBean {

    @Resource
    private LogsMapper logsMapper;
    @Resource
    HttpServletRequest request;

    private static LogsMapper staticLogsMapper;

    private static HttpServletRequest staticHttpServletRequest;


    /**
     * 记录日志
     */
    public static void recordLog( String operation, String type, String username) {
        Logs logs = new Logs();
        logs.setOperation(operation);
        logs.setType(type);
        logs.setTime(DateUtil.now());
        logs.setIp(IpUtils.getIpAddr(staticHttpServletRequest));
        logs.setUsername(username);
        staticLogsMapper.insert(logs);
    }

    /**
     * 新增
     */
    public void add(Logs logs) {
        logsMapper.insert(logs);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        logsMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            logsMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Logs logs) {
        logsMapper.updateById(logs);
    }

    /**
     * 根据ID查询
     */
    public Logs selectById(Integer id) {
        return logsMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Logs> selectAll(Logs logs) {
        return logsMapper.selectAll(logs);
    }

    /**
     * 分页查询
     */
    public PageInfo<Logs> selectPage(Logs logs, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Logs> list = logsMapper.selectAll(logs);
        return PageInfo.of(list);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        staticLogsMapper = this.logsMapper;
        staticHttpServletRequest = this.request;
    }
}