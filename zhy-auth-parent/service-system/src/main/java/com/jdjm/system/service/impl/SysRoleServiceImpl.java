package com.jdjm.system.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdjm.model.system.SysRole;
import com.jdjm.model.vo.SysRoleQueryVo;
import com.jdjm.system.mapper.SysRoleMapper;
import com.jdjm.system.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {
    @Override
    public IPage<SysRole> selectPage(Page<SysRole> sysRolePage, SysRoleQueryVo sysRoleQueryVo) {
       return baseMapper.selectPage(sysRolePage,sysRoleQueryVo);
    }
}
