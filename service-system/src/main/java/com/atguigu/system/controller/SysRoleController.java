package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.vo.AssginRoleVo;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.system.exception.MyException;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;
import java.util.Map;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "获取全部角色列表")
    @GetMapping("/findAll")
    public Result<List<SysRole>> findAll() {
//        try {
//            int i = 10/0;
//        } catch (Exception e) {
////            e.printStackTrace();
//            throw new MyException(10086);
//        }
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        SysRole role = sysRoleService.getById(id);
        return Result.ok(role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/save")
    public Result save(@RequestBody SysRole role) {
        sysRoleService.save(role);
        return Result.ok();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("/update")
    public Result updateById(@RequestBody SysRole role) {
        sysRoleService.updateById(role);
        return Result.ok();
    }

//    @ApiOperation(value = "删除角色")
//    @PostMapping("/remove/{id}")
//    public Result removeById(@RequestBody Long id) {
//        sysRoleService.removeById(id);
//        return Result.ok();
//    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/remove/{id}")
    public Result removeById(@PathVariable Long id) {
        sysRoleService.removeById(id);
        return Result.ok();
    }


    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRomve(@RequestBody List<Long> idlist) {
        sysRoleService.removeByIds(idlist);
        return Result.ok();
    }

//    @ApiOperation(value = "获取分页列表1")
//    @GetMapping("/{page}/{limit}")
//    public Result index1(
//            @ApiParam(name = "page", value = "当前页码", required = true)
//            @PathVariable Long page,
//            @ApiParam(name = "limit", value = "每页记录数", required = true)
//            @PathVariable Long limit,
//            @ApiParam(name = "roleQueryVo", value = "查询对象", required = false)
//            SysRoleQueryVo roleQueryVo) {
//        Page<SysRole> page1 = new Page<>(page, limit);
//        IPage<SysRole> sysRoleIPage = sysRoleService.selectPage(page1, roleQueryVo);
//
//        return Result.ok(sysRoleIPage);
//    }


    @ApiOperation(value = "获取分页列表2")
    @GetMapping("/{page}/{limit}")
    public Result index2(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "roleQueryVo", value = "查询对象", required = false)
            SysRoleQueryVo roleQueryVo) {

        System.out.println("page = " + page);
        System.out.println("limit = " + limit);

        Page<SysRole> pageHelper = new Page<>(page, limit);
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        if (!StringUtils.isEmpty(roleQueryVo.getRoleName())){
            qw.like("role_name", roleQueryVo.getRoleName());
        }
        Page<SysRole> page1 = sysRoleService.page(pageHelper, qw);
        return Result.ok(page1);
    }


//    @ApiOperation(value = "获取分页列表2")
//    @PostMapping("/{page}/{limit}")
//    public Result index2(
//            @ApiParam(name = "page", value = "当前页码", required = true)
//            @PathVariable Long page,
//            @ApiParam(name = "limit", value = "每页记录数", required = true)
//            @PathVariable Long limit,
//            @ApiParam(name = "roleQueryVo", value = "查询对象", required = false)
//            @RequestBody SysRoleQueryVo roleQueryVo) {
//        Page<SysRole> pageHelper = new Page<>(page, limit);
//        QueryWrapper<SysRole> qw = new QueryWrapper<>();
//        if (!StringUtils.isEmpty(roleQueryVo.getRoleName())){
//            qw.like("role_name", roleQueryVo.getRoleName());
//        }
//        Page<SysRole> page1 = sysRoleService.page(pageHelper, qw);
//        return Result.ok(page1);
//    }

    /**
     * 分配权限之前的准备工作，提供表单的回显数据
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId){
        /*
        需要返回两个数据
        角色列表
        当前用户已经分配的角色列表
         */
        Map<String,Object> roleMap = sysRoleService.getRolesByUserId(userId);
        return Result.ok(roleMap);
    }
    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo){
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }



}
