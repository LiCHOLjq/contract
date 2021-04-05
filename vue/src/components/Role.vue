<template>
  <div id="role">
    <v-header></v-header>

    <div class="login-wrap" :style="loginWrapWidth">
      <div class="ms-login">
        <div class="ms-title">我的角色</div>
        <el-row style="text-align:center">
          <el-col v-for="item in adminRoleList" :key="item.adminRoleGroupId" class="index-col" :span="itemSpan">
            <el-card shadow="hover" :class="item.checkState == 'admin_check_state_5' ? 'dlist' : 'dlist-ban' " @click.native="accessRole(item)">
              <i class="el-icon-s-custom"></i>
              <h3>{{item.groupObj == null ? '' : item.groupObj.groupName}}</h3>
              <h3>{{item.roleObj == null ? '' : item.roleObj.roleName}}</h3>
              <h4>{{item.checkStateObj == null ? '' : item.checkStateObj.dictionaryName}}</h4>
            </el-card>
            <el-button class="cancle" @click="cancelApply(item)" v-if="item.checkState != 'admin_check_state_5'" type="text">取消申请</el-button>
            <el-button @click="getTimeList(item)" type="text">审核状态</el-button>
          </el-col>
        </el-row>
        <el-row style="text-align:center;">
          <el-button type="primary" @click="adminRole.visible = true;">申请新角色</el-button>
        </el-row>
      </div>
    </div>

    <el-dialog title="申请新角色" :visible.sync="adminRole.visible" width="500px">
      <el-row>
        <el-form style="padding-left:20px;padding-right:40px">
          <el-form-item label="角色:" :label-width="adminRole.formLabelWidth">
            <el-select v-model="adminRole.roleId" placeholder="选择角色" style="width: 180px;">
              <el-option v-for="item in roleList" :key="item.roleId" :label="item.roleName" :value="item.roleId"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="adminRole.roleId != 'role_professor'" label="招生组:" :label-width="adminRole.formLabelWidth">
            <el-select v-model="adminRole.groupId" placeholder="选择招生组" style="width: 180px;">
              <el-option v-for="item in groupList" :key="item.groupId" :label="item.groupName" :value="item.groupId"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="adminRole.visible= false">取 消</el-button>
        <el-button type="primary" @click="submitApply">提交申请</el-button>
      </div>
    </el-dialog>

    <el-drawer title="审核状态" :visible.sync="timeLineVisible">
      <el-timeline>
        <el-timeline-item v-for="(timeline, index) in timeLineList" :key="index" :icon="timeline.icon" :type="timeline.type" size="large" :timestamp="timeline.time">
          <p>{{timeline.text}}</p>
          <p>{{timeline.admin}}</p>
        </el-timeline-item>
      </el-timeline>
    </el-drawer>

  </div>
</template>

