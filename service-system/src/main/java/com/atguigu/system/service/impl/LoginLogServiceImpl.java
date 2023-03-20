package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysLoginLog;
import com.atguigu.system.mapper.SysLoginLogMapper;
import com.atguigu.system.service.LoginLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional

public class LoginLogServiceImpl implements LoginLogService {
    @Resource
    private SysLoginLogMapper sysLoginLogMapper;
    @Override
    public void recordLoginLog(String username, String ipAddr, Integer status, String msg) {
        //创建SysLoginLog对象
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setIpaddr(ipAddr);
        sysLoginLog.setMsg(msg);
        sysLoginLog.setStatus(status);
        sysLoginLogMapper.insert(sysLoginLog);

    }
}
