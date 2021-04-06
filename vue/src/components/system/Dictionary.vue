<template>
  <div id="dictionary">
    <el-button @click="excelForm.visible = true" type="success" style="margin-top:20px;margin-left:20px">Excel</el-button>
    <el-row>
      <el-col class="main-col" style="height:40%" :span="9">
        <!-- @click=点击按钮时执行的代码或方法 -->
        <el-button size="mini" icon="el-icon-plus" type="primary" @click="handleAddDictionaryType()">添加</el-button>

        <el-table :data="dictionaryTypeTableData" border style="width: 100%">
          <el-table-column label="ID" width="185">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.typeId == null ? '' : scope.row.typeId }}</span>
            </template>
          </el-table-column>
          <el-table-column label="名称">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.typeName == null ? '' : scope.row.typeName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="240">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="handleInitDictionary(scope.$index, scope.row)">查看</el-button>

              <el-button size="mini" :disabled="!scope.row.typeEdit" @click="handleUpdDictionaryType(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" :disabled="!scope.row.typeEdit" @click="handleDelDictionaryType(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
          <el-pagination background @size-change="handleDictionaryTypeSizeChange" @current-change="handleDictionaryTypeCurrentChange" :hide-on-single-page="false" :current-page="dictionaryTypePage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="dictionaryTypePage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="dictionaryTypePage.totalCount"></el-pagination>
        </div>
      </el-col>
      <!--==========================================================================================-->
      <el-col class="main-col" :span="15">
        <el-button size="mini" icon="el-icon-plus" type="primary" :disabled="dictionaryAddDisabled" @click="handleAddDictionary()">添加</el-button>
        <el-table :data="dictionaryTableData" border style="width: 100%">
          <el-table-column label="ID">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.dictionaryId == null ? '' : scope.row.dictionaryId }}</span>
            </template>
          </el-table-column>
          <el-table-column label="名称">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{
                scope.row.dictionaryName == null ? '' : scope.row.dictionaryName
                }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="代码" width="100">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{
                scope.row.dictionaryCode == null ? '' : scope.row.dictionaryCode
                }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="排序" width="100">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{
                scope.row.dictionarySort == null ? '' : scope.row.dictionarySort
                }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="父级别">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{
                scope.row.dictionaryFatherObj == null ? '' : scope.row.dictionaryFatherObj.dictionaryName == null ? '' : scope.row.dictionaryFatherObj.dictionaryName
                }}
              </span>
            </template>
          </el-table-column>

          <el-table-column label="操作" width="280">
            <template slot-scope="scope">
              <!-- <el-button size="mini" type="primary" @click="handleInitDictionaryAlias(scope.$index, scope.row)">别名映射</el-button> -->
              <el-button size="mini" :disabled="dictionaryAddDisabled" @click="handleUpdDictionary(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" :disabled="dictionaryAddDisabled" @click="handleDelDictionary(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
          <el-pagination background @size-change="handleDictionarySizeChange" @current-change="handleDictionaryCurrentChange" :hide-on-single-page="false" :current-page="dictionaryPage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="dictionaryPage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="dictionaryPage.totalCount"></el-pagination>
        </div>
      </el-col>
    </el-row>

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
                <el-button size="mini" type="success" @click="downloadExcel(scope.$index, scope.row)">下载Excel</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
        <el-col class="excel-col" :span="6">
          <el-row>
            <h1>字典导入</h1>
            <el-upload class="upload-demo" ref="upload" :action="axios.defaults.baseURL + '/dictionary/importDictionaryExcel'" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="fileList" :limit="1" :on-success="excelImportOver" :on-error="excelImportError" accept=".xlsx" :auto-upload="false" :headers="{'token': token}">
              <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
              <div slot="tip" class="el-upload__tip">只能上传xlsx文件</div>、
              <div slot="tip" class="el-upload__tip">此导入功能为覆盖模式(仅对可编辑字典有效)</div>
            </el-upload>
          </el-row>
          <!-- <el-row>
            <h1>字典映射导入</h1>
            <el-upload
            class="upload-demo"
            ref="upload2"
            :action="axios.defaults.baseURL + '/dictionary/importDictionaryAliasExcel'"
            :on-preview="handlePreview2"
            :on-remove="handleRemove2"
            :file-list="fileList2"
            :limit="1"
            :on-success="excelImportOver2"
            accept=".xls,.xlsx"
            :auto-upload="false"
            :headers="{'token': token}"
          >
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button
              style="margin-left: 10px;"
              size="small"
              type="success"
              @click="submitUpload2"
            >上传到服务器</el-button>
            <div slot="tip" class="el-upload__tip">只能上传xls/xlsx文件</div>
          </el-upload>
          </el-row> -->
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <!-- <el-button @click="tempAddUpdForm.visible = false">取 消</el-button>
        <el-button type="primary" @click="tempAlter()">确 定</el-button>-->
      </div>
    </el-dialog>

    <el-dialog :title="dictionaryTypeAddUpdForm.title" :visible.sync="dictionaryTypeAddUpdForm.visible" width="690px">
      <el-form label-position="left">
        <el-form-item label="ID：" :label-width="dictionaryTypeAddUpdForm.labelWidth">
          <el-input v-model="dictionaryTypeAddUpdForm.typeId" :disabled="dictionaryTypeAddUpdForm.submitState=='Upd'" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="名称：" :label-width="dictionaryTypeAddUpdForm.labelWidth">
          <el-input v-model="dictionaryTypeAddUpdForm.typeName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dictionaryTypeAddUpdForm.visible = false">取 消</el-button>
        <el-button type="primary" @click="altherDictionaryType()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="dictionaryAddUpdForm.title" :visible.sync="dictionaryAddUpdForm.visible" width="690px">
      <el-form label-position="left">
        <el-form-item label="ID：" :label-width="dictionaryAddUpdForm.labelWidth">
          <el-input v-model="dictionaryAddUpdForm.dictionaryId" :disabled="dictionaryAddUpdForm.submitState=='Upd'" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="名称：" :label-width="dictionaryAddUpdForm.labelWidth">
          <el-input :disabled="dictionaryAddDisabled" v-model="dictionaryAddUpdForm.dictionaryName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="代码：" :label-width="dictionaryAddUpdForm.labelWidth">
          <el-input :disabled="dictionaryAddDisabled" v-model="dictionaryAddUpdForm.dictionaryCode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序：" :label-width="dictionaryAddUpdForm.labelWidth">
          <el-input :disabled="dictionaryAddDisabled" v-model="dictionaryAddUpdForm.dictionarySort" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="父级别:" :label-width="dictionaryAddUpdForm.labelWidth">
          <el-select :disabled="dictionaryAddDisabled" v-model="dictionaryAddUpdForm.dictionaryFather" placeholder="选择父级别">
            <el-option label="无" value=""></el-option>
            <el-option v-for="item in dictionaryFatherList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
          </el-select>
        </el-form-item>

        <!-- <el-form-item label="别名：" :label-width="dictionaryAddUpdForm.labelWidth">
          <el-input type="textarea" v-model="dictionaryAddUpdForm.dictionaryAliasStr" autocomplete="off"></el-input>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dictionaryAddUpdForm.visible = false">取 消</el-button>
        <el-button :disabled="dictionaryAddDisabled" type="primary" @click="altherDictionary()">确 定</el-button>
      </div>
    </el-dialog>

    <!-- <el-dialog
      title="字典映射管理"
      :visible.sync="dictionaryAliasForm.visible"
      width="90%"
    >
            <el-button
          size="mini"
          icon="el-icon-plus"
          type="primary"
          @click="handleAddDictionaryAlias()"
        >添加</el-button>
        <el-table :data="dictionaryAliasTableData" border style="width: 100%">
          <el-table-column label="省份">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{
                scope.row.aliasProvinceObj == null ? '' : scope.row.aliasProvinceObj.dictionaryName == null ? '' : scope.row.aliasProvinceObj.dictionaryName
                }}
              </span>
            </template>
          </el-table-column>
           <el-table-column label="外部代码">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{
                scope.row.aliasCode == null ? '' : scope.row.aliasCode
                }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="外部名称">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{
                scope.row.aliasName == null ? '' : scope.row.aliasName
                }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="参考方式">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{
                scope.row.aliasMethodObj == null ? '' : scope.row.aliasMethodObj.dictionaryName == null ? '' : scope.row.aliasMethodObj.dictionaryName
                }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="160">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleUpdDictionaryAlias(scope.$index, scope.row)">编辑</el-button>
              <el-button
                size="mini"
                type="danger"
                @click="handleDelDictionaryAlias(scope.$index, scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          background
          @size-change="handleDictionaryAliasSizeChange"
          @current-change="handleDictionaryAliasCurrentChange"
          :hide-on-single-page="false"
          :current-page="dictionaryAliasPage.current"
          :page-sizes="[5,10, 20, 30, 50, 100]"
          :page-size="dictionaryAliasPage.showCount"
          layout="total, sizes, prev, pager, next, jumper"
          :total="dictionaryAliasPage.totalCount"
        ></el-pagination>
