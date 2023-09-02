package com.jdjm.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdjm.common.result.Result;
import com.jdjm.model.system.SysUser;
import com.jdjm.model.vo.SysUserQueryVo;
import com.jdjm.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "用户管理")
@RequestMapping("/admin/system/user")
public class SysUserController {


    @Autowired
    private SysUserService sysUserService;

    //返回token
//    {"code":20000,"data":{"token":"admin-token"}}
    @PostMapping("/login")
    public Result userLogin(){
        Map<String,Object> map = new HashMap();
        map.put("token","admin-tokenzhy");
        return Result.ok(map);
    }

//    {
//        "code": 20000,
//            "data": {
//        "roles": [
//        "admin"
//    ],
//        "introduction": "I am a super administrator",
//                "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
//                "name": "Super Admin"
//    }
//    }
    @GetMapping("/info")
    public Result userInfo(){
        String [] arr = {"admin"};
        Map<String,Object> map = new HashMap<>();
        map.put("roles",arr);
        map.put("introduction","I am a super administrator zhy");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","Super Admin");
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


}
