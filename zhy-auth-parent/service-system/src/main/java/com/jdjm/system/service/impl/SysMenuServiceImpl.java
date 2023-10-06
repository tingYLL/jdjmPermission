package com.jdjm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jdjm.common.result.Result;
import com.jdjm.model.system.SysMenu;
import com.jdjm.model.system.SysRoleMenu;
import com.jdjm.model.vo.AssignMenuVo;
import com.jdjm.model.vo.RouterVo;
import com.jdjm.system.mapper.SysMenuMapper;
import com.jdjm.system.mapper.SysRoleMenuMapper;
import com.jdjm.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdjm.system.service.SysRoleMenuService;
import com.jdjm.system.utils.MenuHelper;
import com.jdjm.system.utils.RouterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Override
    public Result removeMenuById(String id) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper();
        wrapper.eq(SysMenu::getParentId,id);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            return Result.fail("请先删除子菜单");
        }

        baseMapper.deleteById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Integer updateStatus(String id, Integer status) {
        SysMenu sysMenu = baseMapper.selectById(id);
        sysMenu.setStatus(status);
        return baseMapper.updateById(sysMenu);
    }

    @Override
    public List<SysMenu> findMenuOfRole(String roleId) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper();
        wrapper.eq(SysMenu::getStatus,1);
        List<SysMenu> sysMenuList = baseMapper.selectList(wrapper);

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
       return  MenuHelper.buildTree(sysMenuList);
    }

    @Override
    public Result assignMenu(AssignMenuVo assignMenuVo) {
        //删除之前已经为该角色分配的菜单
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper();
        wrapper.eq(SysRoleMenu::getRoleId, assignMenuVo.getRoleId());
        sysRoleMenuMapper.delete(wrapper);

        List<String> menuIdList = assignMenuVo.getMenuIdList();
        List<SysRoleMenu> list = new ArrayList<>();
        for (String s : menuIdList) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
            sysRoleMenu.setMenuId(s);
            list.add(sysRoleMenu);
        }
        boolean res = sysRoleMenuService.saveBatch(list);
        return Result.ok();
    }

    @Override
    public List<RouterVo> getUserMenuList(String id) {
        List<SysMenu> sysMenuList = null;
        //如果是超级管理员 直接查询全部
        if("1".equals(id)){
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper();
            wrapper.eq(SysMenu::getStatus,1).orderByDesc(SysMenu::getSortValue);
            sysMenuList = baseMapper.selectList(wrapper);
        }else{
            sysMenuList = baseMapper.getUserMenuList(id);
        }
        List<SysMenu> sysMenuList1 = MenuHelper.buildTree(sysMenuList);
        return RouterHelper.buildRouters(sysMenuList1);
    }

    @Override
    public List<String> getButtonList(String id) {
        List<SysMenu> sysMenuList = null;
        //如果是超级管理员 直接查询全部
        if("1".equals(id)){
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper();
            wrapper.eq(SysMenu::getStatus,1).orderByDesc(SysMenu::getSortValue);
            sysMenuList = baseMapper.selectList(wrapper);
        }else{
            sysMenuList = baseMapper.getUserMenuList(id);
        }

        List<String> collect = sysMenuList.stream().filter(x -> x.getType() == 2).map(x -> x.getPerms()).collect(Collectors.toList());
        return collect;
    }
}
