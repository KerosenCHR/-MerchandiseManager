<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入名称查询" style="width: 200px" v-model="name"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
    </div>

    <div class="table">
      <el-table row-key="id" default-expand-all :data="tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="address" label="地址" show-overflow-tooltip></el-table-column>
        <el-table-column prop="pid" label="父级ID"></el-table-column>
        <el-table-column prop="level" label="层级"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini">编辑</el-button>
            <el-button plain type="danger" size="mini" @click=del(scope.row.id)>删除</el-button>
          </template>
        </el-table-column>
      </el-table>


      <el-dialog title="信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
        <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
          <el-form-item prop="title" label="名称">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="address" label="地址">
            <el-input type="textarea" :rows="5" v-model="form.address" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="父级" prop="pid">
            <el-select ref="selectTree" v-model="form.pid" clearable style="width: 100%;">
              <el-option
                  v-for="(item, index) in departmentList"
                  :key="index"
                  :label="item.name"
                  :value="item.id"
                  style="display: none;"/>

              <el-tree
                  :data="tableData"
                  :props="{children: 'children', label: 'name'}"
                  highlight-current
                  @node-click="handleNodeClick"
                  default-expand-all />
            </el-select>
          </el-form-item>
          <el-form-item prop="level" label="层级">
            <el-input v-model="form.level" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="fromVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>

  </div>
</template>

<script>
export default {
  name: "Department",
  data() {
    return {
      tableData: [],  // 所有的数据
      departmentList: [],
      name: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'},
        ],
        address: [
          {required: true, message: '请输入地址', trigger: 'blur'},
        ]
      },
      ids: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    // node 就是department对象
    handleNodeClick(node) {
      console.log(node)
      if (node.level > 2) {
        this.$message.warning("父节点只能选择1级或2级部门")
        return
      }
      this.$set(this.form, 'pid', node.id)
      this.$refs.selectTree.blur()
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/department/update' : '/department/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load()
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/department/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/department/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load() {
      this.$request.get('/department/selectTree').then(res => {
        this.tableData = res.data || []
      })

      this.$request.get('/department/selectAll').then(res => {
        this.departmentList = res.data || []
      })
    },
    reset() {
      this.name = null
      this.load(1)
    },
  }
}
</script>

<style scoped>

</style>