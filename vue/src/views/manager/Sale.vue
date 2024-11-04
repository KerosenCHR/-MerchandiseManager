<template>
  <div>
    <div class="search">
      <el-input placeholder="请输客户名称查询" style="width: 200px" v-model="customerName"></el-input>
      <el-input placeholder="请输入商品名称查询" style="width: 200px; margin-left: 10px" v-model="goodsName"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="customerName" label="客户"></el-table-column>
        <el-table-column prop="goodsName" label="商品"></el-table-column>
        <el-table-column prop="payType" label="支付类型"></el-table-column>
        <el-table-column prop="time" label="销售时间" width="140"></el-table-column>
        <el-table-column prop="user" label="操作人"></el-table-column>
        <el-table-column prop="price" label="销售单价"></el-table-column>
        <el-table-column prop="num" label="销售数量"></el-table-column>
        <el-table-column prop="unit" label="商品规格"></el-table-column>
        <el-table-column prop="total" label="销售总价"></el-table-column>
        <el-table-column prop="comment" label="备注"></el-table-column>
        <el-table-column label="操作" align="center" width="220">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
            <el-button size="mini" type="warning" @click="handleBack(scope.row)">退货</el-button>
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


    <el-dialog title="商品销售信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="客户" prop="customerId">
          <el-select style="width: 100%" v-model="form.customerId" :disabled="form.id">
            <el-option v-for="item in customerList" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>

        </el-form-item>
        <el-form-item label="商品" prop="goodsId">
          <el-select style="width: 100%" v-model="form.goodsId" :disabled="form.id">
            <el-option v-for="item in goodsList" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="支付类型" prop="payType">
          <el-select style="width: 100%" v-model="form.payType">
            <el-option v-for="item in ['支付宝', '微信', '银联']" :key="item" :value="item" :label="item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="销售时间" prop="time">
          <el-date-picker type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
                          v-model="form.time" style="width: 100%"></el-date-picker>
        </el-form-item>
        <el-form-item label="销售价格" prop="price">
          <el-input v-model="form.price" placeholder="销售价格"></el-input>
        </el-form-item>
        <el-form-item label="销售数量" prop="num">
          <el-input-number :min="1" v-model="form.num" placeholder="销售数量" :disabled="form.id"></el-input-number>
        </el-form-item>
        <el-form-item label="商品规格" prop="unit">
          <el-input v-model="form.unit" placeholder="商品规格"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="comment">
          <el-input type="textarea" v-model="form.comment" placeholder="备注"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="销售退货" :visible.sync="fromVisible1" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form1" label-width="100px" style="padding-right: 50px">
        <el-form-item label="退货数量" prop="num">
          <el-input-number :min="1" v-model="form1.num" placeholder="退货数量"></el-input-number>
        </el-form-item>
        <el-form-item label="备注" prop="comment">
          <el-input type="textarea" v-model="form1.comment" placeholder="备注"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="saveBack">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Sale",
  data() {
    return {
      tableData: [],  // 所有的数据
      customerList: [],
      goodsList: [],
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      customerName: null,
      goodsName: null,
      fromVisible: false,
      fromVisible1: false,
      form: {},
      form1: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        price: [
          {required: true, message: '请输入销售价格', trigger: 'blur'},
        ],
        num: [
          {required: true, message: '请输入销售数量', trigger: 'blur'},
        ]
      },
      ids: [],
      num: 0
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    saveBack() {
      if (this.form1.num  > this.num) {
        this.$message.warning('对不起，数量错误')
        return
      }
      this.$request.post('/saleBack/add', this.form1).then(res => {
        if (res.code === '200') {  // 表示成功保存
          this.$message.success('销售退货成功')
          this.load(1)
          this.fromVisible1 = false
        } else {
          this.$message.error(res.msg)  // 弹出错误的信息
        }
      })
    },
    handleBack(row) {
      this.num = row.num
      this.form1 = JSON.parse(JSON.stringify(row))
      this.form1.id = null       // id自增，所以设置null
      this.form1.num = 1        // 设置退货数量
      this.form1.comment = ''  // 清除备注信息
      this.fromVisible1 = true
    },
    handleAdd() {   // 新增数据
      this.form = {  num: 1 }  // 新增数据的时候清空数据
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
            url: this.form.id ? '/sale/update' : '/sale/add',
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
        this.$request.delete('/sale/delete/' + id).then(res => {
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
        this.$request.delete('/sale/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/sale/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          customerName: this.customerName,
          goodsName: this.goodsName,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })

      // 请求所有客户的信息
      this.$request.get('/customer/selectAll').then(res => {
        this.customerList = res.data || []
      })

      // 请求商品信息
      this.$request.get('/goods/selectAll').then(res => {
        this.goodsList = res.data || []
      })

    },
    reset() {
      this.customerName = null
      this.goodsName = null
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