import request from '@/utils/request'

const api_name = '/admin/system/sysRole'

export default {
  getPageList(page,limit,searchObj){
    return request({
      // 以下写法容易写错 推荐使用es6的模板字符串写法
      // url:'/admin/system/sysRole'+page+'/'+limit,
      url:`${api_name}/${page}/${limit}`,
      method:'get',
      params:searchObj
    })
  },
  removeRole(id){
    return request({
      url:`${api_name}/remove/${id}`,
      method:'delete',
    })
  }
}
