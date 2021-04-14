<template>
  <div id="sharelogin">
    <v-header></v-header>
    <el-card class="login-wrap">
      <div slot="header" class="clearfix">
        <span>邀请您下载文件</span>
        <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
      </div>
      <el-form style="padding-left: 20px;padding-right: 20px;" :model="form" :rules="rules" ref="ruleForm" label-width="0px">
        <p>请输入密码：</p>
        <el-form-item>
          <el-input style="width:300px" v-model="share.sharePassword" autocomplete="off"></el-input>
          <el-button style="float:right;" type="primary" @click="submitForm()">验证</el-button>

        </el-form-item>

        <!-- <el-button type="text" style="padding:0px;margin-bottom:10px;text-align: right;" @click="signForm.visible = true;getCode();">注册新账号</el-button> -->
      </el-form>
    </el-card>

    <!-- <el-dialog title="注册新账号" :visible.sync="signForm.visible" width="1000px">
      <el-row>
        <el-col :span="12">
          <el-form style="padding-left:20px;padding-right:40px">
            <el-form-item label="工号：" :label-width="signForm.formLabelWidth">
              <el-input v-model="signForm.adminAccount" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="设置密码：" :label-width="signForm.formLabelWidth">
              <el-input type="password" v-model="signForm.adminPassword" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="再次输入：" :label-width="signForm.formLabelWidth">
              <el-input type="password" v-model="signForm.adminPasswordAgn" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="手机号：" :label-width="signForm.formLabelWidth">
              <el-input v-model="signForm.adminPhone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="验证码：" :label-width="signForm.formLabelWidth">
              <el-input style="width:190px" v-model="signForm.code" autocomplete="off"></el-input>
              <img style="float:right" :src="imgurl" @click="getCode" height="40px" width="100px">
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="12">
          <el-form>
            <el-form-item label="姓名：" :label-width="signForm.formLabelWidth">
              <el-input v-model="signForm.adminName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="性别：" :label-width="signForm.formLabelWidth">
              <el-select v-model="signForm.adminSex" placeholder="选择用户性别">
                <el-option label="男" value="男"></el-option>
                <el-option label="女" value="女"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="身份证号：" :label-width="signForm.formLabelWidth">
              <el-input v-model="signForm.adminIdentityNumber" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="学院/部门：" :label-width="signForm.formLabelWidth">
              <el-select v-model="signForm.adminCollege" placeholder="选择学院/部门" style="width: 280px;">
                <el-option :key="''" :label="'无'" :value="''"></el-option>
                <el-option v-for="item in collegeSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="职称/职务：" :label-width="signForm.formLabelWidth">
              <el-input v-model="signForm.adminPosition" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="学生(本/硕/博)：" :label-width="signForm.formLabelWidth">
              <el-input v-model="signForm.adminDegree" autocomplete="off"></el-input>
            </el-form-item>

          </el-form>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="signForm.visible= false">取 消</el-button>
        <el-button type="primary" @click="Sign">注 册</el-button>
      </div>
    </el-dialog> -->
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
        sharePassword: ""
      }
      // imgurl: "",
      // savePassword1: "",
      // savePassword2: "",
      // token: "",
      // signForm: {
      //   visible: false,
      //   formLabelWidth: "120px",
      //   adminAccount: "",
      //   adminPassword: "",
      //   adminPasswordAgn: "",
      //   adminName: "",
      //   adminSex: "",
      //   adminPhone: "",
      //   adminCollege: "",
      //   adminPosition: "",
      //   adminDegree: "",
      //   adminIdentityNumber: "",
      //   code: "",
      //   AESCode: ""
      // },
      // collegeSelectiveList: [],
      // form: {
      //   adminAccount: "",
      //   adminPassword: ""
      // },
      // rules: {
      //   adminName: [
      //     { required: true, message: "请输入账号", trigger: "blur" }
      //   ],
      //   adminPassword: [
      //     { required: true, message: "请输入密码", trigger: "blur" }
      //   ]
      // }
    };
  },
  components: {
    "v-header": Header
  },
  methods: {
    getShareAvailable() {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios
        .post("/user/getShareAvailable", {
          params: {
            share: this.share
          }
        }
        )
        .then(res => {
          if (res.data.code === 200) {

          } else {
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "error"
            });
            this.$router.push({ path: "/share/error" });
          }
          loading.close();
        });
    },

    submitForm() {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios
        .post("/user/accessShare", {
          params: {
            share: this.share
          }
        }
        )
        .then(res => {
          if (res.data.code === 200) {
            localStorage.setItem("admin", JSON.stringify(res.data.object.admin));
            localStorage.setItem("shaken", res.data.shaken);
            this.$router.push({ path: "/share/items/" + this.share.shareId });
          } else if (res.data.code === 401) {
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "error"
            });
          } else {
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "error"
            });
            this.$router.push({ path: "/share/error" });
          }
          loading.close();
        });
    },
    // getPowerList() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post(
    //       "/power/getPowerList",
    //       {
    //         params: {

    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         localStorage.setItem("powerList", JSON.stringify(res.data.object));
    //         this.$router.push({ path: "/home" });
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

    // getCode() {
    //   var that = this;
    //   this.axios.get('verifyCode', {
    //     headers: {
    //       'Content-Type': 'application/x-www-form-urlencoded',
    //     },
    //     responseType: "arraybuffer", // 关键 设置 响应类型为二进制流
    //   }).then(function (response) {  // 将后台的图片二进制流传华为base64
    //     that.signForm.AESCode = response.headers.verifycode;
    //     return 'data:image/png;base64,' + btoa(
    //       new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
    //     );
    //   }).then(data => {
    //     that.imgurl = data; // data即为图片地址
    //   });
    // },
    // Sign() {
    //   this.savePassword1 = this.signForm.adminPassword;
    //   this.savePassword2 = this.signForm.adminPasswordAgn;
    //   this.signForm.adminPassword = this.$md5(this.signForm.adminPassword)
    //   this.signForm.adminPasswordAgn = this.$md5(this.signForm.adminPasswordAgn)
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios
    //     .post("/adminSign", {
    //       params: {
    //         admin: this.signForm
    //       }
    //     }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.$alert(res.data.data, "注册", {
    //           confirmButtonText: "确定",
    //           type: "success",
    //           callback: action => {
    //             this.signForm.visible = false;
    //           }
    //         });
    //       } else {
    //         this.$alert(res.data.data, "注册", {
    //           confirmButtonText: "确定",
    //           type: "error",
    //           callback: action => {
    //             this.getCode();
    //             this.signForm.code = "";
    //             this.signForm.adminPassword = this.savePassword1;
    //             this.signForm.adminPasswordAgn = this.savePassword2;
    //           }
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
  },
  created: function () {

    // localStorage.removeItem("admin");
    localStorage.removeItem("shaken");

    if (this.$route.params.shareId == null) {
      this.$router.push({ path: "/share/error" });
    } else {
      this.share.shareId = this.$route.params.shareId;
      this.getShareAvailable();
    }
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

  .login-wrap {
    height: 240px;
    -webkit-border-radius: 5px;

    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 340px auto;
    width: 460px;

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