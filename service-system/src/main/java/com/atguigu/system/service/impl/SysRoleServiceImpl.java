package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.model.vo.AssginRoleVo;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.system.mapper.SysRoleMapper;
import com.atguigu.system.mapper.SysUserRoleMapper;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public Map<String, Object> getRolesByUserId(Long userId) {

//        获取所有角色
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
//        获取当前用户已经分配的角色列表,根据用户id查询
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        //sysUserRoles里面包含所有的字段信息，我们只需要role_id
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(wrapper);

        List<Long> userRoleIds = new ArrayList<>();

        for (SysUserRole sysUserRole : sysUserRoles ){
            userRoleIds.add(sysUserRole.getRoleId());
//            userRoleIds.add(sysUserRole.getUserId());
        }
        //        封装到map集合返回
        Map<String,Object> map = new HashMap<>();
        map.put("allRoles",sysRoles);
        map.put("userRoleIds",userRoleIds);
        return map;
    }


    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //删除之前所有的
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",assginRoleVo.getUserId());
        sysUserRoleMapper.delete(wrapper);
        //添加现在的
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        for (Long roleId :roleIdList){
            if (roleId != null) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(assginRoleVo.getUserId());
                //
                sysUserRole.setRoleId(roleId);
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }

    @Override
    public IPage<SysRole> selectPage(Page<SysRole> page1, SysRoleQueryVo roleQueryVo) {
        return sysRoleMapper.selectPage1(page1,roleQueryVo);
    }
}
