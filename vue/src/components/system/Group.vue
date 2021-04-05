<template>
  <div id="group">
    <el-form :inline="true" class="demo-form-inline">
      <!-- <el-form-item label="姓名">
          <el-input
            v-model="userSelectForm.userName"
            placeholder="姓名"
            style="width: 200px;"
          ></el-input>
        </el-form-item>
        <el-form-item label="账号">
          <el-input
            v-model="userSelectForm.userAccount"
            placeholder="账号"
            style="width: 200px;"
          ></el-input>
        </el-form-item> -->

      <el-form-item>
        <!-- <el-button type="primary" @click="userPage.current=1;initUser()">查询</el-button> -->
        <el-button @click="excelForm.visible = true" type="success">Excel</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-col class="main-col" :span="24">

        <!-- @click=点击按钮时执行的代码或方法 -->
        <el-button size="mini" icon="el-icon-plus" type="primary" @click="handleAddGroup()">添加</el-button>

        <el-table :data="groupTableData" border style="width: 100%">
          <el-table-column label="ID">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.groupId == null ? '' : scope.row.groupId }}</span>
            </template>
          </el-table-column>
          <el-table-column label="招生组名称">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                {{
                scope.row.groupName == null ? '不参考' : scope.row.groupName
                }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="240">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleUpdGroup(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelGroup(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
          <el-pagination background @size-change="handleGroupSizeChange" @current-change="handleGroupCurrentChange" :hide-on-single-page="false" :current-page="groupPage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="groupPage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="groupPage.totalCount"></el-pagination>
        </div>
      </el-col>
    </el-row>
    <!--==========================================================================================-->
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
          <h1>导入</h1>
          <el-upload class="upload-demo" ref="upload" :action="axios.defaults.baseURL + '/group/importGroupExcel'" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="fileList" :limit="1" :on-success="excelImportOver" :on-error="excelImportError" accept=".xlsx" :auto-upload="false" :headers="{'token': token}">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
            <div slot="tip" class="el-upload__tip">只能上传xlsx文件</div>
            <div slot="tip" class="el-upload__tip">此导入功能为覆盖模式</div>
          </el-upload>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">

      </div>
    </el-dialog>
    <el-dialog :title="groupAddUpdForm.title" :visible.sync="groupAddUpdForm.visible" width="690px">
      <el-form label-position="left">
        <el-form-item label="ID：" :label-width="groupAddUpdForm.labelWidth">
          <el-input v-model="groupAddUpdForm.groupId" :disabled="groupAddUpdForm.submitState=='Upd'" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="招生组名称：" :label-width="groupAddUpdForm.labelWidth">
          <el-input v-model="groupAddUpdForm.groupName" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="groupAddUpdForm.visible = false">取 消</el-button>
        <el-button type="primary" @click="alterGroup()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import store from "../../vuex/store.js";
export default {
  store,
  data() {
    return {
      token: "",
      excelForm: {
        title: "Excel导入与导出",
        visible: false,
        excelOutTableData: [
          {
            fileName: "招生组导入模板.xlsx",
            describe: "导出招生组的空Excel表",
            url: "/group/exportGroupNullExcel"
          },
          {
            fileName: "招生组全部数据.xlsx",
            describe: "导出全部招生组",
            url: "/group/exportGroupAllExcel"
          }
        ]
      },
      fileList: [],

      groupTableData: [],
      groupPage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },
      groupAddUpdForm: {
        title: "",
        visible: false,
        groupId: "",
        groupName: "",
        submitState: "",
        labelWidth: "120px"
      },
      //==============================================================================================
    };
  },
  methods: {
    downloadExcel(index, item) {
      // let date = item.plans[this.daysIndex[index]];
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
        this.$alert(response.data, '上传成功', {
          confirmButtonText: '确定',
          type: 'success',
          callback: action => {
            this.initGroup();
            this.fileList = [];
            this.excelForm.visible = false;
          }
        });
      } else if (response.code == 401) {
        this.$router.push({ path: "/login" });
      } else {
        this.$alert(response.data, '上传失败', {
          confirmButtonText: '确定',
          type: 'error',
          callback: action => {
            this.initGroup();
            this.fileList = []
          }
        });
      }
    },
    excelImportError(error, response, file, fileList) {
      this.$alert('服务器错误', '上传失败', {
        confirmButtonText: '确定',
        type: 'error',
        callback: action => {
          this.initGroup();
          this.fileList = []
        }
      });
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    initGroup() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/group/getAllGroupBySearch", {
          params: {
            currentPage: this.groupPage.current,
            showCount: this.groupPage.showCount
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.groupPage.totalCount = res.data.object.totalNum;
            this.groupTableData = res.data.object.items;
          } else if (res.data.code == 401) {
            this.$router.push({ path: "/login" });
          } else if (res.data.code == 402) {
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
    handleAddGroup() {
      this.groupAddUpdForm.title = "添加招生组信息";
      this.groupAddUpdForm.visible = true;
      this.groupAddUpdForm.groupId = "";
      this.groupAddUpdForm.groupName = "";
      this.groupAddUpdForm.submitState = "Add";
    },
    handleUpdGroup(index, row) {
      this.groupAddUpdForm.title = "修改招生组信息";
      this.groupAddUpdForm.visible = true;
      this.groupAddUpdForm.groupId = row.groupId;
      this.groupAddUpdForm.groupName = row.groupName;
      this.groupAddUpdForm.submitState = "Upd";
    },
    handleDelGroup(index, row) {
      this.$confirm('此操作将永久删除该招生组所有相关记录，包括用户申请的该招生组的组长或组员角色，以及该招生组的所有备案和任务记录，删除后不可恢复，请慎重操作，您确定删除该招生组吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.groupAddUpdForm.groupId = row.groupId;
        this.groupAddUpdForm.submitState = "Del";
        this.alterGroup();
      }).catch(() => {

      });
    },
    handleGroupSizeChange(val) {
      this.groupPage.showCount = val;
      this.groupPage.current = 1;
      this.initGroup();
    },
    handleGroupCurrentChange(val) {
      this.groupPage.current = val;
      this.initGroup();
    },
    alterGroup() {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios
        .post("/group/alterGroup", {
          params: {
            group: this.groupAddUpdForm
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.initGroup();
            this.$message({
              showClose: true,
              message: res.data.data,
              type: "success"
            });
            this.groupAddUpdForm.visible = false;
          } else if (res.data.code == 401) {
            this.$router.push({ path: "/login" });
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
  },
  created: function () {
    //登陆验证代码
    //权限验证代码
    this.token = localStorage.getItem("token");
    if (this.token == "" || this.token == null) {
      this.$router.push({ path: "/login" });
      return;
    }

    this.initGroup();

  }
};
</script>

<style  lang="scss">
#group {
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