<template>
  <div id="project">
    <div class="login-wrap">
      <el-row>
        <el-col :span="24">
          <el-button style="float:left;" type="primary" icon="el-icon-back" @click="back">返回</el-button>
          <div class="ms-login">
            <el-form :model="form" :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm">
              <div class="ms-title">{{agreement.submitState == "Add" ? "添加新合同" : agreement.submitState == "Upd" ? "修改合同信息" : ""}}</div>
              <el-row>
                <el-col :span="12">
                  <h1>合同名称</h1>
                  <el-form-item style="width:650px">
                    <el-input style="width:650px" v-model="agreement.agreementName" :placeholder="'请填写合同名称'" autocomplete="off"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <h1>合同类型</h1>
                  <el-form-item style="width:300px">
                    <el-select style="width:300px" v-model="agreement.agreementType" placeholder="选择合同类型">
                      <el-option v-for="item in agreementTypeSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <h1>是否信创</h1>
                  <el-form-item style="width:300px">
                    <el-select style="width:300px" v-model="agreement.agreementInnovation" placeholder="选择是否信创">

                      <el-option label="否" :value="false"></el-option>
                      <el-option label="是" :value="true"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>

                <el-col :span="6">
                  <h1>客户名称</h1>
                  <el-form-item style="width:300px">
                    <el-input :placeholder="'请填写客户名称'" v-model="agreement.agreementProvider" autocomplete="off"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <h1>合同乙方</h1>
                  <el-form-item style="width:300px">
                    <el-select style="width:300px" v-model="agreement.agreementClient" placeholder="请选择合同乙方">
                      <el-option v-for="item in agreementClientSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <h1>签约日期</h1>
                  <el-form-item style="width:300px">
                    <el-date-picker style="width:300px" v-model="agreement.agreementSignDateStr" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <h1>总金额(元)</h1>
                  <el-form-item style="width:300px">
                    <el-input-number style="width:300px" v-model="agreement.agreementAmount"></el-input-number>
                  </el-form-item>
                </el-col>
              </el-row>

              <h1>合同产品</h1>
              <el-button icon="el-icon-plus" type="primary" circle @click="handleAddProduct()" style="margin-top:10px;"></el-button>
              <el-row class="auto-table" v-if="productList.length > 0">
                <el-col :span="5">
                  <p>产品类型</p>
                </el-col>
                <el-col :span="5">
                  <p>产品系列</p>
                </el-col>
                <el-col :span="6">
                  <p>产品型号</p>
                </el-col>
                <el-col :span="5">
                  <p>产品数量</p>
                </el-col>
                <el-col :span="3">
                  <p></p>
                </el-col>
              </el-row>
              <el-row v-for="(item,index) in productList" :key="index" class="auto-table">
                <el-col :span="5">
                  <el-form-item>
                    <el-select v-model="item.productType" @change=" handelproductTypeChange(index)" placeholder="选择产品类型">
                      <el-option v-for="items in productTypeSelectiveList" :key="items.dictionaryId" :label="items.dictionaryName" :value="items.dictionaryId"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item>
                    <el-select v-model="item.productSeries" placeholder="选择产品系列">
                      <el-option v-for="items in item.productSeriesSelectiveList" :key="items.dictionaryId" :label="items.dictionaryName" :value="items.dictionaryId"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item>
                    <el-input v-model="item.productModel" autocomplete="off" :placeholder="'请填写产品型号'"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item>
                    <el-input-number style="width: 150px;" v-model="item.productNumber"></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :span="3">
                  <el-form-item>
                    <el-col :span="8">
                      <el-button :disabled="index==0" type="primary" icon="el-icon-top" circle style="margin:auto;" @click="handleUpProduct(index)"></el-button>
                    </el-col>
                    <el-col :span="8">
                      <el-button :disabled="index==productList.length-1" type="primary" icon="el-icon-bottom" circle style="margin:auto;" @click="handleDownProduct(index)"></el-button>
                    </el-col>
                    <el-col :span="8">
                      <el-button type="danger" icon="el-icon-delete" circle style="margin:auto;" @click="handleDeleteProduct(index)"></el-button>
                    </el-col>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row class="auto-table">

                <el-col :span="agreement.submitState == 'Add' ? 17 : 24">
                  <h1>备注</h1>
                  <el-form-item>
                    <el-input type="textarea" :style="agreement.submitState == 'Add' ? 'width:950px' : ''" :rows="8" v-model="agreement.agreementText" autocomplete="off"></el-input>
                  </el-form-item>
                </el-col>
                <el-col v-if="agreement.submitState == 'Add'" :span="7">
                  <h1>上传文件</h1>
                  <el-upload class="upload-demo" ref="upload" drag :action="axios.defaults.baseURL + '/agreement/addAgreement'" multiple :on-exceed="handleExceed" :on-preview="handlePreview" :on-remove="handleRemove" :on-change="onFileChange" :file-list="fileList" :limit="1" :on-success="excelImportOver" :on-error="excelImportError" :auto-upload="false" :headers="{'token': token}" :data="
                  {params:JSON.stringify({
                    agreement:agreement,
                    productList:productList
                  })
                  }">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击选择文件</em></div>
                  </el-upload>

                  <!-- <el-upload class="upload-demo" ref="upload" :action="axios.defaults.baseURL + '/admin/importAdminExcel'" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="fileList" :limit="1" :on-success="excelImportOver" :on-error="excelImportError" :auto-upload="false" :headers="{'token': token} ">
                    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                    <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传xlsx文件</div>
                    <div slot="tip" class="el-upload__tip">此导入功能为追加模式</div>
                  </el-upload> -->
                </el-col>
              </el-row>
              <el-row class="auto-table">
                <p v-if="!isAdd">
                  {{ agreement.agreementUploadAdminStr == null ? '' : ("上传人：" + agreement.agreementUploadAdminStr)}}&nbsp;&nbsp;&nbsp;&nbsp;
                  {{ agreement.agreementUploadDateStr == null ? '' :("上传时间："+ agreement.agreementUploadDateStr)}}&nbsp;&nbsp;&nbsp;&nbsp;
                </p>
              </el-row>
              <el-row class="auto-table">
                <el-form-item>
                  <el-button v-if="agreement.submitState == 'Add'" icon="el-icon-upload2" type="primary" @click="submitUpload">确定上传</el-button>
                  <el-button v-if="agreement.submitState == 'Upd'" icon="el-icon-upload2" @click="updAgreement" type="primary">确定修改</el-button>
                  <el-button v-if="agreement.submitState == 'Upd'" icon="el-icon-download" @click="handleDownload" type="primary">下载合同文件</el-button>
                </el-form-item>
              </el-row>
              <!-- <div class="login-btn">
            <el-button type="primary" @click="submitForm()">登录</el-button>
          </div>
          <el-button type="text" style="padding:0px;margin-bottom:10px;text-align: right;" @click="signForm.visible = true;getCode();">注册新账号</el-button> -->
            </el-form>

          </div>
        </el-col>

      </el-row>
    </div>
  </div>
