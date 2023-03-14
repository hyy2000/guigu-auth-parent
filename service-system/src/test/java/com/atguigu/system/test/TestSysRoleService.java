package com.atguigu.system.test;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestSysRoleService {
    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void testSysRoleService(){
        List<SysRole> list = sysRoleService.list();
        list.forEach(sysRole -> System.out.println());
    }
    @Test
    public void testSysRoleSave(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试测试");
        sysRole.setRoleCode("test");
        sysRoleService.save(sysRole);
    }
}
