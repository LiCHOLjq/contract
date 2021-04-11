<template>
  <div id="agreementList">
    <el-card style="padding:0px;  padding-bottom:10px;margin:10px">
      <div slot="header" class="search-header">
        <p style="margin-top:10px;float:left">搜索设置</p>
        <el-button v-if="!agreementSelectForm.visible" style="float:right;margin-left:10px;margin-right:10px;" type="primary" icon="el-icon-arrow-down" circle @click="agreementSelectForm.visible = true;"></el-button>
        <el-button v-if="agreementSelectForm.visible" style="float:right;margin-left:10px;margin-right:10px;" type="primary" icon="el-icon-arrow-up" circle @click="agreementSelectForm.visible = false;"></el-button>
        <el-badge style="float:right;margin-left:10px;margin-right:10px;" :value="cartNum" type="success">
          <el-button type="primary" icon="el-icon-share" circle @click="shareForm.visible = true;initCart()"></el-button>
        </el-badge>
        <el-button style="float:right;margin-left:10px;margin-right:10px;" type="primary" icon="el-icon-search" circle @click="agreementPage.current=1;initAgreementList();"></el-button>
      </div>
      <div v-show="agreementSelectForm.visible" class="search-body" style="padding-left:10px;padding-right:10px">
        <el-form :inline="true" :model="agreementSelectForm" class="demo-form-inline">
          <el-row>

            <el-form-item label="合同名称: ">
              <!-- 模糊 -->
              <el-input style="width:400px" v-model="agreementSelectForm.agreementName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="合同类型：">
              <el-select style="width:450px" multiple v-model="agreementSelectForm.agreementTypeList" placeholder="选择合同类型">
                <el-option v-for="item in agreementTypeSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="是否信创：">
              <el-select style="width:140px" v-model="agreementSelectForm.agreementInnovation" placeholder="选择是否信创">
                <el-option label="全部" value=""></el-option>
                <el-option label="是" value="1"></el-option>
                <el-option label="否" value="0"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="客户名称: ">
              <!-- 模糊 -->
              <el-input style="width:220px" v-model="agreementSelectForm.agreementClient" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="提供者: ">
              <!-- 模糊 -->
              <el-input style="width:330px" v-model="agreementSelectForm.agreementProvider" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="总金额范围: ">
              <el-input-number style="width:210px" v-model="agreementSelectForm.agreementAmountBegin"></el-input-number>
            </el-form-item>
            <el-form-item label="→">
              <el-input-number style="width:210px" v-model="agreementSelectForm.agreementAmountEnd"></el-input-number>
            </el-form-item>

            <el-form-item label="签约日期范围：">
              <el-date-picker style="width:220px" v-model="agreementSelectForm.agreementSignDateBeginStr" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="→">
              <el-date-picker style="width:220px" v-model="agreementSelectForm.agreementSignDateEndStr" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期">
              </el-date-picker>
            </el-form-item>

            <el-form-item label="备注: ">
              <!-- 模糊 -->
              <el-input style="width:980px" v-model="agreementSelectForm.agreementText" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="产品类型：">
              <el-select style="width:560px" multiple v-model="agreementSelectForm.productTypeList" placeholder="选择合同类型">
                <el-option v-for="item in productTypeSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="产品型号：">
              <!-- 模糊 -->
              <el-input style="width:300px" v-model="agreementSelectForm.productModel" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="产品数量: ">
              <el-input-number style="width:200px" v-model="agreementSelectForm.productNumberBegin"></el-input-number>
            </el-form-item>
            <el-form-item label="→">
              <el-input-number style="width:200px" v-model="agreementSelectForm.productNumberEnd"></el-input-number>
            </el-form-item>
            <el-form-item v-if="role == 'admin_role_master'" label="是否删除：">
              <el-select v-model="agreementSelectForm.agreementDelete" placeholder="选择是否删除" style="width: 180px;">
                <el-option label="全部" value=""></el-option>
                <el-option label="是" value="1"></el-option>
                <el-option label="否" value="0"></el-option>
              </el-select>
            </el-form-item>
            <!-- <el-form-item>
              <el-button @click="sortForm.visible = true" type="primary">排序</el-button>
              <el-button type="primary" @click="agreementPage.current=1;initAgreementList();">
                查询
              </el-button>
            </el-form-item> -->
          </el-row>
          <el-row>
            <el-row>
              <div style="font-size: 14px;margin-left:20px;margin-top:28px;float:left">排序规则：</div>
              <el-tag style="margin-left:20px;margin-top:20px;" v-for="tag in sortTags" :key="tag.name" closable @close="handleRemoveTag(tag)">
                {{tag.name}}
              </el-tag>
            </el-row>
            <div style="font-size: 14px;margin-left:20px;margin-top:30px;float:left">添加排序规则：</div>
            <el-select style="margin-left:20px;margin-top:20px;" v-model="sortForm.field" value-key="field" placeholder="请选择">
              <el-option v-for="field in fieldsDic" :key="field.field" :label="field.name" :value="field" />
            </el-select>
            <el-select style="margin-left:20px;margin-top:20px;" v-model="sortForm.type" value-key="type" placeholder="请选择">
              <el-option v-for="type in typeDic" :key="type.type" :label="type.name" :value="type" />
            </el-select>
            <el-button type="primary" style="margin-left:20px;margin-top:20px;" @click="handleAddTag()">添 加</el-button>
          </el-row>
        </el-form>
      </div>
    </el-card>
    <el-card style="padding:0px;  padding-bottom:10px;margin:10px">
      <el-row>
        <el-col class="main-col" :span="24">
          <el-button size="mini" icon="el-icon-plus" type="primary" @click="handleAddAgreement()">
            上传新合同
          </el-button>
          <el-table v-bind:data="agreementTableData" border style="width: 100%">
            <el-table-column label="名称">
              <template slot-scope="scope">
                <span style="margin-left: 10px">
                  <el-button type="text" @click="handleDelProject(scope.$index, scope.row)">{{ scope.row.agreementName == null ? '' : scope.row.agreementName }}</el-button>
                </span>
              </template>
            </el-table-column>
            <el-table-column label="类型" width="140">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.agreementTypeObj == null ? '' : scope.row.agreementTypeObj.dictionaryName == null ? '' : scope.row.agreementTypeObj.dictionaryName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="是否信创" width="90">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.agreementInnovation == null ? '' : scope.row.agreementInnovation ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="客户名称" width="140">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.agreementClient == null ? '' : scope.row.agreementClient }}</span>
              </template>
            </el-table-column>
            <el-table-column label="提供者" width="140">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.agreementProvider == null ? '' : scope.row.agreementProvider }}</span>
              </template>
            </el-table-column>
            <el-table-column label="总金额" width="140">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.agreementAmount == null ? '' : scope.row.agreementAmount }}</span>
              </template>
            </el-table-column>
            <el-table-column label="签约日期" width="130">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.agreementSignDateStr == null ? '' : scope.row.agreementSignDateStr }}</span>
              </template>
            </el-table-column>

            <el-table-column label="备注">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.agreementText == null ? '' : scope.row.agreementText }}</span>
              </template>
            </el-table-column>
            <el-table-column label="产品类型" width="150">
              <template slot-scope="scope">
                <el-popover v-for="item in scope.row.productList" :key="item.productId" trigger="hover">
                  <p>{{ item.productModel == null ? '' : ("型号：" + item.productModel)}}</p>
                  <p>{{ item.productNumber == null ? '' : ("数量：" + item.productNumber)}}</p>
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium">{{ item.productTypeObj == null ? '' : item.productTypeObj.dictionaryName  == null ? '' : item.productTypeObj.dictionaryName}}</el-tag>
                  </div>
                </el-popover>
              </template>
            </el-table-column>
            <el-table-column label="上传信息" width="250">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.agreementUploadAdmin == null ? '' : ("上传人：" + scope.row.agreementUploadAdmin)}}</span><br />
                <span style="margin-left: 10px">{{ scope.row.agreementUploadDateStr == null ? '' :("上传时间："+ scope.row.agreementUploadDateStr)}}</span>
              </template>
            </el-table-column>
            <el-table-column v-if="role == 'admin_role_master'" label="是否删除" width="100">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.agreementDelete == null ? '' : scope.row.agreementDelete ? '已删除' : '' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="160">
              <template slot-scope="scope">
                <el-button v-if="!scope.row.agreementDelete" size="mini" type="primary" @click="handleDetails(scope.$index, scope.row)">编辑</el-button>
                <el-button v-if="!scope.row.agreementDelete" size="mini" type="danger" @click="handleDelAgreement(scope.$index, scope.row)">删除</el-button>
                <el-button v-if="scope.row.agreementDelete&&role == 'admin_role_master'" size="mini" type="success" @click="handleRestoreAgreement(scope.$index, scope.row)">还原</el-button>
                <el-button v-if="scope.row.agreementDelete&&role == 'admin_role_master'" size="mini" type="danger" @click="handleRelDelAgreement(scope.$index, scope.row)">彻底删除</el-button>
                <el-button v-if="!scope.row.agreementDelete&&!scope.row.shareState" size="mini" type="success" @click="handleAddToCart(scope.$index, scope.row)">添加分享</el-button>
                <el-button v-if="!scope.row.agreementDelete&&scope.row.shareState" size="mini" type="danger" @click="handleDelFormCart(scope.$index, scope.row)">移除分享</el-button>
                <el-button v-if="!scope.row.agreementDelete" size="mini" type="info" @click="handleDownload(scope.$index, scope.row)">下载</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
            <el-pagination background @size-change="handleAgreementTableSizeChange" @current-change="handleAgreementTableCurrentChange" :hide-on-single-page="false" :current-page="agreementPage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="agreementPage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="agreementPage.totalCount"></el-pagination>
          </div>
        </el-col>
      </el-row>
    </el-card>
    <el-drawer title="待分享列表" :visible.sync="shareForm.visible" direction="rtl" size="770px" @closed="initAgreementList">
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
            <el-form-item label="有效期：">
              <el-date-picker style="width:300px" v-model="shareForm.shareBeginDate" type="date" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择开始时间">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="→">
              <el-date-picker style="width:300px" v-model="shareForm.agreementSignDateEndStr" type="date" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择结束时间">
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
              <el-button type="primary" icon="el-icon-share" @click="addShare()">分享</el-button>
            </el-form-item>
          </el-row>
        </el-form>
      </el-row>
    </el-drawer>
    <!-- <el-dialog :title="sortForm.title" :visible.sync="sortForm.visible" width="40%" @closed="alterSort()">
      <el-row>
        <el-tag style="margin-left:20px;" v-for="tag in sortTags" :key="tag.name" closable @close="handleRemoveTag(tag)">
          {{tag.name}}
        </el-tag>
      </el-row>
      <div slot="footer" class="dialog-footer">

      </div>
    </el-dialog> -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      token: "",
      role: "",
      // role collage form 选择框
      agreementSelectForm: {
        visible: false,
        agreementName: "",
        agreementTypeList: [],
        agreementInnovation: "",
        agreementClient: "",
        agreementAmountBegin: undefined,
        agreementAmountEnd: undefined,
        agreementSignDateBeginStr: "",
        agreementSignDateEndStr: "",
        agreementProvider: "",
        agreementText: "",
        productTypeList: [],
        productModel: "",
        productNumberBegin: undefined,
        productNumberEnd: undefined,
      },
      agreementTypeSelectiveList: [],
      productTypeSelectiveList: [],
      agreementTableData: [],
      agreementPage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },
      //排序
      sortForm: {
        title: "排序",
        visible: false,
        field: { name: '上传时间', field: 'agreement_upload_date' },
        type: { name: '降序', type: 'desc' },
      },
      sortTags: [
        { name: '上传时间 降序', field: 'agreement_upload_date', type: 'desc' },
      ],
      fieldsDic: [
        { name: '名称', field: 'agreement_name' },
        { name: '类型', field: 'agreement_type' },
        { name: '是否信创', field: 'agreement_innovation' },
        { name: '客户名称', field: 'agreement_client' },
        { name: '总金额', field: 'agreement_amount' },
        { name: '签约日期', field: 'agreement_sign_date' },
        { name: '提供者', field: 'agreement_provider' },
        { name: '上传时间', field: 'agreement_upload_date' },
      ],
      typeDic: [
        { name: '升序', type: 'asc' },
        { name: '降序', type: 'desc' },
      ],
      click: {

      },
      sortValue: "agreement_upload_date ",

      shareForm: {
        visible: false,
        shareBeginDateStr: "",
        shareEndDateStr: "",
        shareHasPassword: true,
        sharePassword: "",
      },
      cartNum: 0,
      cartTableData: []


    }
  },
  methods: {
    initAgreementList() {



      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/agreement/getAgreementBySearch",
          {
            params: {
              agreement: this.agreementSelectForm,
              sort: this.sortValue,
              currentPage: this.agreementPage.current,
              showCount: this.agreementPage.showCount
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.agreementPage.totalCount = res.data.object.totalNum;
            this.agreementTableData = res.data.object.items;
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


    handleAddAgreement() {
      this.savePage()
      this.$router.push({ path: "/home/agreement/details" });
    },

    handleDetails(index, row) {
      this.savePage()
      this.$router.push({ path: "/home/agreement/details/" + row.agreementId });
    },

    // handleDelProject(index, row) {

    // },

    handleAgreementTableSizeChange(val) {
      this.agreementPage.showCount = val;
      this.agreementPage.current = 1;
      this.initAgreementList();
    },
    handleAgreementTableCurrentChange(val) {
      this.agreementPage.current = val;
      this.initAgreementList();
    },
    savePage() {
      localStorage.setItem("agreementListPageMessage", JSON.stringify({
        agreementSelectForm: this.agreementSelectForm,
        agreementPage: this.agreementPage,
        sortForm: this.sortForm,
        sortTags: this.sortTags,
        sortValue: this.sortValue,
      }))
    },

    handleRemoveTag(tag) {
      this.sortTags.splice(this.sortTags.indexOf(tag), 1);
    },
    handleAddTag() {
      var i = 0;
      for (var i = 0; i < this.sortTags.length; i++) {
        if (this.sortTags[i].field == this.sortForm.field.field) {
          break;
        }
      }
      if (i < this.sortTags.length) {
        this.sortTags.splice(i, 1);
      }
      this.sortTags.push(
        { name: this.sortForm.field.name + " " + this.sortForm.type.name, field: this.sortForm.field.field, type: this.sortForm.type.type }
      )
    },
    alterSort() {
      this.sortValue = "";
      for (var i = 0; i < this.sortTags.length; i++) {
        this.sortValue = this.sortValue + " " + this.sortTags[i].field + " " + this.sortTags[i].type + " ,";
      }
      this.sortValue = this.sortValue.substring(0, this.sortValue.length - 1);
      this.initAgreementList();
    },
    handleDelAgreement(index, row) {
      this.$confirm('您确定删除此合同吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading(this.$store.state.loadingOption2);
        this.axios.post("/agreement/alterAgreementDel", {
          params: {
            agreement: row
          }
        }, { headers: { token: this.token } }).then(res => {
          if (res.data.code === 200) {
            this.initAgreementList();
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
    handleRestoreAgreement(index, row) {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/agreement/alterAgreementRestore", {
        params: {
          agreement: row
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.initAgreementList();
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
    handleRelDelAgreement(index, row) {
      this.$confirm('此操作将永久删除该合同的所有相关记录，此操作不可恢复，您确定彻底删除此合同吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading(this.$store.state.loadingOption2);
        this.axios.post("/agreement/alterAgreementRelDel", {
          params: {
            agreement: row
          }
        }, { headers: { token: this.token } }).then(res => {
          if (res.data.code === 200) {
            this.initAgreementList();
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
    handleAddToCart(index, row) {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/agreement/addToCart", {
        params: {
          agreement: row
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          row.shareState = true;
          this.cartNum = this.cartNum + 1;

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
    handleDelFormCart(index, row) {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/agreement/delFromCart", {
        params: {
          agreement: row
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          row.shareState = false;
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
    handleDownload(index, row) {

    },
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
    var str = localStorage.getItem("agreementListPageMessage")
    if (str != "" && str != null && str != "null") {
      var agreementListPageMessage = JSON.parse(str)
      this.agreementSelectForm = agreementListPageMessage.agreementSelectForm
      this.agreementPage = agreementListPageMessage.agreementPage
      this.sortForm = agreementListPageMessage.sortForm
      this.sortTags = agreementListPageMessage.sortTags
      this.sortValue = agreementListPageMessage.sortValue
      localStorage.setItem("agreementListPageMessage", null)
    }
    // 加载学院列表数据
    this.initAgreementTypeList();
    // 加载admin table data
    this.initProductTypeStateList();

    this.initAgreementList();

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
#agreementList {
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