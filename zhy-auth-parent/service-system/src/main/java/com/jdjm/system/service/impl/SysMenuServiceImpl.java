package com.jdjm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jdjm.common.result.Result;
import com.jdjm.model.system.SysMenu;
import com.jdjm.model.system.SysRoleMenu;
import com.jdjm.system.mapper.SysMenuMapper;
import com.jdjm.system.mapper.SysRoleMenuMapper;
import com.jdjm.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdjm.system.utils.MainHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author ZhengHongyi
 * @since 2023-09-17
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Result removeMenuById(String id) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper();
        wrapper.eq(SysMenu::getParentId,id);
        Integer count = sysMenuMapper.selectCount(wrapper);
        if(count > 0){
            return Result.fail("请先删除子菜单");
        }

        sysMenuMapper.deleteById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Integer updateStatus(String id, Integer status) {
        SysMenu sysMenu = sysMenuMapper.selectById(id);
        sysMenu.setStatus(status);
        return sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public List<SysMenu> findMenuOfRole(String roleId) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper();
        wrapper.eq(SysMenu::getStatus,1);
        List<SysMenu> sysMenuList = sysMenuMapper.selectList(wrapper);

        //获得当前角色下的所有菜单id
        LambdaQueryWrapper<SysRoleMenu> wrapper1 = new LambdaQueryWrapper();
        wrapper1.eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(wrapper1);
        List<String> menuIdOfCurrentRole = sysRoleMenuList.stream().map(x -> x.getMenuId()).collect(Collectors.toList());

        for (SysMenu sysMenu : sysMenuList) {
            if(menuIdOfCurrentRole.contains(sysMenu.getId())){
                sysMenu.setSelect(true);
            }else{
                sysMenu.setSelect(false);
            }
        }
       return  MainHelper.buildTree(sysMenuList);
    }
}
