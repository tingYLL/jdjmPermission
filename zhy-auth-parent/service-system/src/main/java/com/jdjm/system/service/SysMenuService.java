package com.jdjm.system.service;

import com.jdjm.common.result.Result;
import com.jdjm.model.system.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author ZhengHongyi
 * @since 2023-09-17
 */
public interface SysMenuService extends IService<SysMenu> {

    Result removeMenuById(String id);

    Integer updateStatus(String id, Integer status);
}
