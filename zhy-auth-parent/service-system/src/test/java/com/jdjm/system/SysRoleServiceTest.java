package com.jdjm.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdjm.model.system.SysRole;
import com.jdjm.system.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysRoleServiceTest {


    @Autowired
    SysRoleService sysRoleService;


    @Test
    public void testSelect(){
        List<SysRole> list = sysRoleService.list();
        for(SysRole sysRole : list){
            System.out.println(sysRole);
        }
    }


    @Test
    public void testInsert(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("角色管理员");
        sysRole.setRoleCode("role");
        sysRole.setDescription("角色管理员");

        boolean result = sysRoleService.save(sysRole);
        System.out.println(result); //成功还是失败
    }

    @Test
    public void testUpdateById(){
        SysRole role = sysRoleService.getById("1");
        System.out.println(role);
        role.setDescription("角色管理员zhy");
        boolean result = sysRoleService.updateById(role);
        System.out.println(result);

    }


    @Test
    public void testDel(){
        sysRoleService.removeById(2);
        sysRoleService.removeById("8");
    }


    @Test
    public void select(){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_code","SYSTEM");
        List<SysRole> res = sysRoleService.list(wrapper);
        for(SysRole sysRole:res){
            System.out.println(sysRole);
        }
    }
}
