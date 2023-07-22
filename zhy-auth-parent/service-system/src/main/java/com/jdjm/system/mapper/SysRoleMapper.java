package com.jdjm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdjm.model.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

//@Mapper
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
}
