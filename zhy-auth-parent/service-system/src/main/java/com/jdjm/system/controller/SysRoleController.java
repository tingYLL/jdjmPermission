package com.jdjm.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdjm.common.result.Result;
import com.jdjm.model.system.SysRole;
import com.jdjm.model.vo.SysRoleQueryVo;
import com.jdjm.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "获取全部角色列表")
    @GetMapping("/findAll")
    public Result<List<SysRole>> findAll() {
        List<SysRole> roleList = sysRoleService.list();
        return Result.ok(roleList);
    }

    @DeleteMapping("/remove/{id}")
    public boolean removeRole(@PathVariable Long id){
        boolean res = sysRoleService.removeById(id);
        return res;
    }


    @ApiOperation("分页条件查询")
    @GetMapping("/page")
    public Result<IPage<SysRole>> queryPage(@RequestParam(name = "page", defaultValue="1") Integer page,
                                            @RequestParam(name = "limit",defaultValue="") Integer limit,
                                            SysRoleQueryVo sysRoleQueryVo){
        Page<SysRole> sysRolePage = new Page<>(page, limit);
        IPage<SysRole> iPage = sysRoleService.selectPage(sysRolePage,sysRoleQueryVo);
        return Result.ok(iPage);
    }
}
