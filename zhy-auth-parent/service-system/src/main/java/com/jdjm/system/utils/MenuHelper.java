package com.jdjm.system.utils;

import com.jdjm.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {


    public static List<SysMenu>  buildTree(List<SysMenu> list){
        List<SysMenu> trees = new ArrayList<>();

        for (SysMenu sysMenu : list) {
            //找到顶层菜单
            if(sysMenu.getParentId() == 0){
                trees.add(findChildren(sysMenu,list));
            }
        }
        return trees;
    }

    public static SysMenu findChildren(SysMenu sysMenu,List<SysMenu> sysMenuList){
        sysMenu.setChildren(new ArrayList<SysMenu>());
        for (SysMenu menu : sysMenuList) {
            if(sysMenu.getChildren() == null){
                sysMenu.setChildren(new ArrayList<>());
            }
            if(menu.getParentId() == Long.parseLong(sysMenu.getId())){
               sysMenu.getChildren().add(findChildren(menu,sysMenuList));
            }
        }
        return sysMenu;
    }

}
