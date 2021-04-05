<template>
  <div id="statistical">
    <el-button @click="excelForm.visible = true" type="success" style="margin-top:20px;margin-left:20px">Excel</el-button>
    <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="新建优秀生源地信息管理" name="source">
        <el-row>
          <el-col class="main-col" :span="24">
            <el-table :data="sourceTableData" border style="width: 100%">
              <el-table-column label="类别">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.sourceTypeObj == null ? '' : scope.row.sourceTypeObj.dictionaryName == null ? '' : scope.row.sourceTypeObj.dictionaryName }}</span>
                </template>
              </el-table-column>

              <el-table-column label="名称">
                <template slot-scope="scope">
                  <span v-if="scope.row.sourceType == 'source_type_college'" style="margin-left: 10px">{{ scope.row.collegeObj == null ? '' : scope.row.collegeObj.dictionaryName == null ? '' : scope.row.collegeObj.dictionaryName }}</span>
                  <span v-if="scope.row.sourceType == 'source_type_group'" style="margin-left: 10px">{{ scope.row.groupObj == null ? '' : scope.row.groupObj.groupId == null ? '' : scope.row.groupObj.groupId }}</span>
                </template>
              </el-table-column>

              <el-table-column label="数量">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.sourceNumber == null ? '' : scope.row.sourceNumber }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="240">
                <template slot-scope="scope">
                  <el-button size="mini"  @click="handleUpdSource(scope.$index, scope.row)">编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
              <el-pagination background @size-change="handleSourceSizeChange" @current-change="handleSourceCurrentChange" :hide-on-single-page="false" :current-page="sourcePage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="sourcePage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="sourcePage.totalCount"></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>

    <!--==========================================================================================-->
    <el-dialog :title="excelForm.title" :visible.sync="excelForm.visible" width="60%">
      <el-row>
        <el-col class="excel-col" :span="18">
          <h1>导出</h1>
          <el-table :data="excelForm.excelOutTableData" border style="width: 100%">
            <el-table-column label="导出项目">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.fileName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="描述">
              <template slot-scope="scope">
                <span style="margin-left: 10px">
                  {{
                  scope.row.describe
                  }}
                </span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120">
              <template slot-scope="scope">
                <el-button size="mini" type="success" @click="downloadExcel(scope.$index, scope.row)">下载Excel</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
        <el-col class="excel-col" :span="6">
          <h1>导入新建优秀生源地信息</h1>
          <el-upload class="upload-demo" ref="upload1" :action="axios.defaults.baseURL + '/statistical/importSourceExcel'" :on-preview="handlePreview1" :on-remove="handleRemove1" :file-list="fileList1" :limit="1" :on-success="excelImportOver1" :on-error="excelImportError1" accept=".xlsx" :auto-upload="false" :headers="{'token': token}">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload1">上传到服务器</el-button>
            <div slot="tip" class="el-upload__tip">只能上传xlsx文件</div>
            <div slot="tip" class="el-upload__tip">此导入功能为覆盖模式</div>
          </el-upload>
          <h1>导入中学名称替换表</h1>
          <el-upload class="upload-demo" ref="upload2" :action="axios.defaults.baseURL + '/statistical/importSchoolNameExcel'" :on-preview="handlePreview2" :on-remove="handleRemove2" :file-list="fileList2" :limit="1" :on-success="excelImportOver2" :on-error="excelImportError2" accept=".xlsx" :auto-upload="false" :headers="{'token': token}">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload2">上传到服务器</el-button>
            <div slot="tip" class="el-upload__tip">只能上传xlsx文件</div>
            <div slot="tip" class="el-upload__tip">此导入功能为更新模式</div>
          </el-upload>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">

      </div>
    </el-dialog>
 <!--==========================================================================================-->
<el-dialog title="修改新建优秀生源地数量" :visible.sync="sourceAddUpdForm.visible" width="690px">
      <el-form label-position="left">
        <el-form-item label="新建优秀生源地数量：" :label-width="sourceAddUpdForm.labelWidth">
          <el-input v-model="sourceAddUpdForm.sourceNumber" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="sourceAddUpdForm.visible = false">取 消</el-button>
        <el-button type="primary" @click="alterSource()">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
