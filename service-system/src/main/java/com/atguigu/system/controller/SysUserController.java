package com.atguigu.system.controller;

import com.atguigu.common.helper.JwtHelper;
import com.atguigu.common.result.Result;
import com.atguigu.common.util.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.mapper.SysUserMapper;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @PostMapping("/save")
    public Result save(@RequestBody SysUser user){
        user.setPassword(MD5.encrypt(user.getPassword()));
        sysUserService.save(user);
        return Result.ok();
    }

    @DeleteMapping("/remove/{id}")
    public Result deleteById(@PathVariable Long id){
        sysUserService.removeById(id);
        return Result.ok();
    }
    @PutMapping("/update")
    public Result updateById(@RequestBody SysUser user){
        sysUserService.updateById(user);
        return Result.ok();
    }
    @GetMapping("/get/{id}")
    public Result getUserById(@PathVariable Long id){
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }
    @GetMapping("/{page}/{limit}")
    public Result getPageList(@PathVariable Long page, @PathVariable Long limit, SysUserQueryVo sysUserQueryVo){
        Page<SysUser> pageParam = new Page<>(page, limit);
        IPage<SysUser> iPage =  sysUserService.selectPage(pageParam,sysUserQueryVo);
        return Result.ok(iPage);
    }

    @PutMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,@PathVariable Integer status){
        sysUserService.updateStatus(id,status);
        return Result.ok();
    }

}
