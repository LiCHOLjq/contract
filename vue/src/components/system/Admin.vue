<template>
  <div id="admin">
    <el-form :inline="true" :model="adminSelectForm" class="demo-form-inline">
      <el-form-item label="账号: ">
        <el-input v-model="adminSelectForm.adminAccount" autocomplete="off" style="width:120px"></el-input>
      </el-form-item>
      <el-form-item label="姓名: ">
        <el-input v-model="adminSelectForm.adminName" autocomplete="off" style="width:120px"></el-input>
      </el-form-item>
      <el-form-item label="角色：">
        <el-select v-model="adminSelectForm.adminRole" placeholder="选择角色" style="width: 120px;">
          <el-option label="全部" value=""></el-option>
          <el-option v-for="item in roleSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态：">
        <el-select v-model="adminSelectForm.adminUseful" placeholder="选择用户状态" style="width: 120px;">
          <el-option label="全部" :value="undefined"></el-option>
          <el-option label="启用" :value="true"></el-option>
          <el-option label="冻结" :value="false"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="adminPage.current=1;
            initAdminTable();">
          查询
        </el-button>

        <el-button type="primary" @click="excelForm.visible = true">Excel</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-col class="main-col" :span="24">
        <el-button size="mini" icon="el-icon-plus" type="primary" @click="handleAddAdmin()">
          添加
        </el-button>
        <el-table v-bind:data="adminTableData" border style="width: 100%">
          <el-table-column label="账号" width="200">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.adminAccount == null ? '' : scope.row.adminAccount }}</span>
            </template>
          </el-table-column>
          <el-table-column label="姓名">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.adminName == null ? '' : scope.row.adminName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="角色" width="200">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.adminRoleObj == null ? '' : scope.row.adminRoleObj.dictionaryName == null ? '' : scope.row.adminRoleObj.dictionaryName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" width="200">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.adminUseful == null ? '' : scope.row.adminUseful ? "启用" : "冻结" }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="300">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleUpdAdmin(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDeleteAdmin(scope.$index, scope.row)">删除</el-button>
              <!-- <el-button size="mini" v-if="scope.row.adminUseful=='admin_userful_able'" @click="handleOpenAdmin(scope.$index, scope.row)">启用</el-button>
              <el-button size="mini" v-if="scope.row.adminUseful=='admin_userful_ban'" @click="handleFreezeAdmin(scope.$index, scope.row)">冻结</el-button> -->
              <el-button size="mini" @click="handleResetPassword(scope.$index, scope.row)">重置密码</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
          <el-pagination background @size-change="handleAdminTableSizeChange" @current-change="handleAdminTableCurrentChange" :hide-on-single-page="false" :current-page="adminPage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="adminPage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="adminPage.totalCount"></el-pagination>
        </div>
      </el-col>
    </el-row>

    <!-- ====================================增添或修改用户table============================================ -->
    <el-dialog :title="adminAddUpdForm.title" :visible.sync="adminAddUpdForm.visible" width="600px">
      <el-row>

        <el-form style="padding-left:20px;padding-right:40px">
          <el-form-item label="账号：" :label-width="adminAddUpdForm.formLabelWidth">
            <el-input v-model="adminAddUpdForm.adminAccount" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="姓名：" :label-width="adminAddUpdForm.formLabelWidth">
            <el-input v-model="adminAddUpdForm.adminName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="角色：" :label-width="adminAddUpdForm.formLabelWidth">
            <el-select v-model="adminAddUpdForm.adminRole" placeholder="选择角色">
              <el-option v-for="item in roleSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态：" :label-width="adminAddUpdForm.formLabelWidth">
            <el-select v-model="adminAddUpdForm.adminUseful" placeholder="选择用户状态">
              <el-option :key="1" :label="'启用'" :value="true"></el-option>
              <el-option :key="0" :label="'冻结'" :value="false"></el-option>
            </el-select>
          </el-form-item>
        </el-form>

      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="adminAddUpdForm.visible= false">取 消</el-button>
        <el-button type="primary" @click="alterAdmin()">确 定</el-button>
      </div>
    </el-dialog>
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
                <el-button size="mini" type="primary" @click="downloadExcel(scope.$index, scope.row)">下载Excel</el-button>
              </template>
            </el-table-column>

          </el-table>
        </el-col>
        <el-col class="excel-col" :span="6">
          <h1>导入</h1>
          <el-upload class="upload-demo" ref="upload" :action="axios.defaults.baseURL + '/admin/importAdminExcel'" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="fileList" :limit="1" :on-success="excelImportOver" :on-error="excelImportError" accept=".xlsx" :auto-upload="false" :headers="{'token': token}">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="primary" @click="submitUpload">上传到服务器</el-button>
            <div slot="tip" class="el-upload__tip">只能上传xlsx文件</div>
            <div slot="tip" class="el-upload__tip">此导入功能为追加模式</div>
          </el-upload>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">

      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data() {
    return {
      token: "",
      // role collage form 选择框
      adminSelectForm: {
        adminAccount: "",
        adminName: "",
        adminRole: "",
        adminUseful: undefined,
      },
      // role可选列表
      roleSelectiveList: [],
      // collage可选列表
      // groupSelectiveList: [],
      // 用户表数据
      adminTableData: [],
      // 用户表view页面配置
      adminPage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },
      // 添加或修改用户表数据 form
      adminAddUpdForm: {
        formLabelWidth: "80px",
        title: "",
        visible: false,
        adminId: "",
        adminAccount: "",
        adminName: "",
        adminRole: "",
        adminUseful: true,
        submitState: ""
      },

      excelForm: {
        title: "Excel导入与导出",
        visible: false,
        excelOutTableData: [
          {
            fileName: "用户信息导入模板.xlsx",
            describe: "导出用户信息的空Excel表",
            url: "/admin/exportAdminNullExcel"
          },
          {
            fileName: "用户信息表.xlsx",
            describe: "导出全部用户信息",
            url: "/admin/exportAdminExcel"
          }
        ]
      },
      fileList: [],
    }
  },
  methods: {

    // 用户信息表数据请求
    initAdminTable() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios.post("/admin/getAdminBySearch", {
        params: {
          admin: this.adminSelectForm,
          currentPage: this.adminPage.current,
          showCount: this.adminPage.showCount
        }
      },
        { headers: { token: this.token } }).then(res => {
          if (res.data.code === 200) {
            this.adminPage.totalCount = res.data.object.totalNum;
            this.adminTableData = res.data.object.items;
          } else if (res.data.code == 401) {
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "error"
            });
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
    // 处理增加用户信息
    handleAddAdmin() {
      this.adminAddUpdForm.title = "添加用户信息";
      this.adminAddUpdForm.visible = true;
      this.adminAddUpdForm.adminId = "";
      this.adminAddUpdForm.adminAccount = "";
      this.adminAddUpdForm.adminName = "";
      this.adminAddUpdForm.adminRole = "admin_role_normal";
      this.adminAddUpdForm.adminUseful = "1";
      this.adminAddUpdForm.submitState = "Add";
    },
    // 处理修改用户
    handleUpdAdmin(index, row) {
      this.adminAddUpdForm.title = "修改用户信息";
      this.adminAddUpdForm.visible = true;
      this.adminAddUpdForm.adminId = row.adminId;
      this.adminAddUpdForm.adminAccount = row.adminAccount;
      this.adminAddUpdForm.adminName = row.adminName;
      this.adminAddUpdForm.adminRole = row.adminRole;
      this.adminAddUpdForm.adminUseful = row.adminUseful;
      this.adminAddUpdForm.submitState = "Upd";
    },
    // 删除用户请求
    handleDeleteAdmin(index, row) {
      this.$confirm('此操作将永久删除该用户所有相关记录，该用户已上传的合同将视为分享上传，此操作不可恢复，如果您删除的是自己，那么删除之后您将无法在进行任何操作，您确定删除此用户吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.adminAddUpdForm.adminId = row.adminId;
        // this.adminAddUpdForm.adminAccount = row.adminAccount;
        this.adminAddUpdForm.submitState = "Del";
        this.alterAdmin();
      }).catch(() => {

      });
    },

    handleAdminTableSizeChange(val) {
      this.adminPage.showCount = val;
      this.adminPage.current = 1;
      this.initAdminTable();
    },
    handleAdminTableCurrentChange(val) {
      this.adminPage.current = val;
      this.initAdminTable();
    },

    initRoleList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItems", {
          params: {
            dictionaryType: "ADMIN_ROLE"
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.roleSelectiveList = res.data.object;
          } else if (res.data.code == 401) {
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "error"
            });
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
    // 修改用户信息请求
    alterAdmin() {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/admin/alterAdmin", {
        params: {
          admin: this.adminAddUpdForm
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.initAdminTable();
          this.$message({
            showClose: true,
            message: res.data.data,
            type: "success"
          });
          this.adminAddUpdForm.visible = false;
        } else if (res.data.code == 401) {
          this.$message({
            showClose: true,
            message: res.data.data,
            type: "error"
          });
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
    handleResetPassword(index, row) {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/admin/resetAdminPassword", {
        params: {
          admin: row
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.initAdminTable();
          this.$message({
            showClose: true,
            message: res.data.data,
            type: "success"
          });
        } else if (res.data.code == 401) {
          this.$message({
            showClose: true,
            message: res.data.data,
            type: "error"
          });
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


    // change() {
    //   this.$forceUpdate()
    // },

    // initRoleList() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post(
    //       "/role/getAllRole",
    //       {
    //         params: {

    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.roleSelectiveList = res.data.object;
    //       } else if (res.data.code == 401) {
    //         this.$message({
    //           showClose: true,
    //           message: res.data.data,
    //           type: "error"
    //         });
    //         this.$router.push({ path: "/login" });
    //       } else {
    //         this.$alert(res.data.data, "错误", {
    //           confirmButtonText: "确定",
    //           type: "error",
    //           callback: action => { }
    //         });
    //       }
    //       loading.close();
    //     });
    // },
    // initGroupList() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post(
    //       "/group/getAllGroup",
    //       {
    //         params: {

    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.groupSelectiveList = res.data.object;
    //       } else if (res.data.code == 401) {
    //         this.$message({
    //           showClose: true,
    //           message: res.data.data,
    //           type: "error"
    //         });
    //         this.$router.push({ path: "/login" });
    //       } else {
    //         this.$alert(res.data.data, "错误", {
    //           confirmButtonText: "确定",
    //           type: "error",
    //           callback: action => { }
    //         });
    //       }
    //       loading.close();
    //     });
    // },

    // initCollegeSelectiveList() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post("/dictionary/getDictionaryItems", {
    //       params: {
    //         dictionaryType: "COLLEGE"
    //       }
    //     }, { headers: { token: this.token } })
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.collegeSelectiveList = res.data.object;
    //       } else if (res.data.code == 401) {
    //         this.$message({
    //           showClose: true,
    //           message: res.data.data,
    //           type: "error"
    //         });
    //         this.$router.push({ path: "/login" });
    //       } else {
    //         this.$alert(res.data.data, "错误", {
    //           confirmButtonText: "确定",
    //           type: "error",
    //           callback: action => {

    //           }
    //         });
    //       }
    //       loading.close();
    //     });
    // },
    // // role选择列表选择
    // initCheckStateList() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post("/dictionary/getDictionaryItems", {
    //       params: {
    //         dictionaryType: "ADMIN_CHECK_STATE"
    //       }
    //     }, { headers: { token: this.token } })
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.checkStateSelectiveList = res.data.object;
    //       } else if (res.data.code == 401) {
    //         this.$message({
    //           showClose: true,
    //           message: res.data.data,
    //           type: "error"
    //         });
    //         this.$router.push({ path: "/login" });
    //       } else {
    //         this.$alert(res.data.data, "错误", {
    //           confirmButtonText: "确定",
    //           type: "error",
    //           callback: action => {

    //           }
    //         });
    //       }
    //       loading.close();
    //     });
    // },





    // handleInitAdminRoleGroup(index, row) {
    //   this.adminRoleGroupForm.visible = true;
    //   this.adminRoleGroupAddUpdForm.adminId = row.adminId;
    //   this.initAdminRoleGroup()
    // },

    // initAdminRoleGroup() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post(
    //       "/role/getAdminRoleGroup",
    //       {
    //         params: {
    //           adminId: this.adminRoleGroupAddUpdForm.adminId
    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.adminRoleGroupTableData = res.data.object;
    //       } else if (res.data.code == 401) {
    //         this.$message({
    //           showClose: true,
    //           message: res.data.data,
    //           type: "error"
    //         });
    //         this.$router.push({ path: "/login" });
    //       } else if (res.data.code == 402) {
    //         this.$message({
    //           showClose: true,
    //           message: res.data.data,
    //           type: "error"
    //         });
    //         this.$router.push({ path: "/role" });
    //       } else {
    //         this.$alert(res.data.data, "错误", {
    //           confirmButtonText: "确定",
    //           type: "error",
    //           callback: action => { }
    //         });
    //       }
    //       loading.close();
    //     });
    // },
    // // adminRoleGroupId: "",
    // // roleId: "",
    // // groupId: "",
    // // adminId: "",
    // // checkState: "",
    // handleAddAdminRoleGroup() {
    //   this.adminRoleGroupAddUpdForm.title = "添加用户角色信息";
    //   this.adminRoleGroupAddUpdForm.visible = true;
    //   this.adminRoleGroupAddUpdForm.adminRoleGroupId = "";
    //   this.adminRoleGroupAddUpdForm.roleId = "";
    //   this.adminRoleGroupAddUpdForm.groupId = "";
    //   this.adminRoleGroupAddUpdForm.checkState = "";
    //   this.adminRoleGroupAddUpdForm.submitState = "Add";
    // },
    // // 处理修改用户
    // handleUpdAdminRoleGroup(index, row) {
    //   this.adminRoleGroupAddUpdForm.title = "修改用户信息";
    //   this.adminRoleGroupAddUpdForm.visible = true;

    //   this.adminRoleGroupAddUpdForm.adminRoleGroupId = row.adminRoleGroupId;
    //   this.adminRoleGroupAddUpdForm.roleId = row.roleId;
    //   this.adminRoleGroupAddUpdForm.groupId = row.groupId;
    //   this.adminRoleGroupAddUpdForm.checkState = row.checkState;

    //   this.adminRoleGroupAddUpdForm.submitState = "Upd";
    // },
    // // 删除用户请求
    // handleDeleteAdminRoleGroup(index, row) {
    //   this.adminRoleGroupAddUpdForm.adminRoleGroupId = row.adminRoleGroupId;
    //   // this.adminAddUpdForm.adminAccount = row.adminAccount;
    //   this.adminRoleGroupAddUpdForm.submitState = "Del";
    //   this.alterAdminRoleGroup();
    // },
    // // 修改用户信息请求
    // alterAdminRoleGroup() {
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios.post("/role/alterAdminRoleGroup", {
    //     params: {
    //       adminRoleGroup: this.adminRoleGroupAddUpdForm
    //     }
    //   }, { headers: { token: this.token } }).then(res => {
    //     if (res.data.code === 200) {
    //       this.initAdminRoleGroup();
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "success"
    //       });
    //       this.adminRoleGroupAddUpdForm.visible = false;
    //     } else if (res.data.code == 401) {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "error"
    //       });
    //       this.$router.push({ path: "/login" });
    //     } else if (res.data.code == 402) {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "error"
    //       });
    //       this.$router.push({ path: "/role" });
    //     } else {
    //       this.$alert(res.data.data, "错误", {
    //         confirmButtonText: "确定",
    //         type: "error",
    //         callback: action => {
    //         }
    //       });
    //     }
    //     loading.close();
    //   });
    // },


    downloadExcel(index, item) {
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
    excelImportOver(response, file, fileList) {
      if (response.code == 200) {
        this.$alert(response.data, "上传成功", {
          confirmButtonText: "确定",
          type: "success",
          callback: action => {
            this.initAdminTable();
            this.fileList = []
          }
        });
      } else if (res.data.code == 401) {
        this.$message({
          showClose: true,
          message: res.data.data,
          type: "error"
        });
        this.$router.push({ path: "/login" });
      } else if (res.data.code == 402) {
        this.$message({
          showClose: true,
          message: res.data.data,
          type: "error"
        });
        this.$router.push({ path: "/role" });
      } else {
        this.$alert(response.data, "上传失败", {
          confirmButtonText: "确定",
          type: "error",
          callback: action => {
            this.initAdminTable();
            this.fileList = []
          }
        });
      }
    },
    excelImportError(error, response, file, fileList) {
      this.$alert('服务器错误', '上传失败', {
        confirmButtonText: '确定',
        type: 'error',
        callback: action => {
          this.initDictionaryType();
          this.fileList = []
        }
      });
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
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

    // // 加载Role列表数据
    // this.initRoleList();
    // // 加载学院列表数据
    // this.initGroupList();
    // // 加载admin table data
    // this.initCheckStateList();

    this.initAdminTable();
    this.initRoleList();
    // this.initCollegeSelectiveList();
  }

}
</script>

<style  lang="scss">
#admin {
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
}
</style>