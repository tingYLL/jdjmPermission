package com.jdjm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdjm.model.system.SysUser;
import com.jdjm.model.vo.RouterVo;
import com.jdjm.model.vo.SysUserQueryVo;
import com.jdjm.system.exception.JdjmException;
import com.jdjm.system.mapper.SysUserMapper;
import com.jdjm.system.service.SysMenuService;
import com.jdjm.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ZhengHongyi
 * @since 2023-08-27
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuService sysMenuService;
    @Override
    public IPage<SysUser> selectPage(Page<SysUser> myPage, SysUserQueryVo sysUserQueryVo) {
        IPage<SysUser> iPage= baseMapper.selectPage(myPage,sysUserQueryVo);
        return iPage;
    }

    @Override
    public SysUser queryUser(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);

        return sysUser;
    }

    @Override
    public Map<String, Object> queryUserInfo(String username) {
        Map<String,Object> map = new HashMap<>();
        map.put("roles","[\"admin\"]");
        map.put("introduction","I am a super administrator zhy");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name",username);

        SysUser sysUser = queryUser(username);

        List<RouterVo> routerVoList = sysMenuService.getUserMenuList(sysUser.getId());
        List<String> buttonsList = sysMenuService.getButtonList(sysUser.getId());
        map.put("buttons", buttonsList);
        map.put("routers", routerVoList);
        return map;
    }
}
