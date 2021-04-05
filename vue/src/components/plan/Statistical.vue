<template>
  <div id="statistical">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="用户：">
        <el-select v-model="selectForm.admin" placeholder="选择用户" style="width: 200px;">
          <el-option label="全部" value=""></el-option>
          <el-option v-for="item in adminSelectiveList" :key="item.adminId" :label="item.adminName" :value="item.adminId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="招生组：">
        <el-select v-model="selectForm.group" placeholder="选择招生组" style="width: 200px;">
          <el-option label="全部" value=""></el-option>
          <el-option v-for="item in groupSelectiveList" :key="item.groupId" :label="item.groupName" :value="item.groupId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学院/部门：">
        <el-select v-model="selectForm.college" placeholder="学院/部门" style="width: 200px;">
          <el-option label="全部" value=""></el-option>
          <el-option v-for="item in collegeSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search()">查询</el-button>
        <el-button @click="downloadExcel()" type="success">导出Excel</el-button>
      </el-form-item>
    </el-form>
    <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="出行基本情况统计" name="taskTravel">
        <el-row>
          <el-col class="main-col" :span="24">
            <el-table :data="taskTravelTableData" border style="width: 100%">
              <el-table-column label="工号">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminAccount == null ? '' : scope.row.adminAccount }}</span>
                </template>
              </el-table-column>
              <el-table-column label="姓名">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminName == null ? '' : scope.row.adminName }}</span>
                </template>
              </el-table-column>
              <el-table-column label="学院/部门">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminCollege == null ? '' : scope.row.adminCollege }}</span>
                </template>
              </el-table-column>
              <el-table-column label="职称/职位">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminPosition == null ? '' : scope.row.adminPosition }}</span>
                </template>
              </el-table-column>
              <el-table-column label="电话">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminPhone == null ? '' : scope.row.adminPhone }}</span>
                </template>
              </el-table-column>
              <el-table-column label="出发城市">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.travelBeginCity == null ? '' : scope.row.travelBeginCity }}</span>
                </template>
              </el-table-column>
              <el-table-column label="目的地城市">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.travelEndCity == null ? '' : scope.row.travelEndCity }}</span>
                </template>
              </el-table-column>
              <el-table-column label="出发日期">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.travelDepartureDateStr == null ? '' : scope.row.travelDepartureDateStr }}</span>
                </template>
              </el-table-column>
              <el-table-column label="返程日期">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.travelReturnDateStr == null ? '' : scope.row.travelReturnDateStr }}</span>
                </template>
              </el-table-column>
            </el-table>
            <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
              <el-pagination background @size-change="handletaskTravelSizeChange" @current-change="handletaskTravelCurrentChange" :hide-on-single-page="false" :current-page="taskTravelPage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="taskTravelPage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="taskTravelPage.totalCount"></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="工作开展统计" name="taskItem">
        <el-row>
          <el-col class="main-col" :span="24">
            <el-table :data="taskItemTableData" border style="width: 100%">
              <el-table-column label="工号">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminAccount == null ? '' : scope.row.adminAccount }}</span>
                </template>
              </el-table-column>
              <el-table-column label="姓名">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminName == null ? '' : scope.row.adminName }}</span>
                </template>
              </el-table-column>
              <el-table-column label="学院/部门">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminCollege == null ? '' : scope.row.adminCollege }}</span>
                </template>
              </el-table-column>
              <el-table-column label="职称/职位">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminPosition == null ? '' : scope.row.adminPosition }}</span>
                </template>
              </el-table-column>
              <el-table-column label="电话">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminPhone == null ? '' : scope.row.adminPhone }}</span>
                </template>
              </el-table-column>
              <el-table-column label="出差省市">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.itemCity == null ? '' : scope.row.itemCity }}</span>
                </template>
              </el-table-column>
              <el-table-column label="日期">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.itemDateStr == null ? '' : scope.row.itemDateStr }}</span>
                </template>
              </el-table-column>
              <el-table-column label="任务类型">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.itemType == null ? '' : scope.row.itemType }}</span>
                </template>
              </el-table-column>
              <el-table-column label="中学/咨询会名称">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.itemName == null ? '' : scope.row.itemName }}</span>
                </template>
              </el-table-column>
            </el-table>
            <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
              <el-pagination background @size-change="handleTaskItemSizeChange" @current-change="handleTaskItemCurrentChange" :hide-on-single-page="false" :current-page="taskItemPage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="taskItemPage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="taskItemPage.totalCount"></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="学术报告统计" name="taskLecture">
        <el-row>
          <el-col class="main-col" :span="24">
            <el-table :data="taskLectureTableData" border style="width: 100%">
              <el-table-column label="中学名称">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.lectureSchool == null ? '' : scope.row.lectureSchool }}</span>
                </template>
              </el-table-column>
              <el-table-column label="报告类型">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.lectureType == null ? '' : scope.row.lectureType }}</span>
                </template>
              </el-table-column>
              <el-table-column label="报告人">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.lectureAdmin == null ? '' : scope.row.lectureAdmin }}</span>
                </template>
              </el-table-column>
              <el-table-column label="受众群体">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.lectureAudienceType == null ? '' : scope.row.lectureAudienceType }}</span>
                </template>
              </el-table-column>
              <el-table-column label="受众人数">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.lectureAudienceNumber == null ? '' : scope.row.lectureAudienceNumber }}</span>
                </template>
              </el-table-column>
              <el-table-column label="活动时长">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.lectureDuration == null ? '' : scope.row.lectureDuration }}</span>
                </template>
              </el-table-column>

            </el-table>
            <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
              <el-pagination background @size-change="handleTaskLectureSizeChange" @current-change="handleTaskLectureCurrentChange" :hide-on-single-page="false" :current-page="taskLecturePage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="taskLecturePage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="taskLecturePage.totalCount"></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="招生组情况统计" name="groupMessage">
        <el-row>
          <el-col class="main-col" :span="24">
            <el-table :data="groupMessageTableData" border style="width: 100%">
              <el-table-column label="招生组">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.group == null ? '' : scope.row.group }}</span>
                </template>
              </el-table-column>
              <el-table-column label="招生宣传出行次数">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.travelNum == null ? '' : scope.row.travelNum }}</span>
                </template>
              </el-table-column>
              <el-table-column label="招生宣传出行人次">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminTimes == null ? '' : scope.row.adminTimes }}</span>
                </template>
              </el-table-column>
              <el-table-column label="招生宣传参与人数">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminNum == null ? '' : scope.row.adminNum }}</span>
                </template>
              </el-table-column>
              <el-table-column label="走访中学数量">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.schoolNum == null ? '' : scope.row.schoolNum }}</span>
                </template>
              </el-table-column>
              <el-table-column label="参加咨询会场次">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.itemsNum == null ? '' : scope.row.itemsNum }}</span>
                </template>
              </el-table-column>
              <el-table-column label="新建优秀生源基地数量">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.newBaseNum == null ? '' : scope.row.newBaseNum }}</span>
                </template>
              </el-table-column>
              <el-table-column label="开展学术报告场次">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.lectureNum == null ? '' : scope.row.lectureNum }}</span>
                </template>
              </el-table-column>
            </el-table>
            <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
              <el-pagination background @size-change="handleGroupMessageSizeChange" @current-change="handleGroupMessageCurrentChange" :hide-on-single-page="false" :current-page="groupMessagePage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="groupMessagePage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="groupMessagePage.totalCount"></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="学院部门情况统计" name="collegeMessage">
        <el-row>
          <el-col class="main-col" :span="24">
            <el-table :data="collegeMessageTableData" border style="width: 100%">
              <el-table-column label="学院/部门">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.college == null ? '' : scope.row.college }}</span>
                </template>
              </el-table-column>
              <el-table-column label="招生宣传出行次数">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.travelNum == null ? '' : scope.row.travelNum }}</span>
                </template>
              </el-table-column>
              <el-table-column label="招生宣传出行人次">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminTimes == null ? '' : scope.row.adminTimes }}</span>
                </template>
              </el-table-column>
              <el-table-column label="招生宣传参与人数">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.adminNum == null ? '' : scope.row.adminNum }}</span>
                </template>
              </el-table-column>
              <el-table-column label="走访中学数量">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.schoolNum == null ? '' : scope.row.schoolNum }}</span>
                </template>
              </el-table-column>
              <el-table-column label="参加咨询会场次">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.itemsNum == null ? '' : scope.row.itemsNum }}</span>
                </template>
              </el-table-column>
              <el-table-column label="新建优秀生源基地数量">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.newBaseNum == null ? '' : scope.row.newBaseNum }}</span>
                </template>
              </el-table-column>
              <el-table-column label="开展学术报告场次">
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.lectureNum == null ? '' : scope.row.lectureNum }}</span>
                </template>
              </el-table-column>
            </el-table>
            <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
              <el-pagination background @size-change="handleCollegeMessageSizeChange" @current-change="handleCollegeMessageCurrentChange" :hide-on-single-page="false" :current-page="collegeMessagePage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="collegeMessagePage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="collegeMessagePage.totalCount"></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>

    <!--==========================================================================================-->
    <!-- <el-dialog :title="excelForm.title" :visible.sync="excelForm.visible" width="60%">
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
          <el-upload class="upload-demo" ref="upload" :action="axios.defaults.baseURL + '/group/importGroupExcel'" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="fileList" :limit="1" :on-success="excelImportOver" :on-error="excelImportError" accept=".xls" :auto-upload="false" :headers="{'token': token}">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
            <div slot="tip" class="el-upload__tip">只能上传xls文件</div>
            <div slot="tip" class="el-upload__tip">此导入功能为覆盖模式</div>
          </el-upload>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">

      </div>
    </el-dialog> -->

  </div>