import store from "../../vuex/store.js";
export default {
  store,
  data() {
    return {
      token: "",
      activeName: 'source',

      sourceTableData: [],
      sourcePage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },
      sourceAddUpdForm:{
        visible:false,
        sourceId:"",
        sourceNumber:""
      },
      excelForm: {
        title: "Excel导入与导出",
        visible: false,
        excelOutTableData: [
          {
            fileName: "优秀生源地信息.xlsx",
            describe: "导出招生组和学院的优秀生源地信息Excel表",
            url: "/statistical/exportSourceExcel"
          },
          {
            fileName: "中学名称.xlsx",
            describe: "导出全部中学名称",
            url: "/statistical/exportSchoolNameExcel"
          }
        ]
      },
      fileList1: [],
      fileList2: [],
      //==============================================================================================
    };
  },
  methods: {

    handleClick(tab, event) {
      // if (this.lastSelectForm.admin != this.selectForm.admin || this.lastSelectForm.group != this.selectForm.group || this.lastSelectForm.college != this.selectForm.college) {
      //   this.search();
      // }
    },


    downloadExcel(index, item) {
      // let date = item.plans[this.daysIndex[index]];
      const loading = this.$loading(this.$store.state.loadingOption1);
      let url = item.url;
      this.axios({
        method: "get",
        url: url,
        responseType: "blob",
        headers: { token: this.token }
      }).then(data => {
        if (!data) {
          return;
        }
        debugger;
        let url = window.URL.createObjectURL(data.data);
        let link = document.createElement("a");
        link.style.display = "none";
        link.href = url;
        link.setAttribute("download", item.fileName);
        document.body.appendChild(link);
        link.click();
        loading.close();
      });
    },





    excelImportOver1(response, file, fileList) {

      if (response.code == 200) {
        this.$alert(response.data, '上传成功', {
          confirmButtonText: '确定',
          type: 'success',
          callback: action => {
            this.initSource();
            this.fileList1 = [];
            this.excelForm.visible = false;
          }
        });
      } else if (response.code == 401) {
        this.$router.push({ path: "/login" });
      } else {
        this.$alert(response.data, '上传失败', {
          confirmButtonText: '确定',
          type: 'error',
          callback: action => {
            this.initGroup();
            this.fileList1 = []
          }
        });
      }
    },
    excelImportError1(error, response, file, fileList) {
      this.$alert('服务器错误', '上传失败', {
        confirmButtonText: '确定',
        type: 'error',
        callback: action => {
          this.initGroup();
          this.fileList1 = []
        }
      });
    },
    submitUpload1() {
      this.$refs.upload1.submit();
    },
    handleRemove1(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview1(file) {
      console.log(file);
    },

    excelImportOver2(response, file, fileList) {

      if (response.code == 200) {
        this.$alert(response.data, '上传成功', {
          confirmButtonText: '确定',
          type: 'success',
          callback: action => {

          }
        });
      } else if (response.code == 401) {
        this.$router.push({ path: "/login" });
      } else {
        this.$alert(response.data, '上传失败', {
          confirmButtonText: '确定',
          type: 'error',
          callback: action => {
            this.initGroup();
            this.fileList2 = []
          }
        });
      }
    },
    excelImportError2(error, response, file, fileList) {
      this.$alert('服务器错误', '上传失败', {
        confirmButtonText: '确定',
        type: 'error',
        callback: action => {
          this.initGroup();
          this.fileList2 = []
        }
      });
    },
    submitUpload2() {
      this.$refs.upload2.submit();
    },
    handleRemove2(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview2(file) {
      console.log(file);
    },

    handleSourceSizeChange(val) {
      this.sourcePage.showCount = val;
      this.sourcePage.current = 1;
      this.initSource();
    },
    handleSourceCurrentChange(val) {
      this.sourcePage.current = val;
      this.initSource();
    },
    handleUpdSource(index, row) {
      this.sourceAddUpdForm.sourceId = row.sourceId;
      this.sourceAddUpdForm.sourceNumber = row.sourceNumber;
      this.sourceAddUpdForm.visible = true;
    },
    alterSource(){
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios
        .post("/statistical/alterSource", {
          params: {
            source: this.sourceAddUpdForm
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.initSource();
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "success"
            });
            this.sourceAddUpdForm.visible = false;
          } else if (res.data.code == 401) {
            this.$router.push({ path: "/login" });
          } else {
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "error"
            });
          }
          loading.close();
        });
    },

    initSource() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/statistical/getSourceBySearch", {
          params: {
            currentPage: this.sourcePage.current,
            showCount: this.sourcePage.showCount,
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.sourcePage.totalCount = res.data.object.totalNum;
            this.sourceTableData = res.data.object.items;
          } else if (res.data.code == 401) {
            this.$router.push({ path: "/login" });
          } else if (res.data.code == 402) {
            this.$router.push({ path: "/role" });
          } else {
            this.$alert(res.data.data, "错误", {
              confirmButtonText: "确定",
              type: "error",
              callback: action => {

              }
            });
          }
          loading.close();
        });
    },
 
  },
  created: function () {
    //登陆验证代码
    //权限验证代码
    this.token = localStorage.getItem("token");
    if (this.token == "" || this.token == null) {
      this.$router.push({ path: "/login" });
      return;
    }

    this.initSource();

  }


};
</script>

<style  lang="scss">
#statistical {
  .el-main {
    padding: 0px;
  }
  .main-col {
    padding: 20px;
  }
  .el-form-item {
    margin-top: 20px;
    margin-left: 20px;
    margin-right: 0px;
    margin-bottom: 0px;
  }
  .el-tabs--border-card {
    margin: 20px;
  }
}
</style>