<div slot="footer" class="dialog-footer">

      </div>
    </el-dialog> -->

    <!-- <el-dialog
      :title="dictionaryAliasAddUpdForm.title"
      :visible.sync="dictionaryAliasAddUpdForm.visible"
      width="690px"
    >
      <el-form label-position="left">
        <el-form-item label="ID：" :label-width="dictionaryAliasAddUpdForm.labelWidth">
           当提交方式为Upd时为编辑某一项时，此刻将输入ID框设为禁用 
          <el-input
            v-model="dictionaryAliasAddUpdForm.aliasId"
            :disabled="dictionaryAliasAddUpdForm.submitState=='Upd'" 
            autocomplete="off"
          ></el-input>
        </el-form-item>



        <el-form-item label="省份：" :label-width="dictionaryAliasAddUpdForm.labelWidth">
          <el-select filterable v-model="dictionaryAliasAddUpdForm.aliasProvince" placeholder="选择省份">
            <el-option
              v-for="item in aliasProvinceSelective"
              :key="item.dictionaryId"
              :label="item.dictionaryName"
              :value="item.dictionaryId"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="外部字典代码：" :label-width="dictionaryAliasAddUpdForm.labelWidth">
          <el-input v-model="dictionaryAliasAddUpdForm.aliasCode" autocomplete="off" > </el-input>
        </el-form-item>

        <el-form-item label="外部字典名称：" :label-width="dictionaryAliasAddUpdForm.labelWidth">
          <el-input v-model="dictionaryAliasAddUpdForm.aliasName" autocomplete="off" > </el-input>
        </el-form-item>

        <el-form-item label="参考方式：" :label-width="dictionaryAliasAddUpdForm.labelWidth">
          <el-select filterable v-model="dictionaryAliasAddUpdForm.aliasMethod" placeholder="选择参考方式">
            <el-option
              v-for="item in aliasMethodSelective"
              :key="item.dictionaryId"
              :label="item.dictionaryName"
              :value="item.dictionaryId"
            ></el-option>
          </el-select>
        </el-form-item>



      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dictionaryAliasAddUpdForm.visible = false">取 消</el-button>
         handelAdd或handelUpd为添加或修改前弹出dialog 准备数据的方法，而altherMajor是真正提交数据的方法 
        <el-button type="primary" @click="altherDictionaryAlias()">确 定</el-button>
      </div>
    </el-dialog> -->

  </div>