</template>
<script>
import store from "../../vuex/store.js";
export default {
  store,
  data() {
    return {
      token: "",
      activeName: 'taskTravel',
      selectForm: {
        admin: "",
        group: "",
        college: ""
      },
      lastSelectForm: {
        admin: "",
        group: "",
        college: ""
      },
      taskTravelTableData: [],
      taskTravelPage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },
      taskItemTableData: [],
      taskItemPage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },
      taskLectureTableData: [],
      taskLecturePage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },
      collegeMessageTableData: [],
      collegeMessagePage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },
      groupMessageTableData: [],
      groupMessagePage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },





      // excelForm: {
      //   title: "Excel导入与导出",
      //   visible: false,
      //   excelOutTableData: [
      //     {
      //       fileName: "招生组导入模板.xls",
      //       describe: "导出招生组的空Excel表",
      //       url: "/group/exportGroupNullExcel"
      //     },
      //     {
      //       fileName: "招生组全部数据.xls",
      //       describe: "导出全部招生组",
      //       url: "/group/exportGroupAllExcel"
      //     }
      //   ]
      // },
      // fileList: [],

      //==============================================================================================
    };
  },
  methods: {
    search() {
      this.taskTravelPage.current = 1;
      this.initTaskTravel();
      this.taskItemPage.current = 1;
      this.initTaskItem();
      this.taskLecturePage.current = 1;
      this.initTaskLecture();
      this.collegeMessagePage.current = 1;
      this.initCollegeMessage();
      this.groupMessagePage.current = 1;
      this.initGroupMessage();
    },
    handleClick(tab, event) {
      if (this.lastSelectForm.admin != this.selectForm.admin || this.lastSelectForm.group != this.selectForm.group || this.lastSelectForm.college != this.selectForm.college) {
        this.search();
      }
    },


    downloadExcel(index, item) {
      // let date = item.plans[this.daysIndex[index]];
      const loading = this.$loading(this.$store.state.loadingOption1);
      let url = "/statistical/exportExcel?admin=" + this.selectForm.admin + "&group=" + this.selectForm.group + "&college=" + this.selectForm.college;
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
        link.setAttribute("download", "统计信息.xlsx");
        document.body.appendChild(link);
        link.click();
        loading.close();
      });
    },
    // excelImportOver(response, file, fileList) {

    //   if (response.code == 200) {
    //     this.$alert(response.data, '上传成功', {
    //       confirmButtonText: '确定',
    //       type: 'success',
    //       callback: action => {
    //         this.initGroup();
    //         this.fileList = [];
    //         this.excelForm.visible = false;
    //       }
    //     });
    //   } else if (response.code == 401) {
    //     this.$router.push({ path: "/login" });
    //   } else {
    //     this.$alert(response.data, '上传失败', {
    //       confirmButtonText: '确定',
    //       type: 'error',
    //       callback: action => {
    //         this.initGroup();
    //         this.fileList = []
    //       }
    //     });
    //   }
    // },
    // excelImportError(error, response, file, fileList) {
    //   this.$alert('服务器错误', '上传失败', {
    //     confirmButtonText: '确定',
    //     type: 'error',
    //     callback: action => {
    //       this.initGroup();
    //       this.fileList = []
    //     }
    //   });
    // },
    // submitUpload() {
    //   this.$refs.upload.submit();
    // },
    // handleRemove(file, fileList) {
    //   console.log(file, fileList);
    // },
    // handlePreview(file) {
    //   console.log(file);
    // },

    initTaskTravel() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/statistical/getTaskTravelBySearch", {
          params: {
            currentPage: this.taskTravelPage.current,
            showCount: this.taskTravelPage.showCount,
            admin: this.selectForm.admin,
            group: this.selectForm.group,
            college: this.selectForm.college,
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.taskTravelPage.totalCount = res.data.object.totalNum;
            this.taskTravelTableData = res.data.object.items;
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
    handleTaskTravelSizeChange(val) {
      this.taskTravelPage.showCount = val;
      this.taskTravelPage.current = 1;
      this.initTaskTravel();
    },
    handleTaskTravelCurrentChange(val) {
      this.taskTravelPage.current = val;
      this.initTaskTravel();
    },

    initTaskItem() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/statistical/getTaskItemBySearch", {
          params: {
            currentPage: this.taskItemPage.current,
            showCount: this.taskItemPage.showCount,
            admin: this.selectForm.admin,
            group: this.selectForm.group,
            college: this.selectForm.college,
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.taskItemPage.totalCount = res.data.object.totalNum;
            this.taskItemTableData = res.data.object.items;
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
    handleTaskItemSizeChange(val) {
      this.taskItemPage.showCount = val;
      this.taskItemPage.current = 1;
      this.initTaskItem();
    },
    handleTaskItemCurrentChange(val) {
      this.taskItemPage.current = val;
      this.initTaskItem();
    },

    initTaskLecture() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/statistical/getTaskLectureBySearch", {
          params: {
            currentPage: this.taskLecturePage.current,
            showCount: this.taskLecturePage.showCount,
            admin: this.selectForm.admin,
            group: this.selectForm.group,
            college: this.selectForm.college,
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.taskLecturePage.totalCount = res.data.object.totalNum;
            this.taskLectureTableData = res.data.object.items;
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
    handleTaskLectureSizeChange(val) {
      this.taskLecturePage.showCount = val;
      this.taskLecturePage.current = 1;
      this.initTaskLecture();
    },
    handleTaskLectureCurrentChange(val) {
      this.taskLecturePage.current = val;
      this.initTaskLecture();
    },

    initCollegeMessage() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/statistical/getCollegeMessageBySearch", {
          params: {
            currentPage: this.collegeMessagePage.current,
            showCount: this.collegeMessagePage.showCount,
            admin: this.selectForm.admin,
            group: this.selectForm.group,
            college: this.selectForm.college,
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.collegeMessagePage.totalCount = res.data.object.totalNum;
            this.collegeMessageTableData = res.data.object.items;
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
    handleCollegeMessageSizeChange(val) {
      this.collegeMessagePage.showCount = val;
      this.collegeMessagePage.current = 1;
      this.initCollegeMessage();
    },
    handleCollegeMessageCurrentChange(val) {
      this.collegeMessagePage.current = val;
      this.initCollegeMessage();
    },

    initGroupMessage() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/statistical/getGroupMessageBySearch", {
          params: {
            currentPage: this.groupMessagePage.current,
            showCount: this.groupMessagePage.showCount,
            admin: this.selectForm.admin,
            group: this.selectForm.group,
            college: this.selectForm.college,
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.groupMessagePage.totalCount = res.data.object.totalNum;
            this.groupMessageTableData = res.data.object.items;
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
    handleGroupMessageSizeChange(val) {
      this.groupMessagePage.showCount = val;
      this.groupMessagePage.current = 1;
      this.initGroupMessage();
    },
    handleGroupMessageCurrentChange(val) {
      this.groupMessagePage.current = val;
      this.initGroupMessage();
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

    this.search();

  }


};
</script>

<style  lang="scss">
#statistical {
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
  .el-tabs--border-card {
    margin: 20px;
  }
}
</style>