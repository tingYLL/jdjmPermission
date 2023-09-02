package com.jdjm.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdjm.model.system.SysRole;
import com.jdjm.model.system.SysUser;
import com.jdjm.model.vo.SysRoleQueryVo;
import com.jdjm.model.vo.SysUserQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author ZhengHongyi
 * @since 2023-08-27
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<SysUser> selectPage(Page<SysUser> sysUserPage, @Param("vo") SysUserQueryVo sysUserQueryVo);


}
