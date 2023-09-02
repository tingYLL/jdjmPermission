package com.jdjm.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdjm.model.system.SysUser;
import com.jdjm.model.vo.SysUserQueryVo;
import com.jdjm.system.mapper.SysUserMapper;
import com.jdjm.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> myPage, SysUserQueryVo sysUserQueryVo) {
        IPage<SysUser> iPage= baseMapper.selectPage(myPage,sysUserQueryVo);
        return iPage;
    }
}
