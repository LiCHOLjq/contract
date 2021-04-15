<template>
  <div id="shareitem">
    <v-header></v-header>
    <el-card class="login-wrap">
      <div slot="header" class="clearfix">
        <span>{{share.shareAdmin + "邀请您" + ( share.shareTypeObj == null ? '' : share.shareTypeObj.dictionaryName == null ? '' : share.shareTypeObj.dictionaryName)}}</span>
        <span style="float:right;">{{"有效期：" + share.shareDateStr}}</span>

      </div>
      <el-row>
        <el-col :span="share.shareType == 'share_type_download' ? 24 : share.shareType == 'share_type_upload' ? 17 : 24">
          <el-button v-if="share.shareType == 'share_type_download'" style="margin-bottom:10px"  type="info" @click="handleDownload(scope.$index, scope.row)">下载全部</el-button>
          <el-table v-bind:data="share.shareAgreementList" border style="width: 100%">
            <el-table-column label="名称">
              <template slot-scope="scope">
                <span style="margin-left: 10px">
                  <el-button v-if="share.shareType == 'share_type_download'"  type="text" @click="handleDownload(scope.$index, scope.row)">{{ (scope.row.agreementName == null ? '' : scope.row.agreementName) + (scope.row.agreementExtend == null ? '' : scope.row.agreementExtend) }}</el-button>
                </span>
              </template>
            </el-table-column>
            <el-table-column  v-if="share.shareType == 'share_type_download'"  label="类型" width="180">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.agreementType == null ? '' : scope.row.agreementType == null ? '' : scope.row.agreementType }}</span>
              </template>
            </el-table-column>
            <el-table-column label="上传时间" width="200">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.agreementUploadDate == null ? '' :( scope.row.agreementUploadDate)}}</span>
              </template>
            </el-table-column>
            <el-table-column  v-if="share.shareType == 'share_type_download'"  label="操作" width="120">
              <template slot-scope="scope">
                <el-button v-if="!scope.row.agreementDelete" size="mini" type="info" @click="handleDownload(scope.$index, scope.row)">下载</el-button>
              </template>
            </el-table-column>
          </el-table>
          </el-col>
          <el-col :span="7" style="padding-left:40px;">
            <h2>上传合同</h2>
            <el-form :inline="true" :model="agreementSelectForm" class="demo-form-inline">
                  <el-form-item label="您的姓名">
                    <el-input style="width:300px;" v-model="agreement.agreementUploadAdmin" :placeholder="'请填您的姓名'" autocomplete="off"></el-input>
                  </el-form-item>

                  <el-form-item label="合同名称">
                    <el-input style="width:300px;" v-model="agreement.agreementName" :placeholder="'请填写合同名称'" autocomplete="off"></el-input>
                  </el-form-item>
                  </el-form>
                  <el-upload class="upload-demo" ref="upload" drag :action="axios.defaults.baseURL + '/agreement/addAgreement'" multiple :on-exceed="handleExceed" :on-preview="handlePreview" :on-remove="handleRemove" :on-change="onFileChange" :file-list="fileList" :limit="1" :on-success="excelImportOver" :on-error="excelImportError" :auto-upload="false" :headers="{'token': token}" :data="
                  {params:JSON.stringify({
                    agreement:agreement
                  })
                  }">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击选择文件</em></div>
                  </el-upload>
                  <el-button style="margin-left:120px" icon="el-icon-upload2" type="primary" @click="submitUpload">确定上传</el-button>
          </el-col>
          </el-row>
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
      shaken:"",
      share: {
        shareId: "",
        sharePassword: "",
        shareAdmin:"",
        shareAdmin:"",
        shareType:"",
        shareTypeObj:null,
        shareDateStr:"",
        shareAgreementList:[],
      },
      agreement:{
        agreementUploadAdmin:"",
        agreementName:""
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
     getShareDetails() {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios
        .post("/user/getShareDetails", {
          params: {
            shareId: this.share.shareId
          }
        }, { headers: { token: this.shaken } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.share= res.data.object;
           
          } else if (res.data.code === 401) {

            this.$router.push({ path: "/share/login/"+this.share.shareId });
          } else{
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


  },
  created: function () {
    if (this.$route.params.shareId == null) {
      this.$router.push({ path: "/share/error" });
    } else {
      this.share.shareId = this.$route.params.shareId;

    }
    this.shaken = localStorage.getItem("shaken");
    if (this.shaken == "" || this.shaken == null) {
      this.$router.push({ path: "/share/login/"+this.share.shareId });
    }
    this.getShareDetails();


  }
};
</script>

<style  lang="scss">
#shareitem {
  // height: 966px;
  //background: url("../assets/img/login-bg2.png") no-repeat center bottom;
  background-size: 100%;
  // .el-card__body {
  //   color: #e1140a;
  //   text-align: center;
  //   font-size: 100px;
  //   padding-top: 40px;
  // }
  .login-wrap {
    height: auto;
    -webkit-border-radius: 5px;

    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
        position: absolute;
    left: 50%;
    top: 80px;
    margin-left:-800px;
    width: 1600px;

    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .el-form-item{
    margin-top:0px ;
    margin-bottom:10px ;
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