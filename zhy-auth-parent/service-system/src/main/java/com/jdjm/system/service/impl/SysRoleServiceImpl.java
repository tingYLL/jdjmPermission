package com.jdjm.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdjm.common.result.Result;
import com.jdjm.model.system.SysRole;
import com.jdjm.model.system.SysUserRole;
import com.jdjm.model.vo.AssignRoleVo;
import com.jdjm.model.vo.SysRoleQueryVo;
import com.jdjm.system.mapper.SysRoleMapper;
import com.jdjm.system.mapper.SysUserRoleMapper;
import com.jdjm.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public IPage<SysRole> selectPage(Page<SysRole> sysRolePage, SysRoleQueryVo sysRoleQueryVo) {
       return baseMapper.selectPage(sysRolePage,sysRoleQueryVo);
    }

    @Override
    public Result assignRole(AssignRoleVo assignRoleVo) {
        //清空之前已经为该用户分配的角色
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, assignRoleVo.getUserId());
        int count = sysUserRoleMapper.delete(wrapper);

        List<String> roleIdList = assignRoleVo.getRoleIdList();
        for (String s : roleIdList) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(assignRoleVo.getUserId());
            sysUserRole.setRoleId(s);

            sysUserRoleMapper.insert(sysUserRole);
        }
        return Result.ok();
    }
}
