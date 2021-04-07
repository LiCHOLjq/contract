<template>
  <div id="log">
    <el-form :inline="true" :model="logSelectForm" class="demo-form-inline">
      <el-form-item label="类型">
        <el-select v-model="logSelectForm.logType" placeholder="选择类型" @change="answerPage.current=1;initAnswer()">
          <el-option label="全部" value=""></el-option>
          <el-option v-for="item in logTypeList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间：">
        <el-date-picker style="width: 290px;" v-model="logSelectForm.logDateBeginStr" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间：">
        <el-date-picker style="width: 290px;" v-model="logSelectForm.logDateEndStr" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="用户">
        <el-input v-model="logSelectForm.logAdmin" placeholder="账号/姓名" style="width: 200px;"></el-input>
      </el-form-item>

      <el-form-item label="合同名称">
        <el-input v-model="logSelectForm.logMessage2" placeholder="id/标题" style="width: 200px;"></el-input>
      </el-form-item>

      <el-form-item label="IP">
        <el-input v-model="logSelectForm.logRemoteIp" placeholder="IP地址" style="width: 200px;"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="initLog()">查询</el-button>
        <el-button @click="downloadExcel()" type="success">导出Excel</el-button>
        <el-button type="danger" @click="delAllLog()">清空记录</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-col class="main-col" :span="24">

        <!-- @click=点击按钮时执行的代码或方法 -->

        <el-table :data="logTableData" border style="width: 100%">
          <el-table-column label="时间" width="170">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.logDateStr == null ? '' : scope.row.logDateStr }}</span>
            </template>
          </el-table-column>
          <el-table-column label="用户" width="150">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{
                (scope.row.logAdmin == null ? '' : scope.row.logAdmin) + " " + (scope.row.logAdminName == null ? '' : scope.row.logAdminName)
                }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="类型" width="140">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{
                scope.row.logTypeStr == null ? '' : scope.row.logTypeStr
                }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="对方IP" width="145">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{
                  scope.row.logRemoteIp == null ? '' : scope.row.logRemoteIp
                  }}
              </span>
            </template>
          </el-table-column>

          <el-table-column label="关联合同Id" width="180">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{

                  scope.row.logMessage1 == null ? '' : scope.row.logMessage1
                  }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="关联合同名称" width="180">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{

                   scope.row.logMessage2 == null ? '' : scope.row.logMessage2
                  }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="关联分享Id" width="180">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{

                   scope.row.logMessage3 == null ? '' : scope.row.logMessage3
                  }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="User-Agent">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{

                   scope.row.logUserAgent == null ? '' : scope.row.logUserAgent
                  }}
              </span>
            </template>
          </el-table-column>
          <!-- <el-table-column label="X-Requested-Width" width="200">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{

                   scope.row.logXRequestedWidth == null ? '' : scope.row.logXRequestedWidth
                  }}
              </span>
            </template>
          </el-table-column> -->
          <el-table-column label="操作" width="90">
            <template slot-scope="scope">
              <el-button size="mini" type="danger" @click="handleDelLog(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
          <el-pagination background @size-change="handleLogSizeChange" @current-change="handleLogCurrentChange" :hide-on-single-page="false" :current-page="logPage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="logPage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="logPage.totalCount"></el-pagination>
        </div>
      </el-col>
    </el-row>
    <!--==========================================================================================-->
    <!-- <el-dialog :title="excelForm.title" :visible.sync="excelForm.visible" width="60%">
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
                <el-button
                  size="mini"
                  type="success"
                  @click="downloadExcel(scope.$index, scope.row)"
                >下载Excel</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
        <el-col class="excel-col" :span="6">
          <h1>导入</h1>
          <el-upload
            class="upload-demo"
            ref="upload"
            :action="axios.defaults.baseURL + '/admin/importUserExcel'"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
            :limit="1"
            :on-success="excelImportOver"
            :on-error="excelImportError"
            accept=".xls"
            :auto-upload="false"
            :headers="{'token': token}"
          >
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button
              style="margin-left: 10px;"
              size="small"
              type="success"
              @click="submitUpload"
            >上传到服务器</el-button>
            <div slot="tip" class="el-upload__tip">只能上传xls文件</div>
          </el-upload>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">

      </div>
    </el-dialog> -->
    <!-- <el-dialog
      :title="userAddUpdForm.title"
      :visible.sync="userAddUpdForm.visible"
      width="690px"
    >
      <el-form label-position="left">
        <el-form-item
          label="ID："
          :label-width="userAddUpdForm.labelWidth"
        >
          <el-input v-model="userAddUpdForm.userId" :disabled="userAddUpdForm.submitState=='Upd'" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="姓名：" :label-width="userAddUpdForm.labelWidth">
          <el-input v-model="userAddUpdForm.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="账号：" :label-width="userAddUpdForm.labelWidth">
          <el-input v-model="userAddUpdForm.userAccount" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码：" :label-width="userAddUpdForm.labelWidth">
          <el-input v-model="userAddUpdForm.userPassword" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userAddUpdForm.visible = false">取 消</el-button>
        <el-button type="primary" @click="alterUser()">确 定</el-button>
      </div>
    </el-dialog> -->
  </div>
