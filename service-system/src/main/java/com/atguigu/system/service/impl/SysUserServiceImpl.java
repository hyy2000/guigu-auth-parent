package com.atguigu.system.service.impl;

import com.atguigu.common.helper.MenuHelper;
import com.atguigu.common.helper.RouterHelper;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.RouterVo;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.mapper.*;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public Map<String, Object> getUserInfoByUserId(Long userId) {
        //    根据用户id获取用户登录信息
        SysUser sysUser = sysUserMapper.selectById(userId);
        //根据用户id获取权限菜单的方法
        //获取指定用户的菜单信息
        List<SysMenu> userMenuList = this.getUserMenusByUserId(userId);
        List<SysMenu> menusTree = MenuHelper.buildTree(userMenuList);
        List<RouterVo> routerVos = RouterHelper.buildRouters(menusTree);
        //获取用户的按钮权限信息
        List<String> userButtonList = this.getUserBtnPermsByUserId(userId);

        Map<String,Object> map = new HashMap<>();
        map.put("name",sysUser.getUsername());
        map.put("avatar",sysUser.getHeadUrl());
        map.put("routers",routerVos);
        map.put("buttons",userButtonList);
        return map;
    }

    //TODO
    private List<String> getUserBtnPermsByUserId(Long userId) {
        //getUserMenusByUserId(userId)可以获得菜单列表，取其中的perms
        return null;
    }

    //TODO
    private List<SysMenu> getUserMenusByUserId(Long userId) {
        List<SysMenu> menuList = null;
        if (userId==1){
            //超级管理员
            //获取所有的菜单权限，查菜单表status=1的
            menuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        } else {
            //根据userId查询指定的权限
            menuList = sysMenuMapper.selectMenuListByUserId(userId);
        }
        return menuList;
    }

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username",username));
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setStatus(status);
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo) {
        return sysUserMapper.selectPage(pageParam,sysUserQueryVo);
    }
}
