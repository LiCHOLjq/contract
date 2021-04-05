<template>
  <div id="project">
    <div class="login-wrap">
      <el-row>
        <el-col :span="21">
          <el-button style="float:left;" type="primary" icon="el-icon-back" @click="back">返回</el-button>
          <div class="ms-login">
            <el-form :model="form" :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm">
              <div class="ms-title">东北大学招生宣传出行备案表</div>
              <el-row>
                <el-col :span="18">
                  <h1>1.出行计划名称</h1>
                  <el-form-item>
                    <el-input :disabled="!(isAdd||isEdit)" v-model="project.projectName" autocomplete="off"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <h1 style="padding-left:45px">2.招生组</h1>
                  <el-form-item>
                    <el-select :disabled="!isAdd" v-model="project.projectGroup" @change="checkdeGroup" placeholder="选择招生组">
                      <el-option v-for="item in groupSelectiveList" :key="item.groupId" :label="item.groupName" :value="item.groupId"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>

              <h1>3.出行人员</h1>
              <el-row>
                <el-col :span="5">
                  <el-select :disabled="!(isAdd||isEdit)" v-model="checkedAdmin" value-key="adminId" filterable placeholder="选择出行人员">
                    <el-option-group label="招生组人员">
                      <el-option v-for="item in groupAdminSelectiveList" :key="item.adminId" :label="item.adminAccount + ' ' + item.adminName" :value="item"></el-option>
                    </el-option-group>
                    <el-option-group label="专家教授">
                      <el-option v-for="item in professorAdminSelectiveList" :key="item.adminId" :label="item.adminAccount + ' ' + item.adminName" :value="item"></el-option>
                    </el-option-group>
                  </el-select>
                  <el-button :disabled="!(isAdd||isEdit)" icon="el-icon-right" type="primary" @click="handleAddAdmin()" circle style="margin-top:10px;"></el-button>
                </el-col>
                <el-col :span="19">
                  <el-table v-bind:data="projectAdminList" border style="width: 100%">
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
              <h1>4.行程安排</h1>
              <el-button :disabled="!(isAdd||isEdit)" icon="el-icon-plus" type="primary" circle @click="handleAddTrave()" style="margin-top:10px;"></el-button>
              <el-row class="auto-table" v-if="projectTravelList.length > 0">
                <el-col :span="5">
                  <p>日期</p>
                </el-col>
                <el-col :span="5">
                  <p>途经城市</p>
                </el-col>
                <el-col :span="12">
                  <p>工作内容</p>
                </el-col>
                <el-col :span="2">
                  <p></p>
                </el-col>
              </el-row>
              <el-row v-for="(item,index) in projectTravelList" :key="index" class="auto-table">
                <el-col :span="5">
                  <el-form-item>
                    <el-date-picker :disabled="!(isAdd||isEdit)" v-model="item.travelDateStr" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item>
                    <el-select :disabled="!(isAdd||isEdit)" v-model="item.travelCity" filterable placeholder="选择城市">
                      <el-option-group v-for="city in citySelectiveList" :key="city.dictionaryId" :label="city.dictionaryName">
                        <el-option v-for="children in city.childrenList" :key="children.dictionaryId" :label="children.dictionaryName" :value="children.dictionaryId"></el-option>
                      </el-option-group>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item>
                    <el-input :disabled="!(isAdd||isEdit)" v-model="item.travelText" autocomplete="off" :placeholder="index == 0 ? '请填写去程信息，例如：沈阳至北京' : index==projectTravelList.length-1 ? '请填写返程信息，例如：北京至沈阳' : '填写工作内容'"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="2">
                  <el-form-item>
                    <el-button v-if="index!=0&&index!=projectTravelList.length-1" :disabled="!(isAdd||isEdit)" type="danger" icon="el-icon-delete" circle style="margin:auto;" @click="handleDeleteTrave(index)"></el-button>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row class="auto-table">
                <el-col :span="7">
                  <h1>5.行程预算</h1>
                  <el-form-item label-width="120px" v-for="(item,index) in projectBudgetList" :key="index" :label="item.budgetNameObj.dictionaryName+':'">
                    <el-input-number v-if="item.budgetName != 'budget_name_other'" :disabled="!(isAdd||isEdit)" v-model="item.budgetValue" :precision="0" :min="0" :step="100"></el-input-number>
                    <el-input v-if="item.budgetName == 'budget_name_other'" :disabled="!(isAdd||isEdit)" v-model="item.budgetValue" autocomplete="off" style="width:180px"></el-input>
                  </el-form-item>
                  <!-- <div style="text-align:left;font-size: 14px;margin-top: 20px;">
                    <div style="float:left;margin-right:90px;">合计:</div>
                    <div style="text-align:center;">{{sumBudget()}}</div>
                  </div> -->
                </el-col>
                <el-col :span="17">
                  <h1>6.其他补充说明</h1>
                  <el-form-item>
                    <el-input :disabled="!(isAdd||isEdit)" type="textarea" :rows="5" v-model="project.projectDescription" autocomplete="off"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row class="auto-table">
                <p v-if="!isAdd">
                  创建时间:{{project.projectMakeDateStr}}&nbsp;&nbsp;&nbsp;&nbsp;
                  最后修改时间:{{project.projectAlterDateStr}}&nbsp;&nbsp;&nbsp;&nbsp;
                  申请人:{{project.projectAdminObj == null ? '' : project.projectAdminObj.adminName == null ? '' : project.projectAdminObj.adminName}}&nbsp;&nbsp;&nbsp;&nbsp;
                  审核状态:{{project.projectStateObj == null ? '' : project.projectStateObj.dictionaryName == null ? '' : project.projectStateObj.dictionaryName}}&nbsp;&nbsp;&nbsp;&nbsp;
                </p>
              </el-row>
              <el-row class="auto-table">
                <el-form-item>
                  <el-button v-if="!isAdd&&!isEdit&&project.canEdit" icon="el-icon-edit" @click=" handleUpdProject " type="primary">编辑信息</el-button>

                  <el-button v-if="isAdd" icon="el-icon-upload2" type="primary" @click="alterProject">创建备案表</el-button>
                  <el-button v-if="!isAdd&&isEdit" icon="el-icon-upload2" @click="alterProject" type="primary">提交修改</el-button>
                  <el-button v-if="!isAdd&&isEdit" icon="el-icon-refresh-left" @click=" handleCancelUpdProject ">取消编辑</el-button>
                  <el-button v-if="!isAdd&&!isEdit&&project.projectState == 'project_state_1'" icon="el-icon-receiving" @click="checkedProject" type="primary">提交审核</el-button>
                  <el-button v-if="!isAdd&&!isEdit&&project.canDelete" icon="el-icon-delete" @click="handelDelProject" type="danger">删除申请</el-button>
                  <el-button v-if="!isAdd&&!isEdit&&project.canCheck&&project.projectState != 'project_state_6'&&project.projectState != 'project_state_7'" icon="el-icon-s-claim" @click="checkedProject" type="success">审核通过</el-button>
                  <!-- <el-button v-if="!isAdd&&!isEdit&&project.canCheck" icon="el-icon-s-release" @click="unCheckedProject" type="danger">审核不通过</el-button> -->
                  <el-button v-if="!isAdd&&!isEdit&&project.canCheck" icon="el-icon-s-release" @click="returnProject" type="warning">审核退回</el-button>

                  <el-button v-if="!isAdd&&!isEdit&&project.canDownloadApproval" icon="el-icon-download" @click="exportProjectApprovalWord" type="primary">下载出差审批表</el-button>
                  <el-button v-if="!isAdd&&!isEdit&&project.canDownloadRecord" icon="el-icon-download" @click="exportProjectRecordWord" type="primary">下载出行备案表</el-button>
                  <!-- <el-button v-if="!isAdd&&!isEdit&&project.projectState == 'project_state_6'" icon="el-icon-finished" @click="checkedProject" type="primary">结束任务</el-button> -->
                </el-form-item>
              </el-row>
              <!-- <div class="login-btn">
            <el-button type="primary" @click="submitForm()">登录</el-button>
          </div>
          <el-button type="text" style="padding:0px;margin-bottom:10px;text-align: right;" @click="signForm.visible = true;getCode();">注册新账号</el-button> -->
            </el-form>

          </div>
        </el-col>
        <el-col :span="3">
          <h1>审核状态</h1>
          <el-timeline>
            <el-timeline-item v-for="(timeline, index) in timeLineList" :key="index" :icon="timeline.icon" :type="timeline.type" size="large" :timestamp="timeline.time">
              <p>{{timeline.text}}</p>
              <p>{{timeline.admin}}</p>
            </el-timeline-item>
          </el-timeline>
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
      isAdd: false,
      isEdit: false,
      project: {
        projectGroup: "",
        projectName: "",
        projectDescription: "",
        projectState: "project_state_1",
        canCheck: false,
        canEdit: false,
        submitState: ''
      },
      checkedAdmin: null,
      projectAdminList: [],

      groupAdminSelectiveList: [],
      professorAdminSelectiveList: [],
      groupSelectiveList: [],

      projectTravelList: [{
        travelCity: "",
        travelText: "",
        travelDateStr: "",

      }, {
        travelCity: "",
        travelText: "",
        travelDateStr: "",

      }],

      projectBudgetList: [{
        budgetName: "",
        budgetValue: 0,
        budgetNameObj: {
          dictionaryName: "XX费:"
        }
      }],
      citySelectiveList: [],
      timeLineList: []
    };
  },
  components: {
  },
  methods: {
    back() {
      this.$router.push({ path: "/home/project/list" });
    },
    sumBudget() {
      var sum = 0.0;
      for (var i = 0; i < this.projectBudgetList.length; i++) {
        sum = sum + this.projectBudgetList[i].budgetValue;
      }
      return sum.toFixed(2);
    },
    initGroupList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/group/getAllGroupByToken",
          {
            params: {

            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.groupSelectiveList = res.data.object;
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
    checkdeGroup() {
      this.initAdminSelectiveList();
      this.checkedAdmin = null;
      this.projectAdminList = [];
    },
    initAdminSelectiveList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/admin/getProjectAdminCheckedList",
          {
            params: {
              groupId: this.project.projectGroup
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.groupAdminSelectiveList = res.data.object.groupAdminSelectiveList;
            this.professorAdminSelectiveList = res.data.object.professorAdminSelectiveList;

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

    initProjectBudgetList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItems", {
          params: {
            dictionaryType: "BUDGET_NAME"
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.projectBudgetList = [];
            var budgetDic = res.data.object
            budgetDic.forEach(element => {
              this.projectBudgetList.push({
                budgetName: element.dictionaryId,
                budgetValue: 0,
                budgetNameObj: element
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


    handleAddAdmin() {
      var isExit = false;
      for (var i = 0; i < this.projectAdminList.length; i++) {
        if (this.projectAdminList[i].adminId == this.checkedAdmin.adminId) {
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
        this.projectAdminList.push({
          adminId: this.checkedAdmin.adminId,
          adminObj: this.checkedAdmin
        })
      }

    },
    handleDeleteAdmin(index, row) {
      this.projectAdminList.splice(index, 1)
    },
    handleAddTrave() {
      this.projectTravelList.splice(this.projectTravelList.length - 2, 0, {
        travelCity: "",
        travelText: "",
        travelDateStr: "",
      })


      // this.projectTravelList.push({
      //   travelCity: "",
      //   travelText: "",
      //   travelDateStr: "",
      // })
    },

    handleDeleteTrave(index) {
      this.projectTravelList.splice(index, 1)
    },

    handleUpdProject() {
      this.isEdit = true;
      this.initAdminSelectiveList();
      this.project.submitState = 'Upd'
    },
    handleCancelUpdProject() {
      this.init(this.project.projectId)
    },

    handelDelProject() {
      this.$confirm('此操作将永久删除该招生组所有相关记录，包括已添加的任务和讲座报告，您确定删除此备案吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.project.submitState = 'Del'
        this.alterProject();
      }).catch(() => {

      });


    },

    alterProject() {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/project/alterProject", {
        params: {
          project: this.project,
          projectAdminList: this.projectAdminList,
          projectTravelList: this.projectTravelList,
          projectBudgetList: this.projectBudgetList,
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.$message({
            showClose: true,
            message: res.data.data,
            type: "success"
          });
          if (this.project.submitState == "Add" || this.project.submitState == "Upd") {

            this.init(res.data.object)
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
    init(projectId) {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/project/getProjectDetails",
          {
            params: {
              projectId: projectId
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.project = res.data.object.project;
            this.projectAdminList = res.data.object.projectAdminList;
            this.projectTravelList = res.data.object.projectTravelList;
            this.projectBudgetList = res.data.object.projectBudgetList;
            this.timeLineList = res.data.object.timeLineList;
            this.isAdd = false;
            this.isEdit = false;
            this.checkedAdmin = null;
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
    checkedProject() {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/project/alterProjectNextState", {
        params: {
          project: this.project,
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.$message({
            showClose: true,
            message: res.data.data,
            type: "success"
          });
          this.init(this.project.projectId)
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
    unCheckedProject() {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/project/alterProjectBanState", {
        params: {
          project: this.project,
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.$message({
            showClose: true,
            message: res.data.data,
            type: "success"
          });
          this.init(this.project.projectId)
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
    returnProject() {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/project/returnProject", {
        params: {
          project: this.project,
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.$message({
            showClose: true,
            message: res.data.data,
            type: "success"
          });
          this.init(this.project.projectId)
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
    exportProjectApprovalWord() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      let url = '/project/exportProjectApprovalWord?projectId=' + this.project.projectId;
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
        link.setAttribute("download", '东北大学因公出差审批表.docx');
        document.body.appendChild(link);
        link.click();
        loading.close();
      });
    },
    exportProjectRecordWord() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      let url = '/project/exportProjectRecordWord?projectId=' + this.project.projectId;
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
        link.setAttribute("download", '东北大学招生宣传出行备案表.docx');
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

    this.initGroupList();
    this.initCitySelectiveList();
    if (this.$route.params.projectId == null) {
      this.isAdd = true;

      this.initProjectBudgetList();
      this.project.submitState = 'Add'
    } else {
      this.init(this.$route.params.projectId)
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
}
</style>