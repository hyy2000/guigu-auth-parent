package com.atguigu.system.service.impl;

import com.atguigu.common.helper.MenuHelper;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.atguigu.system.exception.MyException;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.mapper.SysRoleMenuMapper;
import com.atguigu.system.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //1. 根据角色id删除菜单权限
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",assginMenuVo.getRoleId());
        sysRoleMenuMapper.delete(wrapper);
        //2. 遍历菜单id列表，添加已选择的
        //2.1 获得menuIdList
        List<Long> menuIdList = assginMenuVo.getMenuIdList();
        //2.2 遍历 ,新增
        for (Long menuId : menuIdList
                ) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenu.setMenuId(menuId);
//            sysRoleMenu.setRoleId(menuId);
            //需要传入对象
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //1.获取所有菜单 status=1
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        List<SysMenu> menus = sysMenuMapper.selectList(wrapper);
        //2.根据角色id查询菜单列表
        QueryWrapper<SysRoleMenu> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("role_id",roleId);
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectList(wrapper1);
        //3.从第二步查询列表中获得分配所有菜单的id
        List<Long> roleMenuList = new ArrayList<>();
        sysRoleMenus.forEach(sysRoleMenu -> {
            roleMenuList.add(sysRoleMenu.getMenuId());
        });
        //4.数据处理：遍历所有菜单列表 ,isSelect
        //拿着分配菜单和所有菜单对比，相同的让isSelect = true
        for (SysMenu sysMenu: menus
             ) {
            //sysMenu.setSelect(roleMenuList.contains(sysMenu.getId()));
            if (roleMenuList.contains(sysMenu.getId())){
                //该权限已经被分配
                sysMenu.setSelect(true);
            }else {
                sysMenu.setSelect(false);
            }
        }
        //5.转换成树形结构
        List<SysMenu> menuReturn = MenuHelper.buildTree(menus);
        return menuReturn;
    }

    @Override
    public List<SysMenu> findNodes() {
        //1.获取所有菜单
        List<SysMenu> sysMenus = sysMenuMapper.selectList(null);
        //2.将菜单数据转换为要求格式
        List<SysMenu> menusTree = MenuHelper.buildTree(sysMenus);
        return menusTree;
    }

    //重写removeById
    //判断是否有下级元素，如果没有，才可以删除，，如果有，则抛出异常
    @Override
    public boolean removeById(Serializable id) {
        //1.判断指定id的菜单是否有下级元素
        //id = parent_id
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        //2.如果有下级元素，抛出异常，如果没有下级元素，执行删除
        Integer i = sysMenuMapper.selectCount(wrapper);
        if (i>0){
            throw new MyException(ResultCodeEnum.NODE_ERROR);
        } else {
            sysMenuMapper.deleteById(id);
            return false;
        }
    }
}
