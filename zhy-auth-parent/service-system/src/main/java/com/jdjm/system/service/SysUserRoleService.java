package com.jdjm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdjm.model.system.SysUserRole;

import java.util.Map;

public interface SysUserRoleService extends IService<SysUserRole> {

    //    根据用户id查询其下的所有角色id 以及所有角色
    public Map<String,Object> queryRoleOfUserAndAll(String id);
}
