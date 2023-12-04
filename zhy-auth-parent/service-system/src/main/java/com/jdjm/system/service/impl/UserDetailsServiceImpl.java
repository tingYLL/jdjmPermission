package com.jdjm.system.service.impl;

import com.jdjm.model.system.SysUser;
import com.jdjm.system.service.SysUserService;
import com.jdjm.zhy.system.custom.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.queryUser(username);
        if(sysUser == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        if(sysUser.getStatus().intValue() == 0){
            throw new RuntimeException("用户被禁用了");
        }
        return new CustomUser(sysUser, Collections.emptyList());
    }
}
