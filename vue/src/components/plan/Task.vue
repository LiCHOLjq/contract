<template>
  <div id="task">
    <div class="login-wrap">
      <el-button style="float:left;" type="primary" icon="el-icon-back" @click=" back ">返回</el-button>
      <div class="ms-login">
        <el-form ref="ruleForm" label-width="0px" class="demo-ruleForm">
          <div class="ms-title">东北大学招生宣传工作信息统计</div>
          <el-row>
            <el-col :span="18">
              <h1>1.所属备案</h1>
              <p>名称:{{ task.taskProjectObj == null ? "" : task.taskProjectObj.projectName}}&nbsp;&nbsp;&nbsp;
                招生组:{{task.taskProjectObj == null ? "" : task.taskProjectObj.projectGroupObj.groupName}}&nbsp;&nbsp;&nbsp;
                申请人:{{task.taskProjectObj == null ? "" : task.taskProjectObj.projectAdminObj.adminName}}</p>
            </el-col>
          </el-row>
          <h1>2.参与人员</h1>
          <el-row>
            <el-col :span="5">
              <el-select :disabled="!(isAdd||isEdit)" v-model="checkedAdmin" value-key="adminId" filterable placeholder="选择参与人员">
                <el-option-group label="已备案招生组人员">
                  <el-option v-for="item in groupAdminSelectiveList" :key="item.adminId" :label="item.adminAccount + ' ' + item.adminName" :value="item"></el-option>
                </el-option-group>
                <el-option-group label="已备案专家教授">
                  <el-option v-for="item in professorAdminSelectiveList" :key="item.adminId" :label="item.adminAccount + ' ' + item.adminName" :value="item"></el-option>
                </el-option-group>
              </el-select>
              <el-button :disabled="!(isAdd||isEdit)" icon="el-icon-right" type="primary" @click="handleAddAdmin()" circle style="margin-top:10px;"></el-button>
            </el-col>
            <el-col :span="19">
              <el-table v-bind:data="projectTaskAdminList" border style="width: 100%">
                <el-table-column label="工号" width="140">
                  <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.adminObj == null ? '' : scope.row.adminObj.adminAccount == null ? '' : scope.row.adminObj.adminAccount }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="姓名">
                  <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.adminObj == null ? '' : scope.row.adminObj.adminName == null ? '' : scope.row.adminObj.adminName }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="电话" width="140">
                  <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.adminObj == null ? '' : scope.row.adminObj.adminPhone == null ? '' : scope.row.adminObj.adminPhone }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="学院/部门" width="240">
                  <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.adminObj == null ? '' : scope.row.adminObj.adminCollegeObj == null ? '' : scope.row.adminObj.adminCollegeObj.dictionaryName == null ? '' : scope.row.adminObj.adminCollegeObj.dictionaryName }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="职称/职位" width="120">
                  <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.adminObj == null ? '' : scope.row.adminObj.adminPosition == null ? '' : scope.row.adminObj.adminPosition }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="120">
                  <template slot-scope="scope">
                    <el-button :disabled="!(isAdd||isEdit) && checkedAdmin == null" size="mini" icon="el-icon-delete" circle type="danger" @click="handleDeleteAdmin(scope.$index, scope.row)"></el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>

          <h1>3.行程信息</h1>
          <el-button :disabled="!(isAdd||isEdit)" icon="el-icon-plus" type="primary" circle @click="handleAddTrave()" style="margin-top:10px;"></el-button>
          <el-row class="auto-table" v-if="taskTravelList.length > 0">
            <el-col :span="5">
              <p>出发日期</p>
            </el-col>
            <el-col :span="4">
              <p>出发城市</p>
            </el-col>
            <el-col :span="4">
              <p>目的地城市</p>
            </el-col>
            <el-col :span="5">
              <p>抵达日期</p>
            </el-col>
            <el-col :span="4">
              <p>交通费金额</p>
            </el-col>
            <el-col :span="2">
              <p></p>
            </el-col>
          </el-row>
          <el-row v-for="(item,index) in taskTravelList" :key="index" class="auto-table">
            <el-col :span="5">
              <el-form-item>
                <el-date-picker :disabled="!(isAdd||isEdit)" v-model="item.travelDepartureDateStr" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item>
                <el-select :disabled="!(isAdd||isEdit)" v-model="item.travelBeginCity" filterable placeholder="选择城市">
                  <el-option-group v-for="city in citySelectiveList" :key="city.dictionaryId" :label="city.dictionaryName">
                    <el-option v-for="children in city.childrenList" :key="children.dictionaryId" :label="children.dictionaryName" :value="children.dictionaryId"></el-option>
                  </el-option-group>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item>
                <el-select :disabled="!(isAdd||isEdit)" v-model="item.travelEndCity" filterable placeholder="选择城市">
                  <el-option-group v-for="city in citySelectiveList" :key="city.dictionaryId" :label="city.dictionaryName">
                    <el-option v-for="children in city.childrenList" :key="children.dictionaryId" :label="children.dictionaryName" :value="children.dictionaryId"></el-option>
                  </el-option-group>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-date-picker :disabled="!(isAdd||isEdit)" v-model="item.travelReturnDateStr" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item>
                <el-input-number :disabled="!(isAdd||isEdit)" v-model="item.travelCost" :precision="2" :min="0" :step="100"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="2">
              <el-form-item>
                <el-button :disabled="!(isAdd||isEdit)" type="danger" icon="el-icon-delete" circle style="margin:auto;" @click="handleDeleteTrave(index)"></el-button>
              </el-form-item>
            </el-col>
          </el-row>

          <h1>4.费用明细</h1>
          <el-row class="auto-table">
            <el-col :span="7">

              <el-form-item label-width="120px" v-for="(item,index) in taskSpendList" :key="index" :label="item.costNameObj.dictionaryName+':'">
                <el-input-number :disabled="!(isAdd||isEdit)" v-model="item.costValue" :precision="2" :min="0" :step="100"></el-input-number>
              </el-form-item>
              <div style="text-align:left;font-size: 14px;margin-top: 20px;">
                <div style="float:left;margin-right:90px;">合计:</div>
                <div style="text-align:center;">{{sumBudget()}}</div>
              </div>
            </el-col>
          </el-row>

          <h1>5.出差任务</h1>
          <el-button :disabled="!(isAdd||isEdit)" icon="el-icon-plus" type="primary" circle @click="handleAddItem()" style="margin-top:10px;"></el-button>
          <el-row class="auto-table" v-if="taskItemList.length > 0">
            <el-col :span="5">
              <p>任务类型</p>
            </el-col>
            <el-col :span="5">
              <p>日期</p>
            </el-col>
            <el-col :span="5">
              <p>地点</p>
            </el-col>
            <el-col :span="7">
              <p>中学/咨询会名称</p>
            </el-col>
            <el-col :span="2">
              <p></p>
            </el-col>
          </el-row>
          <el-row v-for="(item,index) in taskItemList" :key="index" class="auto-table">
            <el-col :span="5">
              <el-form-item>
                <el-select :disabled="!(isAdd||isEdit)" v-model="item.itemType" filterable placeholder="选择任务类型">
                  <el-option v-for="item in itemTypeSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-date-picker :disabled="!(isAdd||isEdit)" v-model="item.itemDateStr" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-select :disabled="!(isAdd||isEdit)" v-model="item.itemCity" filterable placeholder="选择城市">
                  <el-option-group v-for="city in citySelectiveList" :key="city.dictionaryId" :label="city.dictionaryName">
                    <el-option v-for="children in city.childrenList" :key="children.dictionaryId" :label="children.dictionaryName" :value="children.dictionaryId"></el-option>
                  </el-option-group>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item>
                <el-input :disabled="!(isAdd||isEdit)" v-model="item.itemName" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="2">
              <el-form-item>
                <el-button :disabled="!(isAdd||isEdit)" type="danger" icon="el-icon-delete" circle style="margin:auto;" @click="handleDeleteItem(index)"></el-button>
              </el-form-item>
            </el-col>
          </el-row>
          <h1>6.讲座报告统计</h1>
          <el-button :disabled="!(isAdd||isEdit)" icon="el-icon-plus" type="primary" circle @click="handleAddLecture()" style="margin-top:10px;"></el-button>
          <el-row class="auto-table" v-if="taskLectureList.length > 0">
            <el-col :span="4">
              <p>讲座报告题目</p>
            </el-col>
            <el-col :span="4">
              <p>中学名称</p>
            </el-col>
            <el-col :span="3">
              <p>报告类型</p>
            </el-col>
            <el-col :span="3">
              <p>报告人</p>
            </el-col>
            <el-col :span="2">
              <p>受众群体</p>
            </el-col>
            <el-col :span="3">
              <p>受众人数</p>
            </el-col>
            <el-col :span="3">
              <p>活动时长(小时)</p>
            </el-col>
            <el-col :span="2">
              <p></p>
            </el-col>
          </el-row>
          <el-row v-for="(item,index) in taskLectureList" :key="index" class="auto-table">
            <el-col :span="4">
              <el-form-item>
                <el-input :disabled="!(isAdd||isEdit)" v-model="item.lectureName" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item>
                <el-input :disabled="!(isAdd||isEdit)" v-model="item.lectureSchool" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="3">
              <el-form-item>
                <el-select :disabled="!(isAdd||isEdit)" v-model="item.lectureType" filterable placeholder="选择报告类型">
                  <el-option v-for="item in lectureTypeSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="3">
              <el-form-item>
                <el-select :disabled="!(isAdd||isEdit)" v-model="item.lectureAdmin" filterable placeholder="选择报告人">
                  <el-option-group label="已备案专家教授">
                    <el-option v-for="item in professorAdminSelectiveCheckList" :key="item.adminId" :label="item.adminAccount + ' ' + item.adminName" :value="item.adminId"></el-option>
                  </el-option-group>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="2">
              <el-form-item>
                <el-input :disabled="!(isAdd||isEdit)" v-model="item.lectureAudienceType" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="3">
              <el-form-item>
                <el-input-number style="width: 150px;" :disabled="!(isAdd||isEdit)" v-model="item.lectureAudienceNumber" :precision="0" :min="0" :step="10"></el-input-number>

              </el-form-item>
            </el-col>
            <el-col :span="3">
              <el-form-item>
                <el-input-number style="width: 150px;" :disabled="!(isAdd||isEdit)" v-model="item.lectureDuration" :precision="1" :min="0" :step="1"></el-input-number>
              </el-form-item>
            </el-col>

            <el-col :span="2">
              <el-form-item>
                <el-button :disabled="!(isAdd||isEdit)" type="danger" icon="el-icon-delete" circle style="margin:auto;" @click="handleDeleteLecture(index)"></el-button>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row class="auto-table">
            <p v-if="!isAdd">
              创建时间:{{task.taskMakeDateStr}}&nbsp;&nbsp;&nbsp;&nbsp;
              最后修改时间:{{task.taskAlterDateStr}}&nbsp;&nbsp;&nbsp;&nbsp;
              填报人:{{task.taskAdminObj == null ? '' : task.taskAdminObj.adminName == null ? '' : task.taskAdminObj.adminName}}&nbsp;&nbsp;&nbsp;&nbsp;
            </p>
          </el-row>
          <el-row class="auto-table">
            <el-form-item>
              <el-button v-if="!isAdd&&!isEdit&&task.canEdit" icon="el-icon-edit" @click=" handleUpdTask " type="primary">编辑信息</el-button>
              <el-button v-if="!isAdd&&!isEdit&&task.canEdit" icon="el-icon-receiving" @click=" handleDelTask " type="danger">删除信息</el-button>
              <el-button v-if="isAdd" icon="el-icon-upload2" type="primary" @click="alterTask">添加任务</el-button>
              <el-button v-if="!isAdd&&isEdit" icon="el-icon-upload2" @click="alterTask" type="primary">提交修改</el-button>
              <el-button v-if="!isAdd&&isEdit" icon="el-icon-refresh-left" @click="handleCancelUpdTask" type="primary">取消编辑</el-button>
              <el-button v-if="!isAdd&&!isEdit" icon="el-icon-download" @click="exportTaskExpenseAccount " type="primary">下载差旅费报销单</el-button>
            </el-form-item>
          </el-row>

        </el-form>

      </div>
    </div>
  </div>
