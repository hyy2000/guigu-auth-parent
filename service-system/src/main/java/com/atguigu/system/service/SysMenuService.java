package com.atguigu.system.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    //菜单树形数据
    List<SysMenu> findNodes();

    //根据角色获取菜单
    List<SysMenu> findSysMenuByRoleId(Long roleId);

    //保存角色菜单
    void doAssign(AssginMenuVo assginMenuVo);
}