</template>
<script>
import store from "../../vuex/store.js";
export default {
  store,
  data() {
    return {
      token: "",
      // excelForm: {

      //   title: "Excel导入与导出",
      //   visible: false,
      //   excelOutTableData: [
      //     {
      //       fileName: "学生导入模板.xls",
      //       describe: "导出学生的空Excel表",
      //       url: "/admin/exportUserNullExcel"
      //     },
      //     {
      //       fileName: "学生全部数据.xls",
      //       describe: "导出全部学生",
      //       url: "/admin/exportUserAllExcel"
      //     }
      //   ]
      // },
      // fileList: [],

      logSelectForm: {
        logType: "",
        logDateBeginStr: "",
        logDateEndStr: "",
        logAdmin: "",
        logMessage2: "",
        logRemoteIp: "",
      },
      logTableData: [],
      logPage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },

      // userAddUpdForm: {
      //   title: "",
      //   visible: false,
      //   userId: "",
      //   userName: "",
      //   userAccount: "",
      //   userPassword: "",
      //   submitState: "",
      //   labelWidth: "120px"
      // },
      //==============================================================================================
    };
  },
  methods: {

    downloadExcel() {
      // let date = item.plans[this.daysIndex[index]];
      const loading = this.$loading(this.$store.state.loadingOption1);
      let url = "/log/getLogBySearchToExcel";
      this.axios({
        method: "post",
        url: url,
        responseType: "blob",
        data: {
          params: {
            log: this.logSelectForm,
          }
        },
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
        link.setAttribute("download", "日志.xlsx");
        document.body.appendChild(link);
        link.click();
        loading.close();
      });
    },
    //     excelImportOver(response, file, fileList){

    //       if(response.code == 200){
    // this.$alert(response.data, '上传成功', {
    //           confirmButtonText: '确定',
    //            type: 'success',
    //           callback: action => {
    //             this.initUser();
    //             this.fileList = [];
    //             this.excelForm.visible = false;
    //           }
    //         });
    //       } else if (response.code == 401) {
    //         this.$router.push({ path: "/login" });
    //       }else{
    // this.$alert(response.data, '上传失败', {
    //           confirmButtonText: '确定',
    //            type: 'error',
    //           callback: action => {
    //             this.initUser();
    //             this.fileList = []
    //           }
    //         });
    //       }
    //     },
    //     excelImportError(error,response, file, fileList){
    // this.$alert('服务器错误', '上传失败', {
    //           confirmButtonText: '确定',
    //            type: 'error',
    //           callback: action => {
    //             this.initUser();
    //             this.fileList = []
    //           }
    //         });
    //     },
    //     submitUpload() {
    //       this.$refs.upload.submit();
    //     },
    //     handleRemove(file, fileList) {
    //       console.log(file, fileList);
    //     },
    //     handlePreview(file) {
    //       console.log(file);
    //     },
    initLog() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/log/getLogBySearch", {
          params: {
            log: this.logSelectForm,
            currentPage: this.logPage.current,
            showCount: this.logPage.showCount
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.logPage.totalCount = res.data.data.totalNum;
            this.logTableData = res.data.data.items;
          } else if (res.data.code == 401) {
            this.$router.push({ path: "/login" });
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
    // handleAddUser(){
    //   this.userAddUpdForm.title = "添加学生信息";
    //   this.userAddUpdForm.visible = true;
    //   this.userAddUpdForm.userId = "";
    //   this.userAddUpdForm.userName = "";
    //   this.userAddUpdForm.userAccount = "";
    //   this.userAddUpdForm.userPassword = "";
    //   this.userAddUpdForm.submitState = "Add";
    // },
    // handleUpdUser(index, row){
    //   this.userAddUpdForm.title = "添加学生信息";
    //   this.userAddUpdForm.visible = true;
    //   this.userAddUpdForm.userId = row.userId;
    //   this.userAddUpdForm.userName = row.userName;
    //   this.userAddUpdForm.userAccount = row.userAccount;
    //   this.userAddUpdForm.userPassword = row.userPassword;
    //   this.userAddUpdForm.submitState = "Upd";
    // },
    handleDelLog(index, row) {
      this.$confirm('您确定删除该日志吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading(this.$store.state.loadingOption2);
        this.axios
          .post("/log/delLog", {
            params: {
              log: row
            }
          }, { headers: { token: this.token } })
          .then(res => {
            if (res.data.code === 200) {
              this.initLog();
              this.$message({
                showClose: true,
                message: res.data.data,
                type: "success"
              });
              this.userAddUpdForm.visible = false;
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
      }).catch(() => {

      });
    },
    handleLogSizeChange(val) {
      this.logPage.showCount = val;
      this.logPage.current = 1;
      this.initLog();
    },
    handleLogCurrentChange(val) {
      this.logPage.current = val;
      this.initLog();
    },
    delAllLog() {
      this.$confirm('您确定清空全部日志吗？该操作不可恢复，请确认数据妥善备份后再执行此操作。', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading(this.$store.state.loadingOption2);
        this.axios
          .post("/log/delAllLog", {
            params: {

            }
          }, { headers: { token: this.token } })
          .then(res => {
            if (res.data.code === 200) {
              this.initLog();
              this.$message({
                showClose: true,
                message: res.data.data,
                type: "success"
              });
              this.userAddUpdForm.visible = false;
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
      }).catch(() => {

      });
    }
  },
  created: function () {
    //登陆验证代码
    //权限验证代码
    this.token = localStorage.getItem("token");
    if (this.token == "" || this.token == null) {
      this.$router.push({ path: "/login" });
      return;
    }
    this.initLog();

  }
};
</script>

<style  lang="scss">
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
</style>