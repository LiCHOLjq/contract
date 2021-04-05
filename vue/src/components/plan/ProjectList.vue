<template>
  <div id="projectList">
    <el-form :inline="true" :model="projectSelectForm" class="demo-form-inline">
      <el-form-item label="计划名称: ">
        <el-input v-model="projectSelectForm.projectName" autocomplete="off" style="width:120px"></el-input>
      </el-form-item>
      <el-form-item label="填报人工号/姓名: ">
        <el-input v-model="projectSelectForm.projectAdmin" autocomplete="off" style="width:120px"></el-input>
      </el-form-item>
      <el-form-item label="招生组：">
        <el-select v-model="projectSelectForm.projectGroup" placeholder="选择招生组" style="width: 120px;">
          <el-option label="全部" value=""></el-option>
          <el-option v-for="item in groupSelectiveList" :key="item.groupId" :label="item.groupName" :value="item.groupId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态：">
        <el-select v-model="projectSelectForm.projectState" placeholder="选择审核状态" style="width: 200px;">
          <el-option label="全部" value=""></el-option>
          <el-option v-for="item in projectStateSelectiveList" :key="item.dictionaryId" :label="item.dictionaryName" :value="item.dictionaryId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="projectPage.current=1;
            initProjectList();">
          查询
        </el-button>

        <!-- <el-button type="success" @click="excelForm.visible = true">Excel</el-button> -->
      </el-form-item>
    </el-form>
    <el-row>
      <el-col class="main-col" :span="24">
        <el-button size="mini" icon="el-icon-plus" type="primary" @click="handleAddProject()">
          提交新备案
        </el-button>
        <el-table v-bind:data="projectTableData" border style="width: 100%">
          <el-table-column label="名称">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.projectName == null ? '' : scope.row.projectName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="招生组" width="140">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.projectGroupObj == null ? '' : scope.row.projectGroupObj.groupName == null ? '' : scope.row.projectGroupObj.groupName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="申请人" width="240">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.projectAdminObj == null ? '' : scope.row.projectAdminObj.adminName == null ? '' : scope.row.projectAdminObj.adminName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="参与人员" width="120">
            <template slot-scope="scope">
              <el-popover trigger="hover">
                <p v-for="item in scope.row.projectAdminList" :key="item.adminId">{{ item.adminName == null ? '' : item.adminName }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ scope.row.projectAdminList == null ? '' : scope.row.projectAdminList.length }}人</el-tag>
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="审核状态" width="120">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.projectStateObj == null ? '' : scope.row.projectStateObj.dictionaryName == null ? '' : scope.row.projectStateObj.dictionaryName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" width="180">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.projectMakeDateStr == null ? '' : scope.row.projectMakeDateStr }}</span>
            </template>
          </el-table-column>
          <el-table-column label="最后修改时间" width="180">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.projectAlterDateStr == null ? '' : scope.row.projectAlterDateStr }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="360">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" @click="handleDetails(scope.$index, scope.row)">详情</el-button>
              <!-- <el-button size="mini" type="danger" @click="handleDelProject(scope.$index, scope.row)">撤销申请</el-button> -->
              <el-button :disabled="scope.row.projectState != 'project_state_6'&&scope.row.projectState != 'project_state_7'" size="mini" type="primary" @click="handleInitTask(scope.$index, scope.row)">任务填报</el-button>
              <el-button :disabled="scope.row.projectState != 'project_state_6' && scope.row.projectState != 'project_state_7'" size="mini" type="primary" @click="handleInitNews(scope.$index, scope.row)">新闻撰写</el-button>
              <el-button v-if="scope.row.canOver" size="mini" @click="checkedProject(scope.$index, scope.row)" type="primary">结束任务</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
          <el-pagination background @size-change="handleProjectTableSizeChange" @current-change="handleProjectTableCurrentChange" :hide-on-single-page="false" :current-page="projectPage.current" :page-sizes="[5, 10, 20, 30, 50, 100]" :page-size="projectPage.showCount" layout="total, sizes, prev, pager, next, jumper" :total="projectPage.totalCount"></el-pagination>
        </div>
      </el-col>
    </el-row>
    <!-- ====================================分配权限============================================ -->
    <el-dialog title="任务填报" :visible.sync="projectTaskForm.visible" width="70%">
      <el-button :disabled="this.projectChecked.projectState != 'project_state_6'" size="mini" icon="el-icon-plus" type="primary" @click="handleAddProjectTask()">
        填报新任务
      </el-button>
      <el-table v-bind:data="projectTaskTableData" border style="width: 100%">
        <el-table-column label="填报人">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.taskAdminObj == null ? '' : scope.row.taskAdminObj.adminName == null ? '' : scope.row.taskAdminObj.adminName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="参与人员" width="120">
          <template slot-scope="scope">
            <el-popover trigger="hover">
              <p v-for="item in scope.row.taskAdminList" :key="item.adminId">{{ item.adminName == null ? '' : item.adminName }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.taskAdminList == null ? '无' : (scope.row.taskAdminList.length + '人' )}}</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="任务" width="120">
          <template slot-scope="scope">
            <el-popover trigger="hover">
              <p v-for="item in scope.row.taskItemList" :key="item.itemId">{{ item.itemName == null ? '' : item.itemName }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.taskItemList == null ? '无' : (scope.row.taskItemList.length + '个') }}</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="报告" width="120">
          <template slot-scope="scope">
            <el-popover trigger="hover">
              <p v-for="item in scope.row.taskLectureList" :key="item.lectureId">{{ item.lectureName == null ? '' : item.lectureName }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.taskLectureList == null ? '无' : (scope.row.taskLectureList.length + '个') }}</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column label="创建时间" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.taskMakeDateStr == null ? '' : scope.row.taskMakeDateStr }}</span>
          </template>
        </el-table-column>
        <el-table-column label="最后修改时间" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.taskAlterDateStr == null ? '' : scope.row.taskAlterDateStr }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleUpdProjectTask(scope.$index, scope.row)">详情</el-button>
            <!-- <el-button size="mini" type="danger" @click="handleDelProjectTask(scope.$index, scope.row)">删除</el-button> -->
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog title="新闻列表" :visible.sync="projectNewsForm.visible" width="70%">
      <el-button size="mini" icon="el-icon-plus" type="primary" @click="handleAddProjectNews()">
        撰写新新闻
      </el-button>
      <el-table v-bind:data="projectNewsTableData" border style="width: 100%">
        <el-table-column label="标题">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.newsTitle == null ? '' : scope.row.newsTitle}}</span>
          </template>
        </el-table-column>
        <el-table-column label="发布人">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.newsAdminObj == null ? '' : scope.row.newsAdminObj.adminName == null ? '' : scope.row.newsAdminObj.adminName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.newsStateObj == null ? '' : scope.row.newsStateObj.dictionaryName == null ? '' : scope.row.newsStateObj.dictionaryName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="发布时间" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.newsDateStr == null ? '' : scope.row.newsDateStr }}</span>
          </template>
        </el-table-column>
        <el-table-column label="浏览次数" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.newsViews == null ? '' : scope.row.newsViews }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleUpdProjectNews(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" @click="handleSeeNews(scope.$index, scope.row)">显示新闻</el-button>
            <!-- <el-button size="mini" type="danger" @click="handleDelProjectTask(scope.$index, scope.row)">删除</el-button> -->
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data() {
    return {
      token: "",
      // role collage form 选择框
      projectSelectForm: {
        projectName: "",
        projectAdmin: "",
        projectGroup: "",
        projectState: "",
      },

      groupSelectiveList: [],
      projectStateSelectiveList: [],
      projectTableData: [],

      projectPage: {
        showCount: 10,
        totalCount: 0,
        current: 1
      },

      projectTaskForm: {
        visible: false,
      },

      projectTaskTableData: [],
      projectChecked: {},
      projectNewsForm: {
        visible: false,
      },
      projectNewsTableData: []
    }
  },
  methods: {
    initProjectList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/project/getProjectBySearch",
          {
            params: {
              project: this.projectSelectForm,
              currentPage: this.projectPage.current,
              showCount: this.projectPage.showCount
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.projectPage.totalCount = res.data.object.totalNum;
            this.projectTableData = res.data.object.items;
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
    // role选择列表选择
    initProjectStateList() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/dictionary/getDictionaryItems", {
          params: {
            dictionaryType: "PROJECT_STATE"
          }
        }, { headers: { token: this.token } })
        .then(res => {
          if (res.data.code === 200) {
            this.projectStateSelectiveList = res.data.object;

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

    handleAddProject() {
      this.savePage()
      this.$router.push({ path: "/home/project/details" });
    },

    handleDetails(index, row) {
      this.savePage()
      this.$router.push({ path: "/home/project/details/" + row.projectId });
    },

    // handleDelProject(index, row) {

    // },

    handleProjectTableSizeChange(val) {
      this.projectPage.showCount = val;
      this.projectPage.current = 1;
      this.initProjectList();
    },
    handleProjectTableCurrentChange(val) {
      this.projectPage.current = val;
      this.initProjectList();
    },
    handleInitTask(index, row) {
      this.projectChecked = row;
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/task/getProjectTaskBySearch",
          {
            params: {
              project: row
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {

            this.projectTaskTableData = res.data.object;
            this.projectTaskForm.visible = true;
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

    handleAddProjectTask() {
      this.savePage()
      this.$router.push({ path: "/home/project/task/add/" + this.projectChecked.projectId });
    },

    handleUpdProjectTask(index, row) {
      this.savePage()
      this.$router.push({ path: "/home/project/task/" + row.taskId });
    },

    // handleDelProjectTask(index, row) {

    // },
    savePage() {
      localStorage.setItem("projectListPageMessage", JSON.stringify({
        projectSelectForm: this.projectSelectForm,
        projectPage: this.projectPage,
        projectChecked: this.projectChecked,
        projectTaskForm: this.projectTaskForm,
        projectNewsForm: this.projectNewsForm,
      }))
    },

    checkedProject(index, row) {
      const loading = this.$loading(this.$store.state.loadingOption2);
      this.axios.post("/project/alterProjectNextState", {
        params: {
          project: row,
        }
      }, { headers: { token: this.token } }).then(res => {
        if (res.data.code === 200) {
          this.$message({
            showClose: true,
            message: res.data.data,
            type: "success"
          });
          this.initProjectList();
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

    handleInitNews(index, row) {
      this.projectChecked = row;
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/news/getProjectNewsBySearch",
          {
            params: {
              project: row
            }
          },
          { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {

            this.projectNewsTableData = res.data.object;
            this.projectNewsForm.visible = true;
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
    handleAddProjectNews() {
      this.savePage()
      this.$router.push({ path: "/home/project/news/add/" + this.projectChecked.projectId });
    },

    handleUpdProjectNews(index, row) {
      this.savePage()
      this.$router.push({ path: "/home/project/news/" + row.newsId });
    },
    handleSeeNews(index, row) {
      this.savePage()
      this.$router.push({ path: "/news/" + row.newsId });
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
    var str = localStorage.getItem("projectListPageMessage")
    if (str != "" && str != null && str != "null") {
      var projectListPageMessage = JSON.parse(str)
      this.projectSelectForm = projectListPageMessage.projectSelectForm
      this.projectPage = projectListPageMessage.projectPage
      this.projectChecked = projectListPageMessage.projectChecked
      this.projectTaskForm = projectListPageMessage.projectTaskForm
      this.projectNewsForm = projectListPageMessage.projectNewsForm
      localStorage.setItem("projectListPageMessage", null)
    }
    // 加载学院列表数据
    this.initGroupList();
    // 加载admin table data
    this.initProjectStateList();

    this.initProjectList();

    if (this.projectTaskForm.visible) {
      this.handleInitTask(0, this.projectChecked)
    }
    if (this.projectNewsForm.visible) {
      this.handleInitNews(0, this.projectChecked)
    }


  }

}
</script>

<style  lang="scss">
#projectList {
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