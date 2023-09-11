package com.jdjm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdjm.model.system.SysRole;
import com.jdjm.model.system.SysUser;
import com.jdjm.model.system.SysUserRole;
import com.jdjm.system.mapper.SysRoleMapper;
import com.jdjm.system.mapper.SysUserRoleMapper;
import com.jdjm.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Map<String, Object> queryRoleOfUserAndAll(String id) {
        //所有角色
        List<SysRole> sysRoleList = sysRoleMapper.selectList(null);
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,id);
        List<SysUserRole> sysUserRoles = baseMapper.selectList(wrapper);
        List<String> list = sysUserRoles.stream().map(x -> x.getRoleId()).distinct().collect(Collectors.toList());
        Map<String,Object> map= new HashMap<>();
        map.put("allRole",sysRoleList);
        map.put("roleOfUser",list);
        return map;
    }
}
