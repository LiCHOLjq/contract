<template>
  <div id="sharelogin">
    <v-header></v-header>
    <el-card class="login-wrap">
      <div slot="header" class="clearfix">
        <span>错误</span>
        <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
      </div>
      <i class="el-icon-warning"></i>
      <p style="margin-top:40px;font-size:20px">此链接分享内容不存在或已过期，无法访问！</p>
    </el-card>

  </div>
</template>

<script>
import Header from "../HeaderNoLogin.vue";
import store from "../../vuex/store.js";
export default {
  store,
  data: function () {
    return {

      share: {
        shareId: "",
      }

    };
  },
  components: {
    "v-header": Header
  },
  methods: {

  },
  created: function () {


    // localStorage.removeItem("admin");
    // localStorage.removeItem("token");

    //this.initCollegeSelectiveList();
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
#sharelogin {
  // height: 966px;
  //background: url("../assets/img/login-bg2.png") no-repeat center bottom;
  background-size: 100%;
  .el-card__body {
    color: #e1140a;
    text-align: center;
    font-size: 100px;
    padding-top: 40px;
  }
  .login-wrap {
    height: 380px;
    -webkit-border-radius: 5px;

    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    position: absolute;
    left: 50%;
    top: 50%;
    margin: -160px -400px auto;
    width: 800px;

    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .el-card__header {
    background-color: #e1140a;
    color: #fff;
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
    width: 300px;
    height: 160px;
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
}
</style>