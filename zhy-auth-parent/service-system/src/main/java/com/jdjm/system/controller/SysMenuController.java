package com.jdjm.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jdjm.common.result.Result;
import com.jdjm.model.system.SysMenu;
import com.jdjm.model.vo.AssignMenuVo;
import com.jdjm.system.service.SysMenuService;
import com.jdjm.system.utils.MenuHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author ZhengHongyi
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("查找菜单（树形）")
    @GetMapping("/treeListMenu")
    public Result treeList(){
        List<SysMenu> list = sysMenuService.list();
        List<SysMenu> result = MenuHelper.buildTree(list);
        return Result.ok(result);
    }

    @ApiOperation("根据id查找菜单")
    @GetMapping("/listMenuById/{id}")
    public Result listMenuById(@PathVariable String id){
        SysMenu sysMenu = sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }

    @ApiOperation("添加菜单")
    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody SysMenu sysMenu){
        boolean res = sysMenuService.save(sysMenu);
        if(res) return Result.ok();
        else return Result.fail();
    }

    @ApiOperation("更新菜单")
    @PostMapping("/updateMenu")
    public Result updateMenu(@RequestBody SysMenu sysMenu){
        boolean res = sysMenuService.updateById(sysMenu);
        if(res) return Result.ok();
        else return Result.fail();
    }


    //删除菜单的时候不能简单地直接删除 还需要判断该菜单下是否还有子菜单
    @ApiOperation("删除菜单")
    @DeleteMapping("/delMenu/{id}")
    public Result delMenu(@PathVariable String id){
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper();
        wrapper.eq(SysMenu::getParentId,id);
        Result result = sysMenuService.removeMenuById(id);
        return result;
    }


    //更新状态
    @ApiOperation("更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id,@PathVariable Integer status ){
        Integer res = sysMenuService.updateStatus(id,status);
        return res>0?Result.ok("更新状态成功"):Result.fail("更新状态失败");
    }


    //根据角色id显示当前已为该角色分配的菜单
    @ApiOperation("显示角色已分配的菜单")
    @GetMapping("/findMenu/{id}")
    public Result findMenuOfRole(@PathVariable("id") String roleId){
        List<SysMenu>  list = sysMenuService.findMenuOfRole(roleId);
        return Result.ok(list);
    }

    //为角色分配菜单
    @ApiOperation("为角色分配菜单")
    @PostMapping("/assignMenu")
    public Result assignMenu(@RequestBody AssignMenuVo assignMenuVo){
        return sysMenuService.assignMenu(assignMenuVo);
    }
}

