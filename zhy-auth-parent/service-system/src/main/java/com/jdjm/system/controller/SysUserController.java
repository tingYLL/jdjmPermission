package com.jdjm.system.controller;


import com.jdjm.common.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api("用户管理")
@RequestMapping("/admin/system/user")
public class SysUserController {

//    {"code":20000,"data":{"token":"admin-token"}}
    @PostMapping("/login")
    public Result userLogin(){
        Map<String,Object> map = new HashMap();
        map.put("token","admin-token zhy");
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
}
