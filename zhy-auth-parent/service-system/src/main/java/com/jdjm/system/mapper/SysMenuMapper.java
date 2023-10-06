package com.jdjm.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdjm.model.system.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author ZhengHongyi
 * @since 2023-09-17
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> getUserMenuList(@Param("id") String id);
}
