package com.atguigu.system.test;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TestSysRoleMapper {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Test
    public void testFindAll(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        sysRoles.forEach(System.out::println);
    }
    @Test
    public void testSelectById(){
        SysRole sysRole = sysRoleMapper.selectById(1);
        System.out.println("sysRole = " + sysRole);
    }
    @Test
    public void testInsert(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试1");
        sysRole.setRoleCode("role");
        sysRole.setDescription("测试1");
        int insert = sysRoleMapper.insert(sysRole);
        System.out.println(insert);
    }
    @Test
    public void testUpdateById(){
        SysRole sysRole = new SysRole();
        sysRole.setId(9L);
        sysRole.setRoleName("测试修改");
        int i = sysRoleMapper.updateById(sysRole);
        System.out.println(i);
    }
    @Test
    public void testDeleteById(){
        int i = sysRoleMapper.deleteById(9L);
        System.out.println(i);
    }
    @Test
    public void testDeleteByBatchIds(){
        int i = sysRoleMapper.deleteBatchIds(Arrays.asList(8, 9, 1632355140763693058L));

    }
}
