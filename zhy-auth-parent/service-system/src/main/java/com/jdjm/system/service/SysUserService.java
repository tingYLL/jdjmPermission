package com.jdjm.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jdjm.model.system.SysUser;
import com.jdjm.model.vo.SysUserQueryVo;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ZhengHongyi
 * @since 2023-08-27
 */
public interface SysUserService extends IService<SysUser> {

    IPage<SysUser> selectPage(Page<SysUser> myPage, SysUserQueryVo sysUserQueryVo);

    SysUser queryUser(String username);

    Map<String, Object> queryUserInfo(String username);
}
