import request from '@/utils/request'
const api_name = '/admin/system/user'
export default{

  // 分页条件查询获取全部
  listUsers(page,limit,searchObj){
    return request({
      url:`${api_name}/${page}/${limit}`,
      method:'get',
      params:searchObj
    })
  },

  // 添加用户
  addUser(sysUser){
    return request({
      url:`${api_name}/add`,
      method:'post',
      data:sysUser
    })
  },

  // 根据id进行查询
  queryById(id){
    return request({
      url:`${api_name}/queryById/${id}`,
      method:'get',
    })
  },

  // 根据id进行删除
  deleteUser(id){
    return request({
      url:`${api_name}/deleteUser/${id}`,
      method:'delete',
    })
  },

  // 根据id进行更新
  updateUser(sysUser){
    return request({
      url:`${api_name}/updateById`,
      method:'post',
      data:sysUser
    })
  }

}
