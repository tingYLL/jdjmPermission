package com.jdjm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jdjm.common.result.Result;
import com.jdjm.model.system.SysMenu;
import com.jdjm.system.mapper.SysMenuMapper;
import com.jdjm.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
