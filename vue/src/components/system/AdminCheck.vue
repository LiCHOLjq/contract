<template>
  <div id="adminCheck">
    <el-form :inline="true" :model="adminSelectForm" class="demo-form-inline">
      <el-form-item label="工号: ">
        <el-input v-model="adminSelectForm.adminAccount" autocomplete="off" style="width:120px"></el-input>
      </el-form-item>
      <el-form-item label="姓名: ">
        <el-input v-model="adminSelectForm.adminName" autocomplete="off" style="width:120px"></el-input>
      </el-form-item>
      <el-form-item label="电话: ">
        <el-input v-model="adminSelectForm.adminPhone" autocomplete="off" style="width:120px"></el-input>
      </el-form-item>
      <el-form-item label="学院: ">
        <el-input v-model="adminSelectForm.adminCollege" autocomplete="off" style="width:150px"></el-input>
      </el-form-item>
      <el-form-item label="职位: ">
        <el-input v-model="adminSelectForm.adminPosition" autocomplete="off" style="width:90px"></el-input>
      </el-form-item>
      <el-form-item label="学位: ">
        <el-input v-model="adminSelectForm.adminDegree" autocomplete="off" style="width:90px"></el-input>
      </el-form-item>

      <el-form-item label="角色：">
        <el-select v-model="adminSelectForm.roleId" placeholder="选择角色" style="width: 120px;">
          <el-option label="全部" value=""></el-option>
          <el-option v-for="item in roleSelectiveList" :key="item.roleId" :label="item.roleName" :value="item.roleId"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="招生组：">
        <el-select v-model="adminSelectForm.groupId" placeholder="选择招生组" style="width: 120px;">
          <el-option label="全部" value=""></el-option>
          <el-option v-for="item in groupSelectiveList" :key="item.groupId" :label="item.groupName" :value="item.groupId"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="审核状态：">
        <el-select v-model="adminSelectForm.checkState" placeholder="选择审核状态" style="width: 200px;">
          <el-option label="全部" value=""></el-option>
          <el-option v-for="item in checkStateSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="adminPage.current=1;
            initAdminTable();">
          查询
        </el-button>
        <!-- <el-button type="success" @click="excelForm.visible = true">Excel</el-button> -->
      </el-form-item>
    </el-form>
    <el-row>
      <el-col class="main-col" :span="24">
        <!-- <el-button size="mini" icon="el-icon-plus" type="primary" @click="handleAddAdmin()">
          添加
        </el-button> -->
        <el-table v-bind:data="adminTableData" border style="width: 100%">
          <el-table-column label="工号" width="140">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.adminObj == null ? '' : scope.row.adminObj.adminAccount == null ? '' : scope.row.adminObj.adminAccount }}</span>
            </template>
          </el-table-column>
          <el-table-column label="姓名">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.adminObj == null ? '' : scope.row.adminObj.adminName == null ? '' : scope.row.adminObj.adminName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="电话" width="140">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.adminObj == null ? '' : scope.row.adminObj.adminPhone == null ? '' : scope.row.adminObj.adminPhone }}</span>
            </template>
          </el-table-column>
          <el-table-column label="学院" width="240">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.adminObj == null ? '' : scope.row.adminObj.adminCollegeObj == null ? '' : scope.row.adminObj.adminCollegeObj.dictionaryName  == null ? '' : scope.row.adminObj.adminCollegeObj.dictionaryName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="职位" width="120">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.adminObj == null ? '' : scope.row.adminObj.adminPosition == null ? '' : scope.row.adminObj.adminPosition }}</span>
            </template>
          </el-table-column>
          <el-table-column label="学位" width="120">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.adminObj == null ? '' : scope.row.adminObj.adminDegree == null ? '' : scope.row.adminObj.adminDegree }}</span>
            </template>
          </el-table-column>
          <el-table-column label="角色" width="220">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.roleObj == null ? '' : scope.row.roleObj.roleName == null ? '' : scope.row.roleObj.roleName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="招生组" width="220">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.groupObj == null ? '' : scope.row.groupObj.groupName  == null ? '' : scope.row.groupObj.groupName}}</span>
            </template>
          </el-table-column>
          <el-table-column label="审核状态" width="140">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.checkStateObj == null ? '' : scope.row.checkStateObj.dictionaryName == null ? '' : scope.row.checkStateObj.dictionaryName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="360">
            <template slot-scope="scope">
              <el-button size="mini" :disabled="!scope.row.canCheck" type="success" @click="handleCheck(scope.$index, scope.row)">审核通过</el-button>
              <el-button size="mini" :disabled="!scope.row.canCheck" type="danger" @click="handleUnCheck(scope.$index, scope.row)">审核驳回</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
          <el-pagination background @size-change="handleAdminTableSizeChange" @current-change="handleAdminTableCurrentChange" :hide-on-single-page="false" :current-page="adminPage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="adminPage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="adminPage.totalCount"></el-pagination>
        </div>
      </el-col>
    </el-row>
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
        adminPhone: "",
        adminCollege: "",
        adminPosition: "",
        adminDegree: "",
        adminUseful: "",
        roleId: "",
        groupId: "",
        checkState: "",
      },
      // role可选列表
      roleSelectiveList: [],
      // collage可选列表
      groupSelectiveList: [],
      checkStateSelectiveList: [],

      // 用户表数据
      adminTableData: [],
      // 用户表view页面配置
      adminPage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },

    }
  },
  methods: {
    initRoleList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/role/getApplicableRole",
          {
            params: {

            }
          },
          { headers: { token: this.token } }
        )
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
              callback: action => { }
            });
          }
          loading.close();
        });
    },
    initGroupList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/group/getAllGroupByToken",
          {
            params: {

            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.groupSelectiveList = res.data.object;
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
              callback: action => { }
            });
          }
          loading.close();
        });
    },


    // role选择列表选择
    initCheckStateList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItems", {
          params: {
            dictionaryType: "ADMIN_CHECK_STATE"
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.checkStateSelectiveList = res.data.object;
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
    // 用户信息表数据请求
    initAdminTable() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios.post("/role/getAdminRoleGroupBySearch", {
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
          } else if (res.data.code == 402) {
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "error"
            });
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

    // 处理增加用户信息
    handleCheck(index, row) {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/role/checkRole", {
        params: {
          adminRoleGroup: row
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
        } else if (res.data.code == 402) {
          this.$message({
            showClose: true,
            message: res.data.data,
            type: "error"
          });
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
    // 处理修改用户
    handleUnCheck(index, row) {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/role/unCheckRole", {
        params: {
          adminRoleGroup: row
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
        } else if (res.data.code == 402) {
          this.$message({
            showClose: true,
            message: res.data.data,
            type: "error"
          });
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
    handleAdminTableSizeChange(val) {
      this.adminPage.showCount = val;
      this.adminPage.current = 1;
      this.initAdminTable();
    },
    handleAdminTableCurrentChange(val) {
      this.adminPage.current = val;
      this.initAdminTable();
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
    this.initAdminTable();
    // 加载Role列表数据
    this.initRoleList();
    // 加载学院列表数据
    this.initGroupList();
    // 加载admin table data
    this.initCheckStateList();
  }

}
</script>

<style  lang="scss">
#adminCheck {
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