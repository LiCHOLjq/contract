<template>
  <div class="header">
    <el-menu class="el-menu-demo" mode="horizontal" @select="handleSelect" background-color="#E0E0E0" text-color="#000000" active-text-color="#000000">
      <img class="head-img" width="250" height="60" src="../assets/img/logo-ow-bg.png" />

      <el-submenu index="1">
        <template slot="title">{{ this.adminName }}</template>
        <el-menu-item index="1-1">修改密码</el-menu-item>
        <!-- <el-menu-item index="1-3">更换角色</el-menu-item> -->
        <el-menu-item index="1-2">退出登录</el-menu-item>
      </el-submenu>
      <div class="head-title">标案合同管理系统</div>
    </el-menu>
    <el-dialog :title="title" :visible.sync="dialogFormVisible" width="500px">
      <el-form :model="form">
        <el-form-item label="原密码：" :label-width="formLabelWidth">
          <el-input type="password" v-model="form.adminPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码：" :label-width="formLabelWidth">
          <el-input type="password" v-model="form.adminPasswordNew" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="再次输入：" :label-width="formLabelWidth">
          <el-input type="password" v-model="form.adminPasswordAgn" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="Alter">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import store from "../vuex/store.js";
export default {
  store,
  data() {
    return {
      token: "",
      title: "修改密码",
      formLabelWidth: "90px",
      adminName: "",
      dialogFormVisible: false,
      adminName: "",
      form: {
        // adminId: "",
        // adminName: "",
        adminPassword: "",
        adminPasswordNew: "",
        adminPasswordAgn: ""
      }
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      if (keyPath[0] == "1") {
        if (keyPath[1] == "1-1") {
          this.form.adminPassword = "";
          this.form.adminPasswordNew = "";
          this.form.adminPasswordAgn = "";
          this.title = "修改密码";
          this.dialogFormVisible = true;
        }
        if (keyPath[1] == "1-2") {
          localStorage.removeItem("admin");
          localStorage.removeItem("token");

          this.$router.push({ path: "/login" });
        }
        // if (keyPath[1] == "1-3") {
        //   this.$router.push({ path: "/role" });
        // }
      }
    },
    Alter() {
      // if (this.form.adminPassword != this.$store.state.admin.adminPassword) {
      //   this.$message({
      //     showClose: true,
      //     message: "原密码不正确",
      //     type: "error"
      //   });
      //   this.form.adminPassword = "";
      //   this.form.adminPasswordNew = "";
      //   this.form.adminPasswordAgn = "";
      //   return;
      // }
      if (this.form.adminPasswordNew != this.form.adminPasswordAgn) {
        this.$alert("两次密码输入不一致", "错误", {
          confirmButtonText: "确定",
          type: "error",
          callback: action => { }
        });
        // this.form.adminPassword = "";
        // this.form.adminPasswordNew = "";
        // this.form.adminPasswordAgn = "";
        return;
      }
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/changePassword",
          {
            params: {
              passwordOld: this.$md5(this.form.adminPassword),
              passwordNew: this.$md5(this.form.adminPasswordNew)
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.$alert("密码修改成功，请重新登陆", "提示", {
              confirmButtonText: "确定",
              type: "success",
              callback: action => {
                this.$router.push({ path: "/login" });
              }
            });
          } else if (res.data.code == 401) {
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
      this.dialogFormVisible = false;
    }
  },
  created: function () {
    this.token = localStorage.getItem("token");
    //console.log(localStorage.getItem("admin"))
    this.adminName = JSON.parse(localStorage.getItem("admin")).adminName;
    if (JSON.parse(localStorage.getItem("admin")).adminPassword == "53fbd5ce1202fbdc107a256be285ae2e") {
      this.form.adminPassword = "";
      this.title = "您的密码为初始密码，请尽快修改密码"
      this.dialogFormVisible = true;
    }
  }
};
</script>
<style scoped>
.head-img {
  float: left;
}
.head-title {
  height: 60px;
  line-height: 60px;
  font-size: 25px;
  width: 400px;
  text-align: center;
  color: #000;
  z-index: 99;

  margin: 0px auto;
}
.head-left {
  float: right;
  width: 625px;
}
.head-left li {
  float: left;
}
.el-menu--horizontal > .el-menu-item {
  float: right;
}
.el-menu--horizontal > .el-submenu {
  float: right;
}
</style>

