<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入供应商名称查询" style="width: 200px" v-model="name"></el-input>
      <el-input placeholder="请输入联系人电话查询" style="width: 200px; margin-left: 10px" v-model="phone"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
      <el-button type="primary" plain @click="exp">批量导出</el-button>
      <el-upload :show-file-list="false" style="display: inline-block; margin-left: 10px" :action="$baseUrl + '/supplier/import'" :headers="{ token: user.token }" :on-success="handleImport" >
        <el-button type="info" plain>批量导入</el-button>
      </el-upload>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="供应商名称"></el-table-column>
        <el-table-column prop="address" label="供应商地址"></el-table-column>
        <el-table-column prop="tel" label="供应商电话"></el-table-column>
        <el-table-column prop="email" label="供应商邮箱"></el-table-column>
        <el-table-column prop="zipCode" label="邮编"></el-table-column>
        <el-table-column prop="user" label="联系人"></el-table-column>
        <el-table-column prop="phone" label="联系人电话"></el-table-column>
        <el-table-column prop="bank" label="开户银行"></el-table-column>
        <el-table-column prop="bankCard" label="开户行账号"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template v-slot="scope">
            <el-tag type="success" v-if="scope.row.status === '可用'">可用</el-tag>
            <el-tag type="danger" v-if="scope.row.status === '不可用'">不可用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>


    <el-dialog title="供应商信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="供应商名称" prop="name">
          <el-input v-model="form.name" placeholder="供应商名称"></el-input>
        </el-form-item>
        <el-form-item label="供应商地址" prop="address">
          <el-input v-model="form.address" placeholder="供应商地址"></el-input>
        </el-form-item>
        <el-form-item label="供应商电话" prop="tel">
          <el-input v-model="form.tel" placeholder="供应商电话"></el-input>
        </el-form-item>
        <el-form-item label="供应商邮箱" prop="email">
          <el-input v-model="form.email" placeholder="供应商邮箱"></el-input>
        </el-form-item>
        <el-form-item label="邮编" prop="zipCode">
          <el-input v-model="form.zipCode" placeholder="邮编"></el-input>
        </el-form-item>
        <el-form-item label="联系人" prop="user">
          <el-input v-model="form.user" placeholder="联系人"></el-input>
        </el-form-item>
        <el-form-item label="联系人电话" prop="phone">
          <el-input v-model="form.phone" placeholder="联系人电话"></el-input>
        </el-form-item>
        <el-form-item label="开户银行" prop="bank">
          <el-input v-model="form.bank" placeholder="开户银行"></el-input>
        </el-form-item>
        <el-form-item label="开户行账号" prop="bankCard">
          <el-input v-model="form.bankCard" placeholder="开户行账号"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select style="width: 100%" v-model="form.status">
            <el-option v-for="item in ['可用', '不可用']" :key="item" :value="item" :label="item"></el-option>
          </el-select>

        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Supplier",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      name: null,
      phone: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'},
        ],
        user: [
          {required: true, message: '请输入联系人', trigger: 'blur'},
        ],
        phone: [
          {required: true, message: '请输入联系人电话', trigger: 'blur'},
        ]
      },
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleImport(res) {
      if (res.code === '200') {  // 成功
        this.$message.success('导入成功')
        this.load(1)  // 刷新表格数据
      } else {
        this.$message.error(res.msg)
      }
    },
    exp() {
      window.open(this.$baseUrl + '/supplier/export')
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
            url: this.form.id ? '/supplier/update' : '/supplier/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
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
        this.$request.delete('/supplier/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
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
        this.$request.delete('/supplier/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/supplier/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          phone: this.phone,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.phone = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>

</style>