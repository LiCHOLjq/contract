<template>
  <div id="share">
    <el-card style="padding:0px;  padding-bottom:10px;margin:10px">
      <!-- <div slot="header" class="search-header"> -->
      <!-- <p style="margin-top:10px;float:left">搜索设置</p> -->
      <el-form :inline="true" :model="shareSelectForm" class="demo-form-inline" style="margin-top:10px;float:left">

        <el-form-item style="margin-top:0px" label="分享人：">
          <el-select v-model="shareSelectForm.shareAdmin" placeholder="选择分享人">
            <el-option v-for="item in adminSelectiveList" :key="item.adminId" :label="item.adminName" :value="item.adminId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="margin-top:0px" label="分享类型：">
          <el-select v-model="shareSelectForm.shareType" placeholder="选择分享类型">
            <el-option v-for="item in shareTypeSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="margin-top:0px" v-if="role == 'admin_role_master'" label="是否删除：">
          <el-select v-model="shareSelectForm.shareDelete" placeholder="选择是否删除" style="width: 180px;">
            <el-option label="全部" value=""></el-option>
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <el-tooltip class="item" effect="dark" content="邀请上传合同" placement="bottom">
        <el-button style="float:right;margin-left:10px;margin-right:10px;margin-top:10px;" circle icon="el-icon-upload" type="primary" @click="shareForm.visible2 = true;shareForm.shareType='share_type_upload';setEndDate();"></el-button>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="分享合同" placement="bottom">
        <el-badge style="float:right;margin-left:10px;margin-right:10px;margin-top:10px;" :value="cartNum" type="success">
          <el-button type="primary" icon="el-icon-share" circle @click="shareForm.visible = true;shareForm.shareType='share_type_download';initCart();setEndDate();"></el-button>
        </el-badge>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="删除过期分享" placement="bottom">
        <el-button style="float:right;margin-left:10px;margin-right:10px;margin-top:10px;" type="primary" icon="el-icon-delete-solid" circle @click="handelDelOverdue();"></el-button>
      </el-tooltip>
      <el-tooltip v-if="role == 'admin_role_master'" class="item" effect="dark" content="清空回收站" placement="bottom">
        <el-button style="float:right;margin-left:10px;margin-right:10px;margin-top:10px;" type="primary" icon="el-icon-delete" circle @click="handelDelAllDeleted();"></el-button>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="搜索" placement="bottom">
        <el-button style="float:right;margin-left:10px;margin-right:10px;margin-top:10px;" type="primary" icon="el-icon-search" circle @click="sharePage.current=1;initShareList();"></el-button>
      </el-tooltip>
      <!-- </div> -->

    </el-card>

    <el-card style="padding:0px;  padding-bottom:10px;margin:10px">
      <el-row>
        <el-col class="main-col" :span="24">

          <el-table v-bind:data="shareTableData" border style="width: 100%">
            <el-table-column label="链接">
              <template slot-scope="scope">
                <span style="margin-left: 10px">
                  <el-link type="text" :href=" scope.row.shareId == null ? '' :( baseURL + '/share/items/' + scope.row.shareId )" target="_blank">{{ scope.row.shareId == null ? '' :( baseURL + '/share/items/' + scope.row.shareId )   }}</el-link>
                </span>
              </template>
            </el-table-column>
            <el-table-column label="分享人" width="150">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.shareAdminObj == null ? '' : scope.row.shareAdminObj.adminName == null ? '' : scope.row.shareAdminObj.adminName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="类型" width="100">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.shareTypeObj == null ? '' : scope.row.shareTypeObj.dictionaryName == null ? '' : scope.row.shareTypeObj.dictionaryName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="有效期" width="190">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.shareDateStr == null ? '' : scope.row.shareDateStr }}</span>
              </template>
            </el-table-column>
            <el-table-column label="是否开启密码保护" width="140">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.shareHasPassword == null ? '' : scope.row.shareHasPassword  ? '是' : '否'  }}</span>
              </template>
            </el-table-column>
            <el-table-column label="密码" width="140">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.sharePassword == null ? '' : scope.row.sharePassword }}</span>
              </template>
            </el-table-column>
            <el-table-column v-if="role == 'admin_role_master'" label="是否删除" width="100">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.shareDelete == null ? '' : scope.row.shareDelete ? '已删除' : '' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="200">
              <template slot-scope="scope">
                <!-- <el-button style="margin-bottom:10px" v-if="!scope.row.agreementDelete" size="mini" type="primary" @click="handleDetails(scope.$index, scope.row)">编辑</el-button> -->
                <el-button style="margin-bottom:10px" v-if="!scope.row.shareDelete" size="mini" type="danger" @click="handleDelShare(scope.$index, scope.row)">删除</el-button>
                <el-button style="margin-bottom:10px" v-if="scope.row.shareDelete&&role == 'admin_role_master'" size="mini" type="success" @click="handleRestoreShare(scope.$index, scope.row)">还原</el-button>
                <el-button style="margin-bottom:10px" v-if="scope.row.shareDelete&&role == 'admin_role_master'" size="mini" type="danger" @click="handleRelDelShare(scope.$index, scope.row)">彻底删除</el-button>
                <el-button style="margin-bottom:10px" v-if="!scope.row.shareDelete" size="mini" type="info" @click="handleCopy(scope.$index, scope.row)">复制分享内容</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
            <el-pagination background @size-change="handleShareTableSizeChange" @current-change="handleShareTableCurrentChange" :hide-on-single-page="false" :current-page="sharePage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="sharePage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="sharePage.totalCount"></el-pagination>
          </div>
        </el-col>
      </el-row>
    </el-card>
    <el-drawer title="分享合同" :visible.sync="shareForm.visible" direction="rtl" size="770px">
      <el-row style="padding:10px">
        <el-table v-bind:data="cartTableData" border style="width: 100%">
          <el-table-column label="名称">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{ scope.row.cartAgreementObj == null ? '' : scope.row.cartAgreementObj.agreementName == null ? '' : scope.row.cartAgreementObj.agreementName }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="160">
            <template slot-scope="scope">
              <el-button size="mini" type="danger" @click="handleDelCart(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
      <el-row>
        <el-form v-if="cartNum>0" :inline="true" :model="shareForm" class="demo-form-inline">
          <el-row>
            <!-- <el-form-item label="有效期：">
              <el-date-picker style="width:300px" v-model="shareForm.shareBeginDateStr" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择开始时间（不填写无此限定）">
              </el-date-picker>
            </el-form-item> -->
            <el-form-item label="有效期：">
              <el-date-picker style="width:300px" v-model="shareForm.shareEndDateStr" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" :picker-options="pickerOptions" placeholder="选择结束时间（不填写无此限定）">
              </el-date-picker>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="开启密码保护：">
              <el-switch v-model="shareForm.shareHasPassword">
              </el-switch>
            </el-form-item>
            <el-form-item v-if="shareForm.shareHasPassword" label="设置密码：">
              <el-input style="width:465px" v-model="shareForm.sharePassword" placeholder="不填写将生成4位随机密码" autocomplete="off"></el-input>
            </el-form-item>
          </el-row>
          <el-row style="margin-left:200px">
            <el-form-item>
              <el-button type="primary" icon="el-icon-delete-solid" @click="clearCart()">清空列表</el-button>
              <el-button type="primary" icon="el-icon-download" @click="downLoadCart()">打包下载</el-button>
              <el-button type="primary" icon="el-icon-share" @click="addDownLoadShare()">分享</el-button>
            </el-form-item>
          </el-row>
        </el-form>
      </el-row>
    </el-drawer>
    <el-drawer title="邀请上传合同" :visible.sync="shareForm.visible2" direction="rtl" size="770px">

      <el-row>
        <el-form :inline="true" :model="shareForm" class="demo-form-inline">
          <el-row>
            <!-- <el-form-item label="有效期：">
              <el-date-picker style="width:300px" v-model="shareForm.shareBeginDateStr" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择开始时间（不填写无此限定）">
              </el-date-picker>
            </el-form-item> -->
            <el-form-item label="有效期：">
              <el-date-picker style="width:300px" v-model="shareForm.shareEndDateStr" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" :picker-options="pickerOptions" placeholder="选择结束时间（不填写无此限定）">
              </el-date-picker>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="开启密码保护：">
              <el-switch v-model="shareForm.shareHasPassword">
              </el-switch>
            </el-form-item>
            <el-form-item v-if="shareForm.shareHasPassword" label="设置密码：">
              <el-input style="width:465px" v-model="shareForm.sharePassword" placeholder="不填写将生成4位随机密码" autocomplete="off"></el-input>
            </el-form-item>
          </el-row>
          <el-row style="margin-left:300px">
            <el-form-item>
              <el-button type="primary" icon="el-icon-share" @click="addDownLoadShare()">分享</el-button>
            </el-form-item>
          </el-row>
        </el-form>
      </el-row>
    </el-drawer>
    <el-dialog :close-on-click-modal="false" title="分享成功" :visible.sync="shareSuccess.visible" width="50%">
      <el-form style="padding-left:20px;padding-right:40px">
        <el-form-item label="分享人：" :label-width="shareSuccess.formLabelWidth">
          <el-input v-model="shareSuccess.admin" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="链接：" :label-width="shareSuccess.formLabelWidth">
          <el-input v-model="shareSuccess.url" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码：" :label-width="shareSuccess.formLabelWidth">
          <el-input v-model="shareSuccess.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="有效期：" :label-width="shareSuccess.formLabelWidth">
          <el-input v-model="shareSuccess.date" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="copy()">复制到剪贴板</el-button>
        <el-button type="primary" @click="shareSuccess.visible = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      token: "",
      role: "",
      baseURL: "",
      shareSelectForm: {
        shareAdmin: "",
        shareType: "",
        shareDelete: "",
      },

      adminSelectiveList: [],
      shareTypeSelectiveList: [],

      shareTableData: [],
      sharePage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },

      // agreementTypeSelectiveList: [],
      // productTypeSelectiveList: [],
      // agreementTableData: [],
      // agreementPage: {
      //   showCount: 10,
      //   totalCount: 0,
      //   current: 1
      // },
      //排序
      // sortForm: {
      //   title: "排序",
      //   visible: false,
      //   field: { name: '上传时间', field: 'agreement_upload_date' },
      //   type: { name: '降序', type: 'desc' },
      // },
      // sortTags: [
      //   { name: '上传时间 降序', field: 'agreement_upload_date', type: 'desc' },
      // ],
      // fieldsDic: [
      //   { name: '名称', field: 'agreement_name' },
      //   { name: '类型', field: 'agreement_type' },
      //   { name: '是否信创', field: 'agreement_innovation' },
      //   { name: '客户名称', field: 'agreement_client' },
      //   { name: '总金额', field: 'agreement_amount' },
      //   { name: '签约日期', field: 'agreement_sign_date' },
      //   { name: '提供者', field: 'agreement_provider' },
      //   { name: '上传时间', field: 'agreement_upload_date' },
      // ],
      // typeDic: [
      //   { name: '升序', type: 'asc' },
      //   { name: '降序', type: 'desc' },
      // ],
      // click: {

      // },
      // sortValue: "agreement_upload_date ",

      shareForm: {
        visible: false,
        visible2: false,
        shareBeginDateStr: "",
        shareEndDateStr: "",
        shareHasPassword: true,
        sharePassword: "",
        shareType: ""
      },
      cartNum: 0,
      cartTableData: [],
      shareSuccess: {
        visible: false,
        formLabelWidth: "80px",
        admin: "",
        url: "",
        password: "",
        date: "",
        type: ""
      },
      pickerOptions: {
        shortcuts: [{
          text: '1天后',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '3天后',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 3);
            picker.$emit('pick', date);
          }
        }, {
          text: '7天后',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }, {
          text: '30天后',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 30);
            picker.$emit('pick', date);
          }
        }]
      },

    }
  },
  methods: {
    initShareList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/share/getShareBySearch",
          {
            params: {
              share: this.shareSelectForm,

              currentPage: this.sharePage.current,
              showCount: this.sharePage.showCount
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.sharePage.totalCount = res.data.object.totalNum;
            this.shareTableData = res.data.object.items;
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
    initShareTypeList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItems", {
          params: {
            dictionaryType: "SHARE_TYPE"
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.shareTypeSelectiveList = res.data.object;

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
    initAdminSelectiveList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/admin/getAllAdmin", {
          params: {

          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.adminSelectiveList = res.data.object;

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


    // handleAddAgreement() {
    //   this.savePage()
    //   this.$router.push({ path: "/home/agreement/details" });
    // },

    // handleDetails(index, row) {
    //   this.savePage()
    //   this.$router.push({ path: "/home/agreement/details/" + row.agreementId });
    // },

    // handleDelProject(index, row) {

    // },

    handleShareTableSizeChange(val) {
      this.sharePage.showCount = val;
      this.sharePage.current = 1;
      this.initShareList();
    },
    handleShareTableCurrentChange(val) {
      this.sharePage.current = val;
      this.initShareList();
    },
    // savePage() {
    //   localStorage.setItem("agreementListPageMessage", JSON.stringify({
    //     agreementSelectForm: this.agreementSelectForm,
    //     agreementPage: this.agreementPage,
    //     sortForm: this.sortForm,
    //     sortTags: this.sortTags,
    //     sortValue: this.sortValue,
    //   }))
    // },

    // handleRemoveTag(tag) {
    //   this.sortTags.splice(this.sortTags.indexOf(tag), 1);
    // },
    // handleAddTag() {
    //   var i = 0;
    //   for (var i = 0; i < this.sortTags.length; i++) {
    //     if (this.sortTags[i].field == this.sortForm.field.field) {
    //       break;
    //     }
    //   }
    //   if (i < this.sortTags.length) {
    //     this.sortTags.splice(i, 1);
    //   }
    //   this.sortTags.push(
    //     { name: this.sortForm.field.name + " " + this.sortForm.type.name, field: this.sortForm.field.field, type: this.sortForm.type.type }
    //   )
    // },
    // alterSort() {
    //   this.sortValue = "";
    //   for (var i = 0; i < this.sortTags.length; i++) {
    //     this.sortValue = this.sortValue + " " + this.sortTags[i].field + " " + this.sortTags[i].type + " ,";
    //   }
    //   this.sortValue = this.sortValue.substring(0, this.sortValue.length - 1);
    //   this.initAgreementList();
    // },
    handleDelShare(index, row) {
      this.$confirm('您确定删除此分享吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading(this.$store.state.loadingOption2);
        this.axios.post("/share/alterShareDel", {
          params: {
            share: row
          }
        }, { headers: { token: this.token } }).then(res => {
          if (res.data.code === 200) {
            this.initShareList();
            this.initCart();
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
      }).catch(() => {

      });
    },
    handleRestoreShare(index, row) {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/share/alterShareRestore", {
        params: {
          share: row
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.initShareList();
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
    handleRelDelShare(index, row) {
      this.$confirm('您确定彻底删除此分享吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading(this.$store.state.loadingOption2);
        this.axios.post("/share/alterShareRelDel", {
          params: {
            share: row
          }
        }, { headers: { token: this.token } }).then(res => {
          if (res.data.code === 200) {
            this.initShareList();
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
            this.$alert(res.data.data, "错误", {
              confirmButtonText: "确定",
              type: "error",
              callback: action => {
              }
            });
          }
          loading.close();
        });
      }).catch(() => {

      });
    },
    // handleAddToCart(index, row) {
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios.post("/agreement/addToCart", {
    //     params: {
    //       agreement: row
    //     }
    //   }, { headers: { token: this.token } }).then(res => {
    //     if (res.data.code === 200) {
    //       row.shareState = true;
    //       this.cartNum = this.cartNum + 1;

    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "success"
    //       });

    //     } else if (res.data.code == 401) {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "error"
    //       });
    //       this.$router.push({ path: "/login" });
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
    // handleDelFormCart(index, row) {
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios.post("/agreement/delFromCart", {
    //     params: {
    //       agreement: row
    //     }
    //   }, { headers: { token: this.token } }).then(res => {
    //     if (res.data.code === 200) {
    //       row.shareState = false;
    //       this.cartNum = this.cartNum - 1;
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "success"
    //       });

    //     } else if (res.data.code == 401) {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "error"
    //       });
    //       this.$router.push({ path: "/login" });
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
    // handleDownload(index, row) {

    // },
    initCart() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/agreement/getCart",
          {
            params: {
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.cartTableData = res.data.object;
            this.cartNum = this.cartTableData.length;
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
    handleDelCart(index, row) {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/agreement/delCartById", {
        params: {
          cart: row
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.cartTableData.splice(index, 1)
          this.cartNum = this.cartNum - 1;
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
    randomPassword(size) {
      var seed = new Array('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'Q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '2', '3', '4', '5', '6', '7', '8', '9'
      );//数组
      var seedlength = seed.length;//数组长度
      var createPassword = '';
      for (var i = 0; i < size; i++) {
        var j = Math.floor(Math.random() * seedlength);
        createPassword += seed[j];
      }
      return createPassword;
    },
    addDownLoadShare() {
      if (this.shareForm.shareHasPassword && this.shareForm.sharePassword == "") {
        this.shareForm.sharePassword = this.randomPassword(4);
      } else if (!this.shareForm.shareHasPassword) {
        this.shareForm.sharePassword = "";
      }
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/share/addDownLoadShare", {
        params: {
          share: this.shareForm
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.shareSuccess.admin = res.data.object.shareAdminObj == null ? "" : res.data.object.shareAdminObj.adminName == null ? "" : res.data.object.shareAdminObj.adminName;
          this.shareSuccess.url = this.baseURL + "/share/items/" + res.data.object.shareId;
          this.shareSuccess.password = res.data.object.sharePassword;

          if (res.data.object.shareBeginDateStr != "" && res.data.object.shareEndDateStr != "") {
            this.shareSuccess.date = res.data.object.shareBeginDateStr + " 至 " + res.data.object.shareEndDateStr;
          }
          if (res.data.object.shareBeginDateStr == "" && res.data.object.shareEndDateStr != "") {
            this.shareSuccess.date = res.data.object.shareEndDateStr + " 截止";
          }
          if (res.data.object.shareBeginDateStr != "" && res.data.object.shareEndDateStr == "") {
            this.shareSuccess.date = res.data.object.shareBeginDateStr + " 开始";
          }
          if (res.data.object.shareBeginDateStr == "" && res.data.object.shareEndDateStr == "") {
            this.shareSuccess.date = "永久有效";
          }
          this.shareSuccess.visible = true;
          this.shareSuccess.type = res.data.object.shareType == "share_type_download" ? "下载" : res.data.object.shareType == "share_type_upload" ? "上传" : ""
          this.initShareList();
          this.initCart();
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

    // shareSuccess: {
    //   visible: false,
    //   formLabelWidth: "80px",
    //   admin: "",
    //   url: "",
    //   password: "",
    //   date: ""
    handleCopy(index, row) {
      this.shareSuccess.admin = row.shareAdminObj == null ? "" : row.shareAdminObj.adminName == null ? "" : row.shareAdminObj.adminName;
      this.shareSuccess.url = this.baseURL + "/share/items/" + row.shareId;
      this.shareSuccess.password = row.sharePassword;
      this.shareSuccess.date = row.shareDateStr;
      //this.shareSuccess.visible = true;
      this.shareSuccess.type = row.shareType == "share_type_download" ? "下载" : row.shareType == "share_type_upload" ? "上传" : ""
      this.copy();
    },

    copy() {
      this.$copyText(this.shareSuccess.admin + "邀请您" + this.shareSuccess.type + "合同文件\n链接：" + this.shareSuccess.url + (this.shareSuccess.password == "" ? "" : ("\n密码：" + this.shareSuccess.password)) + "\n有效期：" + this.shareSuccess.date).then(
        res => {
          this.$message({
            showClose: true,
            message: "已复制",
            type: "success"
          });
        },
        err => {
          this.$message({
            showClose: true,
            message: "复制失败",
            type: "danger"
          });
        }
      )
    },
    downLoadCart() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      let url = '/agreement/downLoadCart';
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
        link.setAttribute("download", "合同文件.zip");
        document.body.appendChild(link);
        link.click();
        loading.close();
      });
    },
    clearCart(index, row) {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/agreement/clearCart", {
        params: {
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.initCart();
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
    setEndDate() {
      this.shareForm.shareEndDateStr = this.formatDate(new Date().getTime() + 3600 * 1000 * 24)
    },
    formatDate(time) {
      var date = new Date(time);
      var year = date.getFullYear(),
        month = date.getMonth() + 1,//月份是从0开始的
        day = date.getDate(),
        hour = date.getHours(),
        min = date.getMinutes(),
        sec = date.getSeconds();
      var newTime = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day) + ' ' + (hour < 10 ? '0' + hour : hour) + ':' + (min < 10 ? '0' + min : min) + ':' + (sec < 10 ? '0' + sec : sec);
      return newTime;
    },

    handelDelOverdue() {
      this.$confirm('您确定彻底删除所有已过期的分享吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading(this.$store.state.loadingOption2);
        this.axios.post("/share/delOverdue", {
          params: {
          }
        }, { headers: { token: this.token } }).then(res => {
          if (res.data.code === 200) {
            this.initShareList();
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
            this.$alert(res.data.data, "错误", {
              confirmButtonText: "确定",
              type: "error",
              callback: action => {
              }
            });
          }
          loading.close();
        });
      }).catch(() => {
      });
    },

    handelDelAllDeleted() {

      this.$confirm('此操作将彻底删除全部已删除的分享，您确定清空回收站吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading(this.$store.state.loadingOption2);
        this.axios.post("/share/delAllDeleted", {
          params: {
          }
        }, { headers: { token: this.token } }).then(res => {
          if (res.data.code === 200) {
            this.initShareList();
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
            this.$alert(res.data.data, "错误", {
              confirmButtonText: "确定",
              type: "error",
              callback: action => {
              }
            });
          }
          loading.close();
        });
      }).catch(() => {
      });
    }

    // handleInitTask(index, row) {
    //   this.projectChecked = row;
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post(
    //       "/task/getProjectTaskBySearch",
    //       {
    //         params: {
    //           project: row
    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {

    //         this.projectTaskTableData = res.data.object;
    //         this.projectTaskForm.visible = true;
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

    // handleAddProjectTask() {
    //   this.savePage()
    //   this.$router.push({ path: "/home/project/task/add/" + this.projectChecked.projectId });
    // },

    // handleUpdProjectTask(index, row) {
    //   this.savePage()
    //   this.$router.push({ path: "/home/project/task/" + row.taskId });
    // },

    // // handleDelProjectTask(index, row) {

    // // },


    // checkedProject(index, row) {
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios.post("/project/alterProjectNextState", {
    //     params: {
    //       project: row,
    //     }
    //   }, { headers: { token: this.token } }).then(res => {
    //     if (res.data.code === 200) {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "success"
    //       });
    //       this.initProjectList();
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

    // handleInitNews(index, row) {
    //   this.projectChecked = row;
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post(
    //       "/news/getProjectNewsBySearch",
    //       {
    //         params: {
    //           project: row
    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {

    //         this.projectNewsTableData = res.data.object;
    //         this.projectNewsForm.visible = true;
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
    // handleAddProjectNews() {
    //   this.savePage()
    //   this.$router.push({ path: "/home/project/news/add/" + this.projectChecked.projectId });
    // },

    // handleUpdProjectNews(index, row) {
    //   this.savePage()
    //   this.$router.push({ path: "/home/project/news/" + row.newsId });
    // },
    // handleSeeNews(index, row) {
    //   this.savePage()
    //   this.$router.push({ path: "/news/" + row.newsId });
    // },
  },
  created: function () {
    //登陆验证代码
    //权限验证代码
    this.token = localStorage.getItem("token");
    if (this.token == "" || this.token == null) {
      this.$router.push({ path: "/login" });
      return;
    }
    this.role = JSON.parse(localStorage.getItem("admin")).adminRole;

    this.baseURL = window.location.protocol + "//" + window.location.hostname;
    if (window.location.port != "") {
      this.baseURL = this.baseURL + ":" + window.location.port;
    }
    this.baseURL = this.baseURL + "/agreement"
    // var str = localStorage.getItem("agreementListPageMessage")
    // if (str != "" && str != null && str != "null") {
    //   var agreementListPageMessage = JSON.parse(str)
    //   this.agreementSelectForm = agreementListPageMessage.agreementSelectForm
    //   this.agreementPage = agreementListPageMessage.agreementPage
    //   this.sortForm = agreementListPageMessage.sortForm
    //   this.sortTags = agreementListPageMessage.sortTags
    //   this.sortValue = agreementListPageMessage.sortValue
    //   localStorage.setItem("agreementListPageMessage", null)
    // }
    // 加载学院列表数据
    this.initShareTypeList();
    // 加载admin table data
    this.initAdminSelectiveList();

    this.initShareList();

    this.initCart();

    // if (this.projectTaskForm.visible) {
    //   this.handleInitTask(0, this.projectChecked)
    // }
    // if (this.projectNewsForm.visible) {
    //   this.handleInitNews(0, this.projectChecked)
    // }


  }

}
</script>

<style  lang="scss">
#share {
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
  .el-input-number__decrease {
    display: none;
  }
  .el-input-number__increase {
    display: none;
  }
  .el-input-number {
    width: 100%;
  }
  .el-input-number .el-input .el-input__inner {
    padding: 0 15px;
    text-align: left;
  }
  .el-card__body {
    padding: 0px;
  }
  .el-button + .el-button {
    margin: 0px;
  }
  .el-card__header {
    padding-top: 5px;
    padding-bottom: 25px;
    height: 52px;
  }
  .name-wrapper {
    float: left;
  }
  .el-badge__content {
    margin-top: 6px;
  }
  .el-drawer__header {
    margin: 0px;
    padding: 20px;
    background-color: #e1140a;
    color: #fff;
  }
    .el-drawer__body{
    height: calc(100% - 770px);
    overflow: auto;
  }
  // .form-item-1 {
  //   width: 100%;
  //   .el-form-item__content {
  //     width: calc(100% - 90px);
  //   }
  // }
  // .form-item-2 {
  //   width: calc((100% - 124px) / 2 + 86px);
  //   .el-form-item__content {
  //     width: calc(100% - 96px);
  //   }
  // }
  // .form-item-3 {
  //   width: calc((100% - 124px) / 2 + 26px);
  //   .el-form-item__content {
  //     width: calc(100% - 36px);
  //   }
  // }
  // .form-item-4 {
  //   width: calc((100% - 150px) / 2 + 110px);
  //   .el-form-item__content {
  //     width: calc(100% - 117px);
  //   }
  //   .el-date-editor.el-input,
  //   .el-date-editor.el-input__inner {
  //     width: 100%;
  //   }
  // }
  // .form-item-5 {
  //   width: calc((100% - 150px) / 2 + 26px);
  //   .el-form-item__content {
  //     width: calc(100% - 33px);
  //   }
  //   .el-date-editor.el-input,
  //   .el-date-editor.el-input__inner {
  //     width: 100%;
  //   }
  // }
  // .form-item-6 {
  //   width: 100%;
  //   .el-form-item__content {
  //     width: calc(100% - 62px);
  //   }
  // }
}
</style>