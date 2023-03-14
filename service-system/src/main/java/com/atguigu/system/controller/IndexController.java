package com.atguigu.system.controller;

import com.atguigu.common.helper.JwtHelper;
import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.common.util.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.LoginVo;
import com.atguigu.system.exception.MyException;
import com.atguigu.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户登录")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
//        根据username查询数据
        SysUser sysUser =  sysUserService.getByUsername(loginVo.getUsername());
//        如果数据为空，抛出异常
        if(sysUser == null){
            throw new MyException(ResultCodeEnum.ACCOUNT_ERROR);
        }
        //判断密码是否一致
        if (! MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())){
            throw new MyException(ResultCodeEnum.PASSWORD_ERROR);
        }
        //判断用户是否被停用
        if (sysUser.getStatus() == 0){
            throw new MyException(ResultCodeEnum.ACCOUNT_STOP);
        }
        Map<String, Object> map = new HashMap<>();
        //根据userId和userName生成token字符串，放入map集合中，返回
        map.put("token", JwtHelper.createToken(sysUser.getId(),sysUser.getUsername()));
//        Map<String, Object> map = new HashMap<>();
//        map.put("token","admin-token");
        return Result.ok(map);
    }
    @GetMapping("/info")
    public Result info(HttpServletRequest request){
        //通过HttpServletRequest（不是HttpRequest）获取请求头token
        String token = request.getHeader("token");
        //通过token获得userid
        Long userId = JwtHelper.getUserId(token);
        //通过userid获得用户信息（基本信息和菜单权限和按钮权限数据
        Map<String ,Object> map =  sysUserService.getUserInfoByUserId(userId);

//        Map<String, Object> map = new HashMap<>();
//        map.put("roles","[admin]");
//        map.put("name","JayChou");
//        map.put("introduction","一路向北离开有你的季节");
//        map.put("avatar","https://img.zcool.cn/community/01d9c8584514bea801219c77b3f1ee.jpg@1280w_1l_2o_100sh.jpg");
        return Result.ok(map);
    }
    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }
}
