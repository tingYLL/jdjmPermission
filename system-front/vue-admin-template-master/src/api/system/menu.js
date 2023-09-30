import request from '@/utils/request'
const api_name = '/admin/system/sysMenu'
export default{
  // 查找菜单（树形）
  treeList(){
    return request({
      url:`${api_name}/treeListMenu`,
      method:'get'
    })
  },

  listMenuById(id){
    return request({
      url:`${api_name}/listMenuById/${id}`,
      method:'get'
    })
  },

  //添加菜单
  addMenu(sysMenu){
    return request({
      url:`${api_name}/addMenu`,
      method:'post',
      data:sysMenu
    })
  },

  //更新菜单
  updateMenu(sysMenu){
    return request({
      url:`${api_name}/updateMenu`,
      method:'post',
      data:sysMenu
    })
  },

  //删除菜单
  delMenu(id){
    return request({
      url:`${api_name}/delMenu/${id}`,
      method:'delete'
    })
  },

  updateStatus(id,status){
    return request({
      url:`${api_name}/updateStatus/${id}/${status}`,
      method:'get'
    })
  },

  //根据角色id显示当前已为该角色分配的菜单
  findMenuOfRole(id){
    return request({
      url:`${api_name}/findMenu/${id}`,
      method:'get'
    })
  },

  //为角色分配菜单
  assignMenu(assignMenuVo){
    return request({
      url:`${api_name}/assignMenu`,
      method:'post',
      data:assignMenuVo
    })
  }


}
