package com.jdjm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdjm.model.system.SysRole;
import com.jdjm.model.vo.SysRoleQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

//@Mapper
@Repository

public interface SysRoleMapper extends BaseMapper<SysRole> {
    IPage<SysRole> selectPage(Page<SysRole> sysRolePage, @Param("vo") SysRoleQueryVo sysRoleQueryVo);
}