<script>
import Header from "./Header.vue";
import store from "../vuex/store.js";
export default {
  store,
  data: function () {
    return {

      itemSpan: 24,
      loginWrapWidth: "width:340px;",

      roleList: [],
      groupList: [],
      adminRoleList: [],
      timeLineVisible: false,
      timeLineList: [],

      adminRole: {
        visible: false,
        formLabelWidth: "120px",
        roleId: "",
        groupId: "",
      },
    };
  },
  components: {
    "v-header": Header
  },
  methods: {
    setItemSpan(num) {
      if (num <= 1) {
        this.itemSpan = 24;
        this.loginWrapWidth = "width:340px;"
      } else if (num <= 4) {
        this.itemSpan = 12;
        this.loginWrapWidth = "width:600px;"
      } else if (num <= 6) {
        this.itemSpan = 8;
        this.loginWrapWidth = "width:860px;"
      } else {
        this.itemSpan = 6;
        this.loginWrapWidth = "width:1100px;"
      }
    },
    getRoleList() {
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
            this.roleList = res.data.object;
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
    getGroupList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/group/getAllGroup",
          {
            params: {

            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.groupList = res.data.object;
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
    getAdminRoleList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/role/getAdminRoleWeb",
          {
            params: {

            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.adminRoleList = res.data.object;
            this.setItemSpan(this.adminRoleList.length)
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
    getTimeList(item) {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/role/getAdminRoleTimeList",
          {
            params: {
              adminRoleGroup: item
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.timeLineList = res.data.object;
            this.timeLineVisible = true;
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

    submitApply() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/role/applyRole",
          {
            params: {
              adminRoleGroup: this.adminRole,
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.getAdminRoleList();
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "success"
            });
            this.adminRole.visible = false;
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
    accessRole(item) {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/role/accessRoleWeb",
          {
            params: {
              adminRoleGroup: item,
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            localStorage.setItem("token", res.data.object.token);
            this.token = res.data.object.token;
            localStorage.setItem("role", (item.groupObj == null ? '' : item.groupObj.groupName) + " " + (item.roleObj == null ? '' : item.roleObj.roleName));
            this.getPowerList();

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
    getPowerList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/power/getPowerListWeb",
          {
            params: {

            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            localStorage.setItem("powerList", JSON.stringify(res.data.object));
            this.$router.push({ path: "/home" });
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
              callback: action => { }
            });
          }
          loading.close();
        });
    },
    cancelApply(item) {
      this.$confirm('您确定取消此申请吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        const loading = this.$loading(this.$store.state.loadingOption1);
        this.axios
          .post(
            "/role/cancelRole",
            {
              params: {
                adminRoleGroup: item,
              }
            },
            { headers: { token: this.token } }
          )
          .then(res => {
            if (res.data.code === 200) {
              this.getAdminRoleList();
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


      }).catch(() => {

      });


    },

  },
  created: function () {
    localStorage.setItem("role", null);
    this.token = localStorage.getItem("token");
    if (this.token == "" || this.token == null) {
      this.$router.push({ path: "/login" });
      return;
    }
    this.setItemSpan(0);
    this.getAdminRoleList();
    this.getGroupList();
    this.getRoleList();
    // var adminId = localStorage.getItem("adminId");
    // var adminName = localStorage.getItem("adminName");
    // var adminPassword = localStorage.getItem("adminPassword");
    // if (adminId != null) {
    //   this.form.adminName = adminName;
    //   this.form.adminPassword = adminPassword;
    //   this.submitForm();
    // } 
  }
};
</script>

<style  lang="scss">
#role {
  // height: 966px;
  //background: url("../assets/img/login-bg2.png") no-repeat center bottom;
  background-size: 100%;

  .login-wrap {
    //height: 650px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 100px auto;
    //width: 600px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .ms-title {
    margin: -30px auto 40px auto;
    text-align: center;
    font-size: 30px;
    color: #505458;
  }
  .ms-login {
    //position: absolute;
    left: 50%;
    top: 50%;
    // width: 300px;
    // height: 160px;
    //margin: -150px 0 0 -190px;
    padding: 40px;
    border-radius: 5px;
    background: #fff;
  }
  .login-btn {
    text-align: center;
    margin-top: 50px;
  }
  .login-btn button {
    width: 100%;
  }
  .el-form-item {
    margin-top: 30px;
  }
  .el-form-item__label {
    text-align: left;
  }

  #inds {
    margin: 30px;
  }
  .index-col {
    padding: 20px;
  }

  .dlist {
    height: 200px;
  }
  .dlist i {
    font-size: 40px;
    color: #606266;
    display: block;
    text-align: center;
    padding: 18px;
  }
  .dlist:hover {
    background-color: rgb(241, 241, 241);
  }
  .dlist:hover i {
    color: #409eff !important;
  }
  .dlist:hover h3 {
    color: #409eff !important;
  }
  .dlist:hover h4 {
    color: #409eff !important;
  }
  .dlist h3 {
    color: #606266;
    margin-bottom: 10px;
    margin-top: 10px;
    text-align: center;
  }
  .dlist h4 {
    color: #606266;
    margin-bottom: 10px;
    margin-top: 10px;
    text-align: center;
  }

  .dlist-ban {
    height: 200px;
  }
  .dlist-ban i {
    font-size: 40px;
    color: #9ca0a5;
    display: block;
    text-align: center;
    padding: 18px;
  }
  .dlist-ban h3 {
    color: #9ca0a5;
    margin-bottom: 10px;
    margin-top: 10px;
    text-align: center;
  }
  .dlist-ban h4 {
    color: #9ca0a5;
    margin-bottom: 10px;
    margin-top: 10px;
    text-align: center;
  }

  .index-title {
    font-size: 32px;
    text-align: center;
  }

  .el-dialog__header {
    background-color: #043e6b;
    padding: 15px 20px 15px;
  }
  .el-dialog__title {
    line-height: 24px;
    font-size: 16px;
    color: #fff;
  }
  .el-dialog__headerbtn .el-dialog__close {
    color: #fff;
  }
  .cancle {
    color: #f56c6c;
  }

  .cancle:hover {
    color: #f09d9d;
  }
}
</style>