</template>

<script>
//import store from "../vuex/store.js";
export default {
  //store,
  data: function () {
    return {
      token: "",
      agreement: {
        agreementName: "",
        agreementType: "agreement_type_1",
        agreementInnovation: false,
        agreementClient: "agreement_client_1",
        agreementProvider: "",
        agreementSignDateStr: "",
        agreementAmount: 0,
        agreementText: "",
        submitState: "Add"
      },
      agreementTypeSelectiveList: [],
      productTypeSelectiveList: [],
      agreementClientSelectiveList: [],
      productList: [],
      fileList: [],
      fileNum: 0



      // project: {
      //   projectGroup: "",
      //   projectName: "",
      //   projectDescription: "",
      //   projectState: "project_state_1",
      //   canCheck: false,
      //   canEdit: false,
      //   submitState: ''
      // },
      // checkedAdmin: null,
      // projectAdminList: [],

      // groupAdminSelectiveList: [],
      // professorAdminSelectiveList: [],
      // groupSelectiveList: [],



      // projectBudgetList: [{
      //   budgetName: "",
      //   budgetValue: 0,
      //   budgetNameObj: {
      //     dictionaryName: "XX费:"
      //   }
      // }],
      // citySelectiveList: [],
      // timeLineList: []
    };
  },
  components: {
  },
  methods: {

    getFileName(row) {
      var filename = "";
      filename = filename + (row.agreementProvider == null ? '' : row.agreementProvider) + "-";
      filename = filename + (row.agreementName == null ? '' : row.agreementName) + "-";
      filename = filename + (row.agreementSignDateStr == null ? '' : row.agreementSignDateStr) + "-";
      filename = filename + (row.agreementAmount == 0 ? '0' : (row.agreementAmount + "万")) + "-";
      for (var i = 0; i < row.productList.length; i++) {
        filename = filename + (row.productList[i].productSeriesObj == null ? '' : row.productList[i].productSeriesObj.dictionaryName == null ? '' : row.productList[i].productSeriesObj.dictionaryName);
        filename = filename + "(" + (row.productList[i].productNumber == null ? '' : row.productList[i].productNumber) + ")";
        if (i < row.productList.length - 1) {
          filename = filename + ","
        }
      }
      filename = filename + "-" + (row.agreementClientObj == null ? '' : row.agreementClientObj.dictionaryName == null ? '' : row.agreementClientObj.dictionaryName);
      filename = filename + row.agreementExtend;
      return filename;
    },
    back() {
      this.$router.push({ path: "/home/agreement/list" });
    },
    handleAddProduct() {



      this.productList.push({
        productType: "product_type_1",
        productModel: "",
        productSeries: "",
        productSeriesSelectiveList: [],
        productNumber: 0,
      })

      this.initProductSeriesSelectiveList(this.productList.length - 1)
    },

    handleDeleteProduct(index) {
      this.productList.splice(index, 1)
    },

    handleUpProduct(index) {
      var tempOption = this.productList[index - 1]
      this.$set(this.productList, index - 1, this.productList[index])
      this.$set(this.productList, index, tempOption)
    },
    handleDownProduct(index) {
      var tempOption = this.productList[index + 1]
      this.$set(this.productList, index + 1, this.productList[index])
      this.$set(this.productList, index, tempOption)
    },
    initAgreementTypeList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItems", {
          params: {
            dictionaryType: "AGREEMENT_TYPE"
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.agreementTypeSelectiveList = res.data.object;

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
    handelproductTypeChange(index) {
      this.initProductSeriesSelectiveList(index);
      this.productList[index].productSeries = "";
    },


    initProductSeriesSelectiveList(index) {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItemsByFather", {
          params: {
            dictionaryType: "PRODUCT_SERIES",
            dictionaryFather: this.productList[index].productType
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {


            this.productList[index].productSeriesSelectiveList = res.data.object;
            this.$forceUpdate();
            //this.productList[index].productSeries = this.productList[index].productSeriesSelectiveList.length == 0 ? "" : this.productList[index].productSeriesSelectiveList[0];
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
    initProductTypeStateList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItems", {
          params: {
            dictionaryType: "PRODUCT_TYPE"
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.productTypeSelectiveList = res.data.object;

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

    excelImportOver(response, file, fileList) {
      if (response.code == 200) {
        this.$message({
          showClose: true,
          message: response.data,
          type: "success"
        });
        this.back();
        // this.$alert(response.data, "合同添加成功", {
        //   confirmButtonText: "确定",
        //   type: "success",
        //   callback: action => {
        //     this.back();
        //     this.fileList = []
        //   }
        // });
      } else if (response.code == 401) {
        this.$message({
          showClose: true,
          message: res.data.data,
          type: "error"
        });
        this.$router.push({ path: "/login" });
      } else {
        this.$alert(response.data, "合同添加失败", {
          confirmButtonText: "确定",
          type: "error",
          callback: action => {
            this.fileList = []
          }
        });
      }
    },
    excelImportError(error, response, file, fileList) {
      this.$alert('服务器错误', '合同添加失败', {
        confirmButtonText: '确定',
        type: 'error',
        callback: action => {
          this.initDictionaryType();
          this.fileList = []
        }
      });
    },
    onFileChange(file, fileList) {

      this.fileNum = fileList.length;
    },


    submitUpload() {
      //console.log(this.fileList)

      if (this.fileNum == 1) {
        this.$refs.upload.submit();
      } else {
        this.$alert('请选择文件', '合同添加失败', {
          confirmButtonText: '确定',
          type: 'error',
          callback: action => {

          }
        });
      }


    },
    handleRemove(file, fileList) {
      this.fileNum = fileList.length;
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed() {
      this.$alert('您只能添加一个文件', '文件添加失败', {
        confirmButtonText: '确定',
        type: 'error',
        callback: action => {

        }
      });
    },


    init(agreementId) {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/agreement/getAgreementDetails",
          {
            params: {
              agreementId: agreementId
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.agreement = res.data.object;
            this.agreement.submitState = "Upd";
            // {
            //   agreementName: res.data.object.agreement.agreementName,
            //   agreementType: res.data.object.agreement.agreementType,
            //   agreementInnovation: res.data.object.agreement.agreementInnovation,
            //   agreementClient: res.data.object.agreement.agreementClient,
            //   agreementProvider: res.data.object.agreement.agreementProvider,
            //   agreementSignDateStr: res.data.object.agreement.agreementSignDateStr,
            //   agreementAmount: res.data.object.agreement.agreementAmount,
            //   submitState: "Upd"
            // };
            this.productList = res.data.object.productList;
            for (var i = 0; i < this.productList.length; i++) {
              this.initProductSeriesSelectiveList(i);
            }

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
            this.$router.push({ path: "/home/agreement/list" });
          }
          loading.close();
        });
    },
    updAgreement() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/agreement/updAgreement",
          {
            params: {
              agreement: this.agreement,
              productList: this.productList
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {

            this.$message({
              showClose: true,
              message: res.data.data,
              type: "success"
            });

            // this.$alert(res.data.data, "合同修改成功", {
            //   confirmButtonText: "确定",
            //   type: "success",
            //   callback: action => { }
            // });
            this.back();
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
            this.$router.push({ path: "/home/agreement/list" });
          }
          loading.close();
        });
    },
    handleDownload() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      let url = '/agreement/admin/download?type=agreement&id=' + this.agreement.agreementId;
      this.axios({
        method: "get",
        url: url,
        responseType: "blob",
        headers: { token: this.token }
      }).then(data => {
        console.log(data.headers)
        if (data.headers["content-type"] == "application/json;charset=UTF-8") {
          this.$alert("下载文件出错", "错误", {
            confirmButtonText: "确定",
            type: "error",
            callback: action => {
            }
          });
          loading.close();
          return;
        }

        if (!data) {
          loading.close();
          return;
        }
        debugger;
        let url = window.URL.createObjectURL(data.data);
        let link = document.createElement("a");
        link.style.display = "none";
        link.href = url;
        link.setAttribute("download", this.getFileName(this.agreement));
        document.body.appendChild(link);
        link.click();
        loading.close();
      });
    },
    initAgreementClientList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItems", {
          params: {
            dictionaryType: "AGREEMENT_CLIENT"
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.agreementClientSelectiveList = res.data.object;

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
    // sumBudget() {
    //   var sum = 0.0;
    //   for (var i = 0; i < this.projectBudgetList.length; i++) {
    //     sum = sum + this.projectBudgetList[i].budgetValue;
    //   }
    //   return sum.toFixed(2);
    // },
    // initGroupList() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post(
    //       "/group/getAllGroupByToken",
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
    // checkdeGroup() {
    //   this.initAdminSelectiveList();
    //   this.checkedAdmin = null;
    //   this.projectAdminList = [];
    // },
    // initAdminSelectiveList() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post(
    //       "/admin/getProjectAdminCheckedList",
    //       {
    //         params: {
    //           groupId: this.project.projectGroup
    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.groupAdminSelectiveList = res.data.object.groupAdminSelectiveList;
    //         this.professorAdminSelectiveList = res.data.object.professorAdminSelectiveList;

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

    // initProjectBudgetList() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post("/dictionary/getDictionaryItems", {
    //       params: {
    //         dictionaryType: "BUDGET_NAME"
    //       }
    //     }, { headers: { token: this.token } })
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.projectBudgetList = [];
    //         var budgetDic = res.data.object
    //         budgetDic.forEach(element => {
    //           this.projectBudgetList.push({
    //             budgetName: element.dictionaryId,
    //             budgetValue: 0,
    //             budgetNameObj: element
    //           })
    //         });

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
    // initCitySelectiveList() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post("/dictionary/getDictionaryItemsTree", {
    //       params: {
    //         dictionaryType: "CITY"
    //       }
    //     }, { headers: { token: this.token } })
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.citySelectiveList = res.data.object;
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


    // handleAddAdmin() {
    //   var isExit = false;
    //   for (var i = 0; i < this.projectAdminList.length; i++) {
    //     if (this.projectAdminList[i].adminId == this.checkedAdmin.adminId) {
    //       isExit = true;
    //     }
    //   }
    //   if (isExit) {
    //     this.$message({
    //       showClose: true,
    //       message: "已存在",
    //       type: "error"
    //     });
    //   } else {
    //     this.projectAdminList.push({
    //       adminId: this.checkedAdmin.adminId,
    //       adminObj: this.checkedAdmin
    //     })
    //   }

    // },
    // handleDeleteAdmin(index, row) {
    //   this.projectAdminList.splice(index, 1)
    // },


    // handleUpdProject() {
    //   this.isEdit = true;
    //   this.initAdminSelectiveList();
    //   this.project.submitState = 'Upd'
    // },
    // handleCancelUpdProject() {
    //   this.init(this.project.projectId)
    // },

    // handelDelProject() {
    //   this.$confirm('此操作将永久删除该招生组所有相关记录，包括已添加的任务和讲座报告，您确定删除此备案吗？', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     this.project.submitState = 'Del'
    //     this.alterProject();
    //   }).catch(() => {

    //   });


    // },

    // alterProject() {
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios.post("/project/alterProject", {
    //     params: {
    //       project: this.project,
    //       projectAdminList: this.projectAdminList,
    //       projectTravelList: this.projectTravelList,
    //       projectBudgetList: this.projectBudgetList,
    //     }
    //   }, { headers: { token: this.token } }).then(res => {
    //     if (res.data.code === 200) {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "success"
    //       });
    //       if (this.project.submitState == "Add" || this.project.submitState == "Upd") {

    //         this.init(res.data.object)
    //       } else {
    //         this.$router.push({ path: "/home/project/list" });
    //       }

    //       //this.adminAddUpdForm.visible = false;
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
    // init(projectId) {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post(
    //       "/project/getProjectDetails",
    //       {
    //         params: {
    //           projectId: projectId
    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.project = res.data.object.project;
    //         this.projectAdminList = res.data.object.projectAdminList;
    //         this.projectTravelList = res.data.object.projectTravelList;
    //         this.projectBudgetList = res.data.object.projectBudgetList;
    //         this.timeLineList = res.data.object.timeLineList;
    //         this.isAdd = false;
    //         this.isEdit = false;
    //         this.checkedAdmin = null;
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
    //         this.$router.push({ path: "/home/project/list" });
    //       }
    //       loading.close();
    //     });
    // },
    // checkedProject() {
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios.post("/project/alterProjectNextState", {
    //     params: {
    //       project: this.project,
    //     }
    //   }, { headers: { token: this.token } }).then(res => {
    //     if (res.data.code === 200) {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "success"
    //       });
    //       this.init(this.project.projectId)
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
    // unCheckedProject() {
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios.post("/project/alterProjectBanState", {
    //     params: {
    //       project: this.project,
    //     }
    //   }, { headers: { token: this.token } }).then(res => {
    //     if (res.data.code === 200) {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "success"
    //       });
    //       this.init(this.project.projectId)
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
    // returnProject() {
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios.post("/project/returnProject", {
    //     params: {
    //       project: this.project,
    //     }
    //   }, { headers: { token: this.token } }).then(res => {
    //     if (res.data.code === 200) {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "success"
    //       });
    //       this.init(this.project.projectId)
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
    // exportProjectApprovalWord() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   let url = '/project/exportProjectApprovalWord?projectId=' + this.project.projectId;
    //   this.axios({
    //     method: "get",
    //     url: url,
    //     responseType: "blob",
    //     headers: { token: this.token }
    //   }).then(data => {
    //     console.log(data.headers)
    //     if (data.headers["content-type"] == "application/json;charset=UTF-8") {
    //       this.$alert("下载文件出错", "错误", {
    //         confirmButtonText: "确定",
    //         type: "error",
    //         callback: action => {
    //         }
    //       });
    //       loading.close();
    //       return;
    //     }

    //     if (!data) {
    //       loading.close();
    //       return;
    //     }
    //     debugger;
    //     let url = window.URL.createObjectURL(data.data);
    //     let link = document.createElement("a");
    //     link.style.display = "none";
    //     link.href = url;
    //     link.setAttribute("download", '东北大学因公出差审批表.docx');
    //     document.body.appendChild(link);
    //     link.click();
    //     loading.close();
    //   });
    // },
    // exportProjectRecordWord() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   let url = '/project/exportProjectRecordWord?projectId=' + this.project.projectId;
    //   this.axios({
    //     method: "get",
    //     url: url,
    //     responseType: "blob",
    //     headers: { token: this.token }
    //   }).then(data => {
    //     if (data.headers["content-type"] == "application/json;charset=UTF-8") {
    //       this.$alert("下载文件出错", "错误", {
    //         confirmButtonText: "确定",
    //         type: "error",
    //         callback: action => {
    //         }
    //       });
    //       loading.close();
    //       return;
    //     }

    //     if (!data) {
    //       loading.close();
    //       return;
    //     }
    //     debugger;
    //     let url = window.URL.createObjectURL(data.data);
    //     let link = document.createElement("a");
    //     link.style.display = "none";
    //     link.href = url;
    //     link.setAttribute("download", '东北大学招生宣传出行备案表.docx');
    //     document.body.appendChild(link);
    //     link.click();
    //     loading.close();
    //   });
    // }

  },
  created: function () {
    this.token = localStorage.getItem("token");
    if (this.token == "" || this.token == null) {
      this.$router.push({ path: "/login" });
      return;
    }



    this.initAgreementTypeList();
    this.initProductTypeStateList();
    this.initAgreementClientList();
    // this.initGroupList();
    // this.initCitySelectiveList();
    if (this.$route.params.agreementId == null) {
      this.project.submitState = 'Add'
    } else {
      this.init(this.$route.params.agreementId)
    }


    // localStorage.removeItem("admin");
    // localStorage.removeItem("token");
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
#project {
  // height: 966px;
  //background: url("../assets/img/login-bg2.png") no-repeat center bottom;
  background-size: 100%;

  .login-wrap {
    //height: 360px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 50px auto;
    width: 1485px;
    padding: 25px 35px 15px 35px;
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
    //sheight: 160px;
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
    margin-top: 10px;
    margin-bottom: 10px;
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
  .el-form-item__content {
    text-align: center;
  }
  .auto-table {
    p {
      font-size: 16px;
      text-align: center;
    }
  }
  .el-timeline {
    padding-left: 0px;
  }

  .el-input-number__decrease,
  .el-input-number__increase {
    display: none;
  }
  .el-input-number .el-input__inner {
    padding-left: 15px;
    padding-right: 15px;
    text-align: left;
  }
  .el-upload-list__item-name {
    color: #000;
    font-weight: 700;
  }
}
</style>