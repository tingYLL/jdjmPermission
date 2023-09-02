package com.jdjm.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdjm.model.system.SysRole;
import com.jdjm.system.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    @Test
    public void testReg(){
        String inputText = "你好 ${name} 此处是其他文本 ${name} ${age}";
        // 正则表达式模式，用于匹配占位符
        String pattern = "(\\$\\{\\w+\\})";

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(inputText);
        HashSet<String> hashSet = new HashSet<>();

        while (matcher.find()){
            if (!hashSet.contains(matcher.group())){
                hashSet.add(matcher.group());
            } else {
                String substring = matcher.group().substring(2, matcher.group().length()-1);
                inputText = inputText.replaceAll("\\$\\{" + substring + "\\}", "#\\{" + substring + "\\}");
                inputText = inputText.replaceFirst("#\\{" + substring + "\\}", "\\$\\{" + substring + "\\}");
            }
        }

        // 第一次模版替换
        // 替换
        System.out.println(inputText);

        // 把 #{} 恢复成 ${}
        inputText = inputText.replaceAll("#\\{", "\\$\\{");

        // 第二次模版替换
        System.out.println(inputText);
    }
}
