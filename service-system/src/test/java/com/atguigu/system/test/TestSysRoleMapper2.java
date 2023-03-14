package com.atguigu.system.test;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.mapper.SysRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class TestSysRoleMapper2 {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Test
    public void tesQueryWrapper(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        sysRoles.forEach(System.out::println);
    }
    @Test
    public void tesQueryWrapper2(){
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        sysRoleQueryWrapper.eq("role_name","测试1");
        List<SysRole> sysRoles = sysRoleMapper.selectList(sysRoleQueryWrapper);
        sysRoles.forEach(System.out::println);
    }
    @Test
    public void tesQueryWrapper3(){
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
       sysRoleQueryWrapper.like("role_name","测试");
        List<SysRole> sysRoles = sysRoleMapper.selectList(sysRoleQueryWrapper);
        sysRoles.forEach(System.out::println);
    }
    @Test
    public void tesQueryWrapper4(){
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        sysRoleQueryWrapper.likeRight("role_name","测试");
        List<SysRole> sysRoles = sysRoleMapper.selectList(sysRoleQueryWrapper);
        sysRoles.forEach(System.out::println);
    }
    @Test
    public void tesQueryWrapper5(){
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        sysRoleQueryWrapper.orderByDesc("id");
        sysRoleQueryWrapper.select("id","role_name");
        List<SysRole> sysRoles = sysRoleMapper.selectList(sysRoleQueryWrapper);
        sysRoles.forEach(System.out::println);
    }
}