</template>

<script>
//import store from "../vuex/store.js";
export default {
  //store,
  data: function () {
    return {
      isAdd: false,
      isEdit: false,
      task: {
        taskProject: "",
        taskProjectObj: {
          projectId: "",
          projectName: "",
        }
      },
      checkedAdmin: null,
      projectTaskAdminList: [],
      groupAdminSelectiveList: [],
      professorAdminSelectiveList: [],

      professorAdminSelectiveCheckList: [],

      taskTravelList: [],
      citySelectiveList: [],
      taskSpendList: [],
      taskItemList: [],
      taskLectureList: [],
      itemTypeSelectiveList: [],
      lectureTypeSelectiveList: [],


    };
  },
  components: {
  },
  methods: {
    back() {
      this.$router.push({ path: "/home/project/list" });
    },
    // initAdminSelectiveList() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post(
    //       "/admin/getTaskAdminCheckedList",
    //       {
    //         params: {
    //           projectId: this.task.taskProject
    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.adminSelectiveList = res.data.object.adminSelectiveList;
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
    handleAddAdmin() {
      var isExit = false;
      for (var i = 0; i < this.projectTaskAdminList.length; i++) {
        if (this.projectTaskAdminList[i].adminId == this.checkedAdmin.adminId) {
          isExit = true;
        }
      }
      if (isExit) {
        this.$message({
          showClose: true,
          message: "已存在",
          type: "error"
        });
      } else {
        this.projectTaskAdminList.push({
          adminId: this.checkedAdmin.adminId,
          adminObj: this.checkedAdmin
        })
      }
      this.altherProfessorAdminSelectiveCheckList();
    },
    handleDeleteAdmin(index, row) {
      if (this.adminCanDel(row)) {
        this.projectTaskAdminList.splice(index, 1)
        this.altherProfessorAdminSelectiveCheckList();
      } else {
        this.$message({
          showClose: true,
          message: "请先删除此人报告的讲座",
          type: "error"
        });
      }

    },
    altherProfessorAdminSelectiveCheckList() {
      this.professorAdminSelectiveCheckList = [];
      for (var i = 0; i < this.professorAdminSelectiveList.length; i++) {
        for (var j = 0; j < this.projectTaskAdminList.length; j++) {
          if (this.professorAdminSelectiveList[i].adminId == this.projectTaskAdminList[j].adminId) {
            this.professorAdminSelectiveCheckList.push(this.professorAdminSelectiveList[i]);
            break;
          }
        }
      }

    },


    handleAddTrave() {
      this.taskTravelList.push({
        travelBeginCity: "",
        travelEndCity: "",
        travelCost: "",
        travelDepartureDateStr: "",
        travelReturnDateStr: "",
      })
    },

    handleDeleteTrave(index) {
      this.taskTravelList.splice(index, 1)
    },
    initCitySelectiveList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItemsTree", {
          params: {
            dictionaryType: "CITY"
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.citySelectiveList = res.data.object;
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
    sumBudget() {
      var sum = 0.0;
      for (var i = 0; i < this.taskSpendList.length; i++) {
        sum = sum + this.taskSpendList[i].costValue;
      }
      return sum.toFixed(2);
    },
    initTaskSpendList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItems", {
          params: {
            dictionaryType: "COST_NAME"
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.taskSpendList = [];
            var budgetDic = res.data.object
            budgetDic.forEach(element => {
              this.taskSpendList.push({
                costName: element.dictionaryId,
                costValue: 0,
                costNameObj: element
              })
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
    handleAddItem() {
      this.taskItemList.push({
        itemName: "",
        itemType: "",
        itemCity: "",
        itemDateStr: "",
      })
    },
    handleDeleteItem(index) {
      this.taskItemList.splice(index, 1)
    },
    initItemTypeSelectiveList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItems", {
          params: {
            dictionaryType: "ITEM_TYPE"
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.itemTypeSelectiveList = res.data.object;
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

    initLectureTypeSelectiveList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItems", {
          params: {
            dictionaryType: "LECTURE_TYPE"
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.lectureTypeSelectiveList = res.data.object;
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
    handleAddLecture() {
      this.taskLectureList.push({
        lectureName: "",
        lectureType: "",
        lectureSchool: "",
        lectureAdmin: "",
        lectureAudienceType: "",
        lectureAudienceNumber: 0,
        lectureDuration: 0,
      })
    },
    handleDeleteLecture(index) {
      this.taskLectureList.splice(index, 1)
    },
    handleUpdTask() {
      this.isEdit = true;
      this.task.submitState = 'Upd'
    },
    handleDelTask() {
      this.$confirm('您确定删除此条任务记录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.task.submitState = 'Del'
        this.alterTask();
      }).catch(() => {

      });


    },

    alterTask() {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/task/alterTask", {
        params: {
          task: this.task,
          projectTaskAdminList: this.projectTaskAdminList,
          taskTravelList: this.taskTravelList,
          taskSpendList: this.taskSpendList,
          taskItemList: this.taskItemList,
          taskLectureList: this.taskLectureList,
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.$message({
            showClose: true,
            message: res.data.data,
            type: "success"
          });
          if (this.task.submitState == "Add" || this.task.submitState == "Upd") {
            this.init(res.data.object)
            this.$router.push({ path: "/home/project/task/" + res.data.object });
          } else {
            this.$router.push({ path: "/home/project/list" });
          }

          //this.adminAddUpdForm.visible = false;
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
    },
    initProject() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/project/getProjectById",
          {
            params: {
              projectId: this.task.taskProject
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.task.taskProjectObj = res.data.object
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
    initAdminSelectiveList() {
      console.log(this.task.taskProject)
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/admin/getTaskAdminCheckedList",
          {
            params: {
              projectId: this.task.taskProject
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.groupAdminSelectiveList = res.data.object.groupAdminSelectiveList;
            this.professorAdminSelectiveList = res.data.object.professorAdminSelectiveList;
            this.altherProfessorAdminSelectiveCheckList();
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
    handleCancelUpdTask() {
      this.init(this.task.taskId)
    },
    init(taskId) {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/task/getTaskDetails",
          {
            params: {
              taskId: taskId
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.task = res.data.object.task;
            this.projectTaskAdminList = res.data.object.projectTaskAdminList;
            this.taskTravelList = res.data.object.taskTravelList;
            this.taskSpendList = res.data.object.taskSpendList;
            this.taskItemList = res.data.object.taskItemList;
            this.taskLectureList = res.data.object.taskLectureList;
            this.isAdd = false;
            this.isEdit = false;
            this.checkedAdmin = null;
            this.initAdminSelectiveList();
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
            this.$router.push({ path: "/home/project/list" });
          }
          loading.close();
        });
    },
    adminCanDel(admin) {
      console.log(admin);
      for (var i = 0; i < this.taskLectureList.length; i++) {
        if (this.taskLectureList[i].lectureAdmin == admin.adminId) {
          return false;
        }
      }
      return true;
    },











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









    // handleUpdProject() {
    //   this.isEdit = true;
    //   this.initAdminSelectiveList();
    //   this.project.submitState = 'Upd'
    // },
    // alterProject() {

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
    // exportProjectApprovalWord() {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   let url = '/project/exportProjectApprovalWord?projectId=' + this.project.projectId;
    //   this.axios({
    //     method: "get",
    //     url: url,
    //     responseType: "blob",
    //     headers: { token: this.token }
    //   }).then(data => {
    //     if (!data) {
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
    exportTaskExpenseAccount() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      let url = '/task/exportTaskExpenseAccount?taskId=' + this.task.taskId;
      this.axios({
        method: "get",
        url: url,
        responseType: "blob",
        headers: { token: this.token }
      }).then(data => {
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
        link.setAttribute("download", '东北大学差旅费报销单.docx');
        document.body.appendChild(link);
        link.click();
        loading.close();
      });
    }

  },
  created: function () {
    this.token = localStorage.getItem("token");
    if (this.token == "" || this.token == null) {
      this.$router.push({ path: "/login" });
      return;
    }

    this.initItemTypeSelectiveList();
    this.initLectureTypeSelectiveList();
    this.initCitySelectiveList();
    if (this.$route.params.taskId == null && this.$route.params.projectId != null) {
      this.isAdd = true;
      this.initTaskSpendList();
      this.task.taskProject = this.$route.params.projectId;
      this.initProject();
      this.task.submitState = 'Add'
      this.initAdminSelectiveList();
    } else if (this.$route.params.taskId != null && this.$route.params.projectId == null) {
      this.init(this.$route.params.taskId)
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
#task {
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
    width: 1300px;
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
  .el-input-number__decrease,
  .el-input-number__increase {
    display: none;
  }
  .el-input-number .el-input__inner {
    padding-left: 15px;
    padding-right: 15px;
    text-align: left;
  }
}
</style>