</template>
<script>
export default {
  data() {
    return {
      token: "",
      excelForm: {
        title: "Excel导入与导出",
        visible: false,
        excelOutTableData: [
          {
            fileName: "字典导入模板.xlsx",
            describe: "导出字典的空Excel表",
            url: "/dictionary/exportDictionaryNullExcel"
          },
          {
            fileName: "字典.xlsx",
            describe: "导出全部字典信息",
            url: "/dictionary/exportDictionaryExcel"
          },
          {
            fileName: "可编辑字典.xlsx",
            describe: "导出可编辑的字典信息",
            url: "/dictionary/exportDictionaryEditExcel"
          },
          // {
          //   fileName: "字典映射导入模板.xls",
          //   describe: "导出字典映射的空Excel表",
          //   url: "/system/excel/exportDictionaryAliasNullExcel"
          // }
          // ,
          // {
          //   fileName: "字典映射.xls",
          //   describe: "导出全部的字典映射信息",
          //   url: "/system/excel/exportDictionaryAliasExcel"
          // }
        ]
      },
      dictionaryAddDisabled: true,

      fileList: [],

      dictionaryTypeTableData: [],
      dictionaryTableData: [],
      dictionaryTypePage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },
      dictionaryTypeAddUpdForm: {
        title: "",
        visible: false,
        typeId: "",
        typeName: "",
        typeEdit: "",
        submitState: "",
        labelWidth: "120px"
      },

      dictionaryPage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },
      dictionarySelectForm: {
        typeId: ""
      },

      dictionaryAddUpdForm: {
        title: "",
        visible: false,
        dictionaryId: "",
        dictionaryName: "",
        dictionaryCode: "",
        dictionarySort: "",
        dictionaryType: "",
        //dictionaryAliasStr: "",
        dictionaryFather: "",
        labelWidth: "120px"
      },


      // dictionaryAliasForm: {
      //   visible: false,
      //   dictionaryId: ""
      // },
      // dictionaryAliasTableData: [],
      // dictionaryAliasPage: {
      //   showCount: 10,
      //   totalCount: 0,
      //   current: 1
      // },
      // dictionaryAliasAddUpdForm: {
      //   title: "",
      //   visible: false,
      //   aliasId: "",
      //   aliasProvince: "",
      //   aliasDictionary: "",
      //   aliasCode: "",
      //   aliasName: "",
      //   aliasMethod: "",
      //   submitState: "",
      //   labelWidth: "120px"
      // },
      dictionaryFatherList: [],
      // aliasMethodSelective: []



    };
  },
  methods: {
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
            this.initDictionaryType();
            this.fileList = [];
          }
        });
      } else if (response.code == 401) {
        this.$message({
          showClose: true,
          message: res.data.data,
          type: "error"
        });
        this.$router.push({ path: "/login" });
      } else if (response.code == 402) {
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
            this.initDictionaryType();
            this.fileList = [];
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
    // excelImportOver2(response, file, fileList) {
    //   if (response.code == 200) {
    //     this.$alert(response.data, '上传成功', {
    //       confirmButtonText: '确定',
    //       type: 'success',
    //       callback: action => {
    //         this.initDictionaryType();
    //       }
    //     });
    //   } else if (response.code == 401) {
    //     this.$router.push({ path: "/login" });
    //   } else {
    //     this.$alert(response.data, '上传失败', {
    //       confirmButtonText: '确定',
    //       type: 'error',
    //       callback: action => {
    //         this.initDictionaryType();
    //       }
    //     });
    //   }
    // },


    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    // submitUpload2() {
    //   this.$refs.upload2.submit();
    // },
    // handleRemove2(file, fileList) {
    //   console.log(file, fileList);
    // },
    // handlePreview2(file) {
    //   console.log(file);
    // },
    initDictionaryType() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      //todo
      this.axios
        .post("/dictionary/getDictionaryTypeBySearch", {
          params: {
            currentPage: this.dictionaryTypePage.current,
            showCount: this.dictionaryTypePage.showCount
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.dictionaryTypePage.totalCount = res.data.object.totalNum;
            this.dictionaryTypeTableData = res.data.object.items;
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
      this.dictionaryTableData = [];
      this.dictionaryAddDisabled = true;
    },


    handleAddDictionaryType() {
      this.dictionaryTypeAddUpdForm.title = "添加字典类别";
      this.dictionaryTypeAddUpdForm.visible = true;
      this.dictionaryTypeAddUpdForm.typeId = "";
      this.dictionaryTypeAddUpdForm.typeName = "";
      this.dictionaryTypeAddUpdForm.typeEdit = true;
      this.dictionaryTypeAddUpdForm.submitState = "Add";
    },
    handleUpdDictionaryType(index, row) {
      this.dictionaryTypeAddUpdForm.title = "修改字典类别";
      this.dictionaryTypeAddUpdForm.visible = true;
      this.dictionaryTypeAddUpdForm.typeId = row.typeId;
      this.dictionaryTypeAddUpdForm.typeName = row.typeName;
      this.dictionaryTypeAddUpdForm.typeEdit = true;
      this.dictionaryTypeAddUpdForm.submitState = "Upd";
    },
    handleDelDictionaryType(index, row) {
      this.$confirm('此操作必将导致系统不稳定，请在技术人员指导下进行操作，您确定删除此字典类别吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.dictionaryTypeAddUpdForm.typeId = row.typeId;
        this.dictionaryTypeAddUpdForm.submitState = "Del";
        this.altherDictionaryType();
      }).catch(() => {

      });


    },
    altherDictionaryType() {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios
        .post("/dictionary/altherDictionaryType", {
          params: {
            dictionaryType: this.dictionaryTypeAddUpdForm
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.initDictionaryType();
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "success"
            });
            this.dictionaryTypeAddUpdForm.visible = false;
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
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "error"
            });
          }
          loading.close();
        });
    },
    handleDictionaryTypeSizeChange(val) {
      this.dictionaryTypePage.showCount = val;
      this.dictionaryTypePage.current = 1;
      this.initDictionaryType();
    },
    handleDictionaryTypeCurrentChange(val) {
      this.dictionaryTypePage.current = val;
      this.initDictionaryType();
    },
    handleInitDictionary(index, row) {


      this.dictionarySelectForm.typeId = row.typeId;
      this.dictionaryPage.current = 1;
      this.initDictionary();
      this.dictionaryAddDisabled = !row.typeEdit;
    },
    initDictionary() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      //todo
      this.axios
        .post("/dictionary/getDictionaryBySearch", {
          params: {
            currentPage: this.dictionaryPage.current,
            showCount: this.dictionaryPage.showCount,
            dictionary: {
              dictionaryType: this.dictionarySelectForm.typeId
            }
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.dictionaryPage.totalCount = res.data.object.totalNum;
            this.dictionaryTableData = res.data.object.items;

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
      this.dictionaryAddUpdForm.dictionaryType = this.dictionarySelectForm.typeId;

    },
    handleAddDictionary() {
      this.initDictionaryFatherList();
      this.dictionaryAddUpdForm.title = "添加字典信息";
      this.dictionaryAddUpdForm.visible = true;
      this.dictionaryAddUpdForm.dictionaryId = "";
      this.dictionaryAddUpdForm.dictionaryName = "";
      this.dictionaryAddUpdForm.dictionaryCode = "";
      this.dictionaryAddUpdForm.dictionarySort = "";
      this.dictionaryAddUpdForm.dictionaryFather = "";
      //this.dictionaryAddUpdForm.dictionaryType = "";
      this.dictionaryAddUpdForm.dictionaryAliasStr = "";
      this.dictionaryAddUpdForm.submitState = "Add";
    },
    handleUpdDictionary(index, row) {
      this.initDictionaryFatherList();
      this.dictionaryAddUpdForm.title = "添加字典信息";
      this.dictionaryAddUpdForm.visible = true;
      this.dictionaryAddUpdForm.dictionaryId = row.dictionaryId;
      this.dictionaryAddUpdForm.dictionaryName = row.dictionaryName;
      this.dictionaryAddUpdForm.dictionaryCode = row.dictionaryCode;
      this.dictionaryAddUpdForm.dictionarySort = row.dictionarySort;
      this.dictionaryAddUpdForm.dictionaryFather = (row.dictionaryFather == null ? "" : row.dictionaryFather);
      //this.dictionaryAddUpdForm.dictionaryType = row.dictionaryType;
      this.dictionaryAddUpdForm.dictionaryAliasStr = row.dictionaryAliasStr;
      this.dictionaryAddUpdForm.submitState = "Upd";
    },
    handleDelDictionary(index, row) {

      this.$confirm('此操作必将导致系统不稳定，请在技术人员指导下进行操作，您确定删除此字典吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.dictionaryAddUpdForm.dictionaryId = row.dictionaryId;
        this.dictionaryAddUpdForm.submitState = "Del";
        const loading = this.$loading(this.$store.state.loadingOption2);
        this.altherDictionary();
      }).catch(() => {

      });
    },

    handleDictionarySizeChange(val) {
      this.dictionaryPage.showCount = val;
      this.dictionaryPage.current = 1;
      this.initDictionary();
    },
    handleDictionaryCurrentChange(val) {
      this.dictionaryPage.current = val;
      this.initDictionary();
    },
    altherDictionary() {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios
        .post("/dictionary/altherDictionary", {
          params: {
            dictionary: this.dictionaryAddUpdForm
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.initDictionary();
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "success"
            });
            this.dictionaryAddUpdForm.visible = false;
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
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "error"
            });
          }
          loading.close();
        });
    },
    // handleInitDictionaryAlias(index, row) {
    //   this.dictionaryAliasForm.visible = true;
    //   this.dictionaryAliasForm.dictionaryId = row.dictionaryId;
    //   this.initDictionaryAlias();
    // },
    // handleAddDictionaryAlias() {
    //   this.dictionaryAliasAddUpdForm.title = "添加字典映射";
    //   this.dictionaryAliasAddUpdForm.visible = true;
    //   this.dictionaryAliasAddUpdForm.aliasId = "";
    //   this.dictionaryAliasAddUpdForm.aliasProvince = "";
    //   this.dictionaryAliasAddUpdForm.aliasDictionary = this.dictionaryAliasForm.dictionaryId;

    //   this.dictionaryAliasAddUpdForm.aliasCode = "";
    //   this.dictionaryAliasAddUpdForm.aliasName = "";
    //   this.dictionaryAliasAddUpdForm.aliasMethod = "";
    //   this.dictionaryAliasAddUpdForm.submitState = "Add";
    // },
    // handleUpdDictionaryAlias(index, row) {
    //   this.dictionaryAliasAddUpdForm.title = "修改字典映射";
    //   this.dictionaryAliasAddUpdForm.visible = true;
    //   this.dictionaryAliasAddUpdForm.aliasId = row.aliasId;
    //   this.dictionaryAliasAddUpdForm.aliasProvince = row.aliasProvince;
    //   this.dictionaryAliasAddUpdForm.aliasDictionary = this.dictionaryAliasForm.dictionaryId;
    //   this.dictionaryAliasAddUpdForm.aliasCode = row.aliasCode;
    //   this.dictionaryAliasAddUpdForm.aliasName = row.aliasName;
    //   this.dictionaryAliasAddUpdForm.aliasMethod = row.aliasMethod;
    //   this.dictionaryAliasAddUpdForm.submitState = "Upd";
    // },
    // handleDelDictionaryAlias(index, row) {
    //   this.dictionaryAliasAddUpdForm.aliasId = row.aliasId;
    //   this.dictionaryAliasAddUpdForm.aliasProvince = row.aliasProvince;
    //   this.dictionaryAliasAddUpdForm.aliasDictionary = this.dictionaryAliasForm.dictionaryId;
    //   this.dictionaryAliasAddUpdForm.aliasCode = row.aliasCode;
    //   this.dictionaryAliasAddUpdForm.aliasName = row.aliasName;
    //   this.dictionaryAliasAddUpdForm.aliasMethod = row.aliasMethod;
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios
    //     .post(
    //       "/dictionary/removeDictionaryAlias",
    //       {
    //         params: {
    //           dictionaryAlias: this.dictionaryAliasAddUpdForm
    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.initDictionaryAlias();
    //         this.$message({
    //           showClose: true,
    //           message: res.data.data,
    //           type: "success"
    //         });
    //       } else if (res.data.code == 401) {
    //         this.$router.push({ path: "/login" });
    //       } else {
    //         this.initDictionaryAlias();
    //         this.$message({
    //           showClose: true,
    //           message: res.data.data,
    //           type: "error"
    //         });
    //       }
    //       loading.close();
    //     });
    // },
    // handleDictionaryAliasSizeChange(val) {
    //   this.dictionaryAliasPage.showCount = val;
    //   this.dictionaryAliasPage.current = 1;
    //   this.initDictionaryAlias();
    // },
    // handleDictionaryAliasCurrentChange(val) {
    //   this.dictionaryAliasPage.current = val;
    //   this.initDictionaryAlias();
    // },
    // initDictionaryAlias() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   //todo
    //   this.axios
    //     .post(
    //       "/system/getAllDictionaryAliasByDictionary",
    //       {
    //         params: {
    //           dictionaryId: this.dictionaryAliasForm.dictionaryId,
    //           currentPage: this.dictionaryAliasPage.current,
    //           showCount: this.dictionaryAliasPage.showCount
    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.dictionaryAliasPage.totalCount = res.data.data.totalNum;
    //         this.dictionaryAliasTableData = res.data.data.items;
    //       } else if (res.data.code == 401) {
    //         this.$router.push({ path: "/login" });
    //       } else {
    //         this.$alert(res.data.data, "错误", {
    //           confirmButtonText: "确定",
    //           type: "error",
    //           callback: action => {}
    //         });
    //       }
    //       loading.close();
    //     });
    // },
    // altherDictionaryAlias() {
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios
    //     .post(
    //       "/system/alterDictionaryAlias",
    //       {
    //         params: {
    //           dictionaryAlias: this.dictionaryAliasAddUpdForm
    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.initDictionaryAlias();
    //         this.$message({
    //           showClose: true,
    //           message: res.data.data,
    //           type: "success"
    //         });
    //         this.dictionaryAliasAddUpdForm.visible = false;
    //       } else if (res.data.code == 401) {
    //         this.$router.push({ path: "/login" });
    //       } else {
    //         this.$message({
    //           showClose: true,
    //           message: res.data.data,
    //           type: "error"
    //         });
    //       }
    //       loading.close();
    //     });
    // },
    initDictionaryFatherList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/dictionary/getDictionaryItems",
          {
            params: {
              dictionaryType: this.dictionarySelectForm.typeId
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.dictionaryFatherList = res.data.object;
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
    // initAliasMethodSelective() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post(
    //       "/system/getDictionaryItems",
    //       {
    //         params: {
    //           dictionaryType: "ATTR_REF_TYPE"
    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.aliasMethodSelective = res.data.data;
    //       } else if (res.data.code == 401) {
    //         this.$router.push({ path: "/login" });
    //       } else {
    //         this.$alert(res.data.data, "错误", {
    //           confirmButtonText: "确定",
    //           type: "error",
    //           callback: action => {}
    //         });
    //       }
    //       loading.close();
    //     });
    // }



  },
  created: function () {
    //登陆验证代码
    //权限验证代码
    this.token = localStorage.getItem("token");
    if (this.token == "" || this.token == null) {
      this.$router.push({ path: "/login" });
      return;
    }
    this.initDictionaryType();
    //this.initAliasProvinceSelective();
    //this.initAliasMethodSelective();
  }
};
</script>

<style  lang="scss">
#dictionary {
  .el-main {
    padding: 0px;
  }
  .main-col {
    padding: 20px;
  }
}
</style>