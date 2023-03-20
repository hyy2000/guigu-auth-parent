package com.atguigu.system.service;

public interface LoginLogService {
    void recordLoginLog(String username,String idAddr,Integer status,String msg);
}
