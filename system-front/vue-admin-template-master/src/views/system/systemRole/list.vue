<template>
  <div class="app-container">
    <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色名称">
              <el-input style="width: 100%" v-model="searchObj.roleName" placeholder="角色名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini"  @click="fetchData()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
        </el-row>
      </el-form>
    </div>
    <!-- 工具条 -->
    <div class="tools-div">
      <el-button type="success" icon="el-icon-plus" size="mini" @click="add">添 加</el-button>
      <el-button class="btn-add" size="mini" @click="batchRemove()" >批量删除</el-button>
    </div>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      style="width: 100%;margin-top: 10px;"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection"/>
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="roleCode" label="角色编码" />
      <el-table-column prop="createTime" label="创建时间" width="160"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" title="修改"/>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)" title="删除"/>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="fetchData"
    />
    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%" >
      <el-form ref="dataForm" :model="sysRole" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="角色名称">
          <el-input v-model="sysRole.roleName"/>
        </el-form-item>
        <el-form-item label="角色编码">
          <el-input v-model="sysRole.roleCode"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>


<script>
import api from '@/api/system/role'

export default{
  data(){
    return {
      listLoading:false,
      list:[],
      total:0,
      page:1, //当前页
      limit:3,
      searchObj:{},
      sysRole:{},
      dialogVisible:false,
      selectionsValue:[]
    }
  },
  created() {
    this.fetchData(1)
  },
  methods:{
    fetchData(pageNum=1){

      this.page = pageNum
      //发送axios请求
      this.list = api.getPageList(this.page,this.limit,this.searchObj).then(res=>{
        this.list = res.data.records
        this.total = res.data.total
      })
    },
    resetData(){
      console.log('重置查询表单')
      this.searchObj = {}
      this.fetchData()
    },
    removeDataById(id){
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        //点击确定执行then，执行删除方法
        api.removeRole(id).then(res=>{
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          //刷新页面
          this.fetchData()
        })

      })
    },

    //点击添加button 调用方法 把弹出框设为可见
    add(){
      this.dialogVisible = true
      // 清空上一次新增时留下的数据
      this.sysRole = {}
    },


    //弹出编辑框 根据这一行的id去数据库中查到数据 然后回显到编辑框中
    edit(id){
      this.dialogVisible = true
      api.queryRoleById(id).then(res=>{
        this.sysRole = res.data
      })
    },

    //添加角色
    saveRole(){
      api.saveRole(this.sysRole).then(res=>{

        //提示添加成功
        this.$message({
          type: 'success',
          message: '添加成功!'
        });

        //关闭弹窗
        this.dialogVisible = false

        //刷新页面
        this.fetchData(this.page)
      })
    },

    //更新角色
    updateRole(){
      api.updateRole(this.sysRole).then(res=>{
        this.$message({
          type: 'success',
          message: '修改成功!'
        });
        //关闭弹窗
        this.dialogVisible = false

        //刷新页面
        this.fetchData(this.page)
      })
    },

    saveOrUpdate(){
      if(!this.sysRole.id){
        this.saveRole()

      }else{
        this.updateRole()
      }
    },

    batchRemove(){
      if(this.selectionsValue.length === 0){
        this.$message.warning('请选择要删除的记录！')
        return
      }
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var ids = []
        for(var i = 0;i < this.selectionsValue.length;i++){
          ids.push(this.selectionsValue[i].id)
        }
        //点击确定执行then，执行删除方法
        api.removeBatch(ids).then(res=>{
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        })

        //刷新
        this.fetchData(this.page)
      })
    },


    handleSelectionChange(selections){
      console.log("选择的记录内容:",selections)
      this.selectionsValue = selections
    }
  }
}
</script>
