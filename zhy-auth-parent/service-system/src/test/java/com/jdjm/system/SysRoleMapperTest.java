package com.jdjm.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdjm.model.system.SysRole;
import com.jdjm.system.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SysRoleMapperTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    public void findAll(){
        List<SysRole> list = sysRoleMapper.selectList(null);
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

        int result = sysRoleMapper.insert(sysRole);
        System.out.println(result); //影响的行数
        System.out.println(sysRole.getId()); //id自动回填
    }


    @Test
    public void testUpdateById(){
        SysRole sysRole = sysRoleMapper.selectById(1);
        sysRole.setDescription("系统管理员小郑");

        int result = sysRoleMapper.updateById(sysRole);
        System.out.println(result);
    }


    //id删除
    @Test
    public void testDel(){
        int res = sysRoleMapper.deleteById(1);
        System.out.println("删除成功");
    }

    //批量删除
    @Test
    public void testDeleteBatchIds() {
        int result = sysRoleMapper.deleteBatchIds(Arrays.asList(1, 2));
        System.out.println(result);
    }



    //条件查询
    @Test
    public void testSelectByWrapper(){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.like("role_name","管理员");
        List<SysRole> res = sysRoleMapper.selectList(wrapper);
        for(SysRole sysRole :res){
            System.out.println(sysRole);
        }
    }

    //条件删除
    @Test
    public void testDelByWrapper(){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name","系统管理员");
        int res = sysRoleMapper.delete(wrapper);
        System.out.println(res);
    }

}
