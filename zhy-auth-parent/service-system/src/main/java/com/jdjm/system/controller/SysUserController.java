package com.jdjm.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdjm.common.result.Result;
import com.jdjm.common.utils.JwtHelper;
import com.jdjm.common.utils.MD5;
import com.jdjm.model.system.SysUser;
import com.jdjm.model.vo.LoginVo;
import com.jdjm.model.vo.SysUserQueryVo;
import com.jdjm.system.exception.JdjmException;
import com.jdjm.system.service.SysUserRoleService;
import com.jdjm.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "用户管理")
@RequestMapping("/admin/system/user")
public class SysUserController {

    @Autowired
    private SysUserRoleService sysUserRoleService;


    @Autowired
    private SysUserService sysUserService;

    //返回token
//    {"code":20000,"data":{"token":"admin-token"}}
    @PostMapping("/login")
    public Result userLogin(@RequestBody LoginVo loginVo){
        //判断该用户是否注册过
        SysUser user = sysUserService.queryUser(loginVo.getUsername());
        if(user == null){
            throw new JdjmException(20001,"不存在该用户");
        }

        //判断密码是否正确
        String encrypt = MD5.encrypt(loginVo.getPassword());
        if(!encrypt.equals(user.getPassword())){
            throw new JdjmException(20001,"密码不正确");
        }

        //判断用户的状态是否被禁用
        if(user.getStatus() == 0){
            throw new JdjmException(20001,"用户已被禁用");
        }

       //登录成功 分发token 返回token
        String token = JwtHelper.createToken(user.getId(), user.getUsername());
        Map<String,Object> map = new HashMap();
        map.put("token",token);
        return Result.ok(map);
    }


    @GetMapping("/info")
    public Result userInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        String username = JwtHelper.getUsername(token);
        Map<String,Object> map = sysUserService.queryUserInfo(username);
        return Result.ok(map);
    }


    @GetMapping("/{page}/{limit}")
    @ApiOperation(value = "分页条件查询获取全部")
    public Result listUsers(@PathVariable Integer page, @PathVariable Integer limit,   SysUserQueryVo sysUserQueryVo){
        Page<SysUser> myPage = new Page<SysUser>(page,limit);
        IPage<SysUser> iPage = sysUserService.selectPage(myPage,sysUserQueryVo);
        return Result.ok(iPage);
    }

    @PostMapping("add")
    @ApiOperation(value="添加用户")
    public Result<SysUser> addUser(@RequestBody SysUser sysUser){
        String encrypt = MD5.encrypt(sysUser.getPassword());
        sysUser.setPassword(encrypt);
        boolean res = sysUserService.save(sysUser);
        if(res) return Result.ok();
        else return Result.fail();
    }

    @GetMapping("queryById/{id}")
    @ApiOperation(value="根据id进行查询")
    public Result<SysUser> queryById(@PathVariable Long id){
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @DeleteMapping("deleteUser/{id}")
    @ApiOperation(value="根据id进行删除")
    public Result deleteUser(@PathVariable  String id){
        boolean res = sysUserService.removeById(id);
        if(res) return Result.ok();
        else return Result.fail();
    }

    @PostMapping("updateById")
    @ApiOperation(value="根据id进行更新")
    public Result updateRole(@RequestBody SysUser sysUser){
        boolean res = sysUserService.updateById(sysUser);
        if(res) return Result.ok();
        else return Result.fail();
    }

    @GetMapping("updateStatus/{id}/{status}")
    @ApiOperation(value="修改用户状态")
    public Result updateRoleStatus(@PathVariable  String id,@PathVariable Integer status){
        SysUser sysUser = sysUserService.getById(id);
        sysUser.setStatus(status);
        boolean res = sysUserService.updateById(sysUser);
        if(res) return Result.ok();
        return Result.fail();
    }

    //    根据用户id查询其下的所有角色id 以及所有角色
    @GetMapping("/findRoleOfUser/{uid}")
    @ApiOperation(value="查询用户拥有哪些角色身份以及所有角色列表")
    public Result<Map<String,Object>> queryRoleOfUserAndAll(@PathVariable String uid){
        Map<String, Object> map = sysUserRoleService.queryRoleOfUserAndAll(uid);
        return Result.ok(map);
    }

}
