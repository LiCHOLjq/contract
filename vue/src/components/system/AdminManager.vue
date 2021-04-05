<template>
<div id="AdminManager">
    <el-form :inline="true" :model="boxSelectForm" class="demo-form-inline">
        <el-form-item label="角色">
          <el-select 
            v-model="boxSelectForm.roleId"
            placeholder="选择角色"
            style="width: 180px;">

            <el-option label="全部" value=""></el-option>
            <el-option
              v-for="item in roleSelectiveList"
              :key="item.dictionaryId"
              :label="item.dictionaryName"
              :value="item.dictionaryId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学院">
          <el-select 
            v-model="boxSelectForm.collageId"
            placeholder="选择学院"
            style="width: 280px;">

            <el-option label="全部" value=""></el-option>
            <el-option
              v-for="item in collageSelectiveList"
              :key="item.dictionaryId"
              :label="item.dictionaryName"
              :value="item.dictionaryId"
            ></el-option>
          </el-select>
          
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="AdminPage.current=1;
            initAdminTable();">
                查询
            </el-button>


          <el-button type="success" @click="excelForm.visible = true">Excel</el-button> 
        </el-form-item>
      </el-form>
    <el-row>
        <el-col class="main-col" :span="24">
            <el-button
                size="mini"
                icon="el-icon-plus"
                type="primary"
                @click="handleAddAdmin()">
                增加用户
            </el-button>
            <el-table v-bind:data="adminTableData" border style="width: 100%">
                <el-table-column label="用户名">
                    <template slot-scope="scope">
                        <span
                        style="margin-left: 10px"
                        >{{ scope.row.adminAccount == null ? '' : scope.row.adminAccount }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="用户真实姓名">
                    <template slot-scope="scope">
                        <span
                        style="margin-left: 10px"
                        >{{ scope.row.adminName == null ? '' : scope.row.adminName }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="用户电话">
                    <template slot-scope="scope">
                        <span
                        style="margin-left: 10px"
                        >{{ scope.row.adminPhone == null ? '' : scope.row.adminPhone }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="备注">
                    <template slot-scope="scope">
                        <span
                        style="margin-left: 10px"
                        >{{ scope.row.adminText == null ? '' : scope.row.adminText }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="状态">
                    <template slot-scope="scope">
                        <span
                            style="margin-left: 10px"
                            v-if="scope.row.adminUseful == 0">
                            未启用
                        </span>
                        <span
                            style="margin-left: 10px"
                            v-else-if="scope.row.adminUseful == 1">
                            正在填报
                        </span>
                        <span
                            style="margin-left: 10px"
                            v-else-if="scope.row.adminUseful == 2">
                            已冻结
                        </span>
                        <span
                            style="margin-left: 10px"
                            v-else>
                            管理员账号
                        </span>
                    </template>
                </el-table-column>
                <el-table-column label="启用时间">
                    <template slot-scope="scope">
                        <span
                        style="margin-left: 10px"
                        >{{ scope.row.adminTime1Str == null ? '' : scope.row.adminTime1Str }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="启用截止时间">
                    <template slot-scope="scope">
                        <span
                        style="margin-left: 10px"
                        >{{ scope.row.adminTime2Str == null ? '' : scope.row.adminTime2Str }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="上报截止时间">
                    <template slot-scope="scope">
                        <span
                        style="margin-left: 10px"
                        >{{ scope.row.adminTime3Str == null ? '' : scope.row.adminTime3Str }}</span>
                    </template>
                </el-table-column>


                <el-table-column label="操作" width="280">
                    <template slot-scope="scope">
                        <el-button size="mini" 
                            @click="handleUpdAdmin(scope.$index, scope.row)
                            ">
                            编辑
                        </el-button>
                        <el-button
                            size="mini"
                            type="danger"
                            @click="handleDeleteAdmin(scope.$index, scope.row)
                            ">
                            删除
                        </el-button>
                        <el-dropdown size="mini" trigger="click">
                            <el-button size="mini" type="primary">
                                更多操作<i class="el-icon-arrow-down el-icon--right"></i>
                            </el-button>
                            <el-dropdown-menu slot="dropdown">
                                <el-button
                                    size="mini"
                                    v-if="scope.row.adminUseful==0"
                                    @click="handleOpenAdmin(scope.$index, scope.row)
                                ">
                                    启用
                                </el-button>
                                <el-button
                                    size="mini"
                                    v-if="scope.row.adminUseful==1"
                                    @click="handleFreezeAdmin(scope.$index, scope.row)
                                ">
                                    冻结
                                </el-button>
                                <el-button
                                    size="mini"
                                    v-if="scope.row.adminUseful==2"
                                    @click="handleUnfreezeAdmin(scope.$index, scope.row)
                                ">
                                    解冻
                                </el-button>
                                <el-button
                                    size="mini"
                                    @click="handleResetPassword(scope.$index, scope.row)
                                ">
                                    重置密码
                                </el-button>
                                <el-button
                                    size="mini"
                                    @click="handleAssignRoleCollage(scope.$index, scope.row)
                                ">
                                    分配权限
                                </el-button>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pager" style="padding-top:10px; padding-right:10px; float: right;">
                <el-pagination
                background
                @size-change="handleAdminTableSizeChange"
                @current-change="handleAdminTableCurrentChange"
                :hide-on-single-page="false"
                :current-page="AdminPage.current"
                :page-sizes="[5, 10, 20, 30, 50, 100]"
                :page-size="AdminPage.showCount"
                layout="total, sizes, prev, pager, next, jumper"
                :total="AdminPage.totalCount"
                ></el-pagination>
            </div>
        </el-col>
    </el-row>

    <!-- ====================================增添或修改用户table============================================ -->
    <el-dialog
      :title="AdminAddUpdForm.title"
      :visible.sync="AdminAddUpdForm.visible"
      width="690px"
    >
      <el-form label-position="left">
        <el-form-item
          label="ID: "
          :label-width="AdminAddUpdForm.labelWidth">
          <el-input v-model="AdminAddUpdForm.adminId" :disabled="AdminAddUpdForm.submitState=='Upd'" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="用户名: "
          :label-width="AdminAddUpdForm.labelWidth">
          <el-input v-model="AdminAddUpdForm.adminAccount" :disabled="AdminAddUpdForm.submitState=='Upd'" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="用户真实姓名: " :label-width="AdminAddUpdForm.labelWidth">
          <el-input v-model="AdminAddUpdForm.adminName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户电话: " :label-width="AdminAddUpdForm.labelWidth">
          <el-input v-model="AdminAddUpdForm.adminPhone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注: " :label-width="AdminAddUpdForm.labelWidth">
          <el-input v-model="AdminAddUpdForm.adminText" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="启用时间: " :label-width="AdminAddUpdForm.labelWidth">
          <el-date-picker v-model="AdminAddUpdForm.adminTime1" type="datetime" placeholder="选择启用时间" format="yyyy-MM-dd HH:mm:ss"> </el-date-picker>
        </el-form-item>
        <el-form-item label="启用截止时间: " :label-width="AdminAddUpdForm.labelWidth">
          <el-date-picker v-model="AdminAddUpdForm.adminTime2" type="datetime" placeholder="启用截止时间" format="yyyy-MM-dd HH:mm:ss"> </el-date-picker>
        </el-form-item>
        <el-form-item label="上报截止时间: " :label-width="AdminAddUpdForm.labelWidth">
          <el-date-picker v-model="AdminAddUpdForm.adminTime3" type="datetime" placeholder="上报截止时间" format="yyyy-MM-dd HH:mm:ss"> </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="AdminAddUpdForm.visible = false">取 消</el-button>
        <el-button type="primary" @click="alterAdmin()">确 定</el-button>
      </div>
    </el-dialog>
    <!-- ====================================分配权限============================================ -->
    <el-dialog title="分配权限" :visible.sync="adminRoleCheckForm.visible" width="90%">
        
        <h1 style="font-size:16px">角色分配</h1>
        
        <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="adminRoleCheckForm.checkAll"
            @change="handleRoleCheckAll"
        >
            全选
        </el-checkbox>

        <el-checkbox-group
            v-model="adminRoleCheckForm.checked"
            @change="handleCheckedRoleChange"
        >
            <!-- role row -->
            <el-row>
                <el-col :span="4" 
                    v-for="role in roleSelectiveList"
                    :key="role.dictionaryId"
                    >
                    <el-checkbox 
                        :key="role.dictionaryId"
                        :label="role.dictionaryId"
                        >
                        {{role.dictionaryName}}
                    </el-checkbox>
                </el-col>
            </el-row>
        </el-checkbox-group>
  <!-- ====================================分配学院============================================ -->
        
        <h1 style="font-size:16px">学院分配</h1>
        
        <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="adminCollageCheckForm.checkAll"
            @change="handleCollageCheckAll"
        >
            全选
        </el-checkbox>

        <el-checkbox-group
            v-model="adminCollageCheckForm.checked"
            @change="handleCheckedCollageChange"
        >
            <el-row>
                <el-col :span="4" 
                    v-for="role in collageSelectiveList"
                    :key="role.dictionaryId"
                    >
                    <el-checkbox 
                        :key="role.dictionaryId"
                        :label="role.dictionaryId"
                        >
                        {{role.dictionaryName}}
                    </el-checkbox>
                </el-col>
            </el-row>
        </el-checkbox-group>

      <div slot="footer" class="dialog-footer">
        <el-button @click="adminRoleCheckForm.visible = false">取 消</el-button>
        <el-button type="primary" @click="assignAdminRolesCollages()">确 定</el-button>
      </div>
    </el-dialog>

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
                <el-button
                  size="mini"
                  type="success"
                  @click="downloadExcel(scope.$index, scope.row)"
                >下载Excel</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
        <el-col class="excel-col" :span="6">
          <h1>导入</h1>
          <el-upload
            class="upload-demo"
            ref="upload"
            :action="axios.defaults.baseURL + '/system/excel/importAdminExcel'"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
            :limit="1"
            :on-success="excelImportOver"
            accept=".xls"
            :auto-upload="false"
            :headers="{'token': token}"
          >
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button
              style="margin-left: 10px;"
              size="small"
              type="success"
              @click="submitUpload"
            >上传到服务器</el-button>
            <div slot="tip" class="el-upload__tip">只能上传xls/xlsx文件</div>
          </el-upload>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">

      </div>
    </el-dialog>

</div>  
</template>

<script>
export default {
    data(){
        return {
            token:"",
            // role collage form 选择框
            boxSelectForm:{
                roleId:"",
                collageId: ""
            },
            // role可选列表
            roleSelectiveList:[],
            // collage可选列表
            collageSelectiveList:[],
            // 用户表数据
            adminTableData: [],
            // 用户表view页面配置
            AdminPage: {
                showCount: 10,
                totalCount: 0,
                current: 1
            },
            // 添加或修改用户表数据 form
            AdminAddUpdForm: {
                title: "",
                visible: false,
                adminId: "",
                adminAccount: "",
                adminPassword: null,
                adminName: "",
                adminPhone: null,
                adminUseful: "",
                adminText: null,
                submitState: "",
                labelWidth: "120px",
                adminTime1: "",
                adminTime2: "",
                adminTime3: "",
            },
            // 用户分配角色权限 check
            adminRoleCheckForm: {
                visible: false,
                admin: null,
                checkAll: false,
                checked: [],
                isIndeterminate: true
            },
            // 用户分配角色权限 check
            adminCollageCheckForm: {
                visible: false,
                admin: null,
                checkAll: false,
                checked: [],
                isIndeterminate: true
            },
            // 限定符
            isIndeterminate: false, 
            excelForm: {
        title: "Excel导入与导出",
        visible: false,
        excelOutTableData: [
          {
            fileName: "用户信息导入模板.xls",
            describe: "导出用户信息的空Excel表",
            url: "/system/excel/exportAdminNullExcel"
          },
          {
            fileName: "用户信息表.xls",
            describe: "导出全部用户信息",
            url: "/system/excel/exportAdminExcel"
          }
        ]
      },
      fileList: [],
        }
    },
    methods:{
        // role选择列表选择
         initRoleSelectiveList(){
            const loading = this.$loading(this.$store.state.loadingOption1);
            this.axios
                .post("/system/getDictionaryItems", {
                params: {
                    dictionaryType: "ROLE"
                }
                },{ headers: { token: this.token } })
                .then(res => {
                if (res.data.code === 200) {
                    this.roleSelectiveList = res.data.data;
                } else if (res.data.code == 401) {
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
        // 学院选择列表选择
        initCollageSelectiveList(){
            const loading = this.$loading(this.$store.state.loadingOption1);
            this.axios
                .post("/system/getDictionaryItems", {
                params: {
                    dictionaryType: "COLLAGE"
                }
                },{ headers: { token: this.token } })
                .then(res => {
                if (res.data.code === 200) {
                    this.collageSelectiveList = res.data.data;
                } else if (res.data.code == 401) {
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
        // 用户信息表数据请求
        initAdminTable(){
            const loading = this.$loading(this.$store.state.loadingOption1);
            this.axios.post("system/getAdminByRoleAndCollage", {
                    params: {
                        roleId: this.boxSelectForm.roleId,
                        collageId: this.boxSelectForm.collageId,
                        currentPage: this.AdminPage.current,
                        showCount: this.AdminPage.showCount
                    }
                },
                { headers: { token: this.token } }).then(res => {
                    if (res.data.code === 200) {
                        this.AdminPage.totalCount = res.data.data.totalNum;
                        this.adminTableData = res.data.data.items;
                    } else if (res.data.code == 401) {
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

        // 处理增加用户信息
        handleAddAdmin(){
            this.AdminAddUpdForm.title = "添加用户信息";
            this.AdminAddUpdForm.visible = true;
            this.AdminAddUpdForm.adminId = "";
            this.AdminAddUpdForm.adminAccount = "";
            this.AdminAddUpdForm.adminName = "";
            this.AdminAddUpdForm.adminPhone = "";
            this.AdminAddUpdForm.adminText = "";
            this.AdminAddUpdForm.adminUseful = "";
            this.AdminAddUpdForm.adminTime1 = "";
            this.AdminAddUpdForm.adminTime2 = "";
            this.AdminAddUpdForm.adminTime3 = "";
            this.AdminAddUpdForm.submitState = "Add";
        },
        // 处理修改用户
        handleUpdAdmin(index, row){
            this.AdminAddUpdForm.title = "修改用户信息";
            this.AdminAddUpdForm.visible = true;
            this.AdminAddUpdForm.adminId = row.adminId;
            this.AdminAddUpdForm.adminAccount = row.adminAccount;
            this.AdminAddUpdForm.adminName = row.adminName;
            this.AdminAddUpdForm.adminPhone = row.adminPhone;
            this.AdminAddUpdForm.adminText = row.adminText;
             this.AdminAddUpdForm.adminUseful =  row.adminUseful;
            this.AdminAddUpdForm.adminTime1 = row.adminTime1;
            this.AdminAddUpdForm.adminTime2 = row.adminTime2;
            this.AdminAddUpdForm.adminTime3 = row.adminTime3;
            this.AdminAddUpdForm.submitState = "Upd";
        },
        // 删除用户请求
        handleDeleteAdmin(index, row){
            this.AdminAddUpdForm.adminId = row.adminId;
            this.AdminAddUpdForm.adminAccount = row.adminAccount;
            const loading = this.$loading(this.$store.state.loadingOption2);
            this.axios.post("/system/removeAdmin", {
                params: {
                    admin: this.AdminAddUpdForm
                }
                },{ headers: { token: this.token } }).then(res => {
                    if (res.data.code === 200) {
                        this.initAdminTable();
                        this.$message({
                            showClose: true,
                            message: res.data.data,
                            type: "success"
                        });
                } else if (res.data.code == 401) {
                    this.$router.push({ path: "/login" });
            } else {
                    this.initAdminTable();
                    this.$message({
                        showClose: true,
                        message: res.data.data,
                        type: "error"
                    });
                }
                loading.close();
            });
        },

        handleAdminTableSizeChange(val) {
            this.AdminPage.showCount = val;
            this.AdminPage.current = 1;
            this.initAdminTable();
        },
        handleAdminTableCurrentChange(val) {
            this.AdminPage.current = val;
            this.initAdminTable();
        },
        // 冻结用户操作请求
        handleFreezeAdmin(index, row){
            const loading = this.$loading(this.$store.state.loadingOption2);
            this.axios.post("/system/freezeAdmin", {
                params: {
                    admin: row
                }
                },{ headers: { token: this.token } }).then(res => {
                    if (res.data.code === 200) {
                        this.initAdminTable();
                        this.$message({
                            showClose: true,
                            message: res.data.data,
                            type: "success"
                        });
                } else if (res.data.code == 401) {
                    this.$router.push({ path: "/login" });
            } else {
                    this.initAdminTable();
                    this.$message({
                        showClose: true,
                        message: res.data.data,
                        type: "error"
                    });
                }
                loading.close();
            });
        },
        // 解冻用户操作请求
        handleUnfreezeAdmin(index, row){
            const loading = this.$loading(this.$store.state.loadingOption2);
            this.axios.post("/system/unfreezeAdmin", {
                params: {
                    admin: row
                }
                },{ headers: { token: this.token } }).then(res => {
                    if (res.data.code === 200) {
                        this.initAdminTable();
                        this.$message({
                            showClose: true,
                            message: res.data.data,
                            type: "success"
                        });
                } else if (res.data.code == 401) {
                    this.$router.push({ path: "/login" });
            } else {
                    this.initAdminTable();
                    this.$message({
                        showClose: true,
                        message: res.data.data,
                        type: "error"
                    });
                }
                loading.close();
            });
        },
        handleOpenAdmin(index, row){
            const loading = this.$loading(this.$store.state.loadingOption2);
            this.axios.post("/system/openAdmin", {
                params: {
                    admin: row
                }
                },{ headers: { token: this.token } }).then(res => {
                    if (res.data.code === 200) {
                        this.initAdminTable();
                        this.$message({
                            showClose: true,
                            message: res.data.data,
                            type: "success"
                        });
                } else if (res.data.code == 401) {
                    this.$router.push({ path: "/login" });
            } else {
                    this.initAdminTable();
                    this.$message({
                        showClose: true,
                        message: res.data.data,
                        type: "error"
                    });
                }
                loading.close();
            });
        },
        // 修改用户信息请求
        alterAdmin(){
            const loading = this.$loading(this.$store.state.loadingOption2);
            this.axios.post("/system/alterAdmin", {
                params: {
                    admin: this.AdminAddUpdForm
                }
                },{ headers: { token: this.token } }).then(res => {
                    if (res.data.code === 200) {
                        this.initAdminTable();
                        this.$message({
                            showClose: true,
                            message: res.data.data,
                            type: "success"
                        });
                        this.AdminAddUpdForm.visible = false;
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
        // 处理分配权限操作按钮
        handleAssignRoleCollage(index, row){
            // admin对象拷贝入form
            this.adminRoleCheckForm.admin = row;
            this.adminCollageCheckForm.admin = row;
            // 显示check box
            this.adminRoleCheckForm.visible = true;
            // checked box 传入admin的role and collage list
            this.adminRoleCheckForm.checked = row.adminRoleIdList;
            this.adminCollageCheckForm.checked = row.adminCollageIdList;
            
        },
        // 处理重置密码请求
        handleResetPassword(index, row){
            const loading = this.$loading(this.$store.state.loadingOption2);
            this.axios.post("/system/resetAdminPassword", {
                params: {
                    admin: row
                }
                },{ headers: { token: this.token } }).then(res => {
                    if (res.data.code === 200) {
                        this.initAdminTable();
                        this.$message({
                            showClose: true,
                            message: res.data.data,
                            type: "success"
                        });
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
        // 分配角色 学院权限请求
        assignAdminRolesCollages() {
            const loading = this.$loading(this.$store.state.loadingOption2);
            // 分配角色请求
            this.axios.post("/system/assignRole", 
                {
                    params: {
                        admin: this.adminRoleCheckForm.admin,
                        roleIdList: this.adminRoleCheckForm.checked
                    }
                },
                { headers: { token: this.token } }).then(res => {
                    if (res.data.code === 200) {
                        this.$message({
                            showClose: true,
                            message: res.data.data,
                            type: "success"
                        });
                    } else if (res.data.code == 401) {
                        this.$router.push({ path: "/login" });
                    } else {
                        this.$message({
                        showClose: true,
                        message: res.data.data,
                        type: "error"
                    });
                }
            });
            // 分配学院请求
            this.axios.post("/system/assignCollage", 
                {
                    params: {
                        admin: this.adminRoleCheckForm.admin,
                        collageIdList: this.adminCollageCheckForm.checked
                    }
                },
                { headers: { token: this.token } }).then(res => {
                    if (res.data.code === 200) {
                        this.initAdminTable();
                        this.$message({
                            showClose: true,
                            message: res.data.data,
                            type: "success"
                        });
                        this.adminRoleCheckForm.visible = false;
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
        // role check 选中handle
        handleCheckedRoleChange(value) {
            var allCount = this.roleSelectiveList.length;
            // console.log(this.adminRoleCheckForm.checked);
            
            let checkedCount = value.length;
            this.checkAll = checkedCount === allCount;
            this.isIndeterminate = checkedCount > 0 && checkedCount < allCount;
        },
        // collage check 选中handle
        handleCheckedCollageChange(value) {
            var allCount = this.collageSelectiveList.length;
            // console.log(this.adminCollageCheckForm.checked);
            
            let checkedCount = value.length;
            this.checkAll = checkedCount === allCount;
            this.isIndeterminate = checkedCount > 0 && checkedCount < allCount;
        },
        // role check all 选中处理
        handleRoleCheckAll(val) {
            if (val) {
                this.adminRoleCheckForm.checked = [];
                for (let index = 0; index < this.roleSelectiveList.length; index++) {
                    this.adminRoleCheckForm.checked.push(this.roleSelectiveList[index].dictionaryId);   
                }
                console.log( this.adminRoleCheckForm.checked);
            } else {
                this.adminRoleCheckForm.checked = [];
            }
            this.isIndeterminate = false;
        },
        // collage check all 选中处理
        handleCollageCheckAll(val) {
            if (val) {
                this.adminCollageCheckForm.checked = [];
                for (let index = 0; index < this.collageSelectiveList.length; index++) {
                    this.adminCollageCheckForm.checked.push(this.collageSelectiveList[index].dictionaryId);   
                }
            } else {
                this.adminCollageCheckForm.checked = [];
            }
            this.isIndeterminate = false;
        },


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
            this.initAdminTable();
          }
        });
      } else if (response.code == 401) {
        this.$router.push({ path: "/login" });
      } else {
        this.$alert(response.data, "上传失败", {
          confirmButtonText: "确定",
          type: "error",
          callback: action => {
            this.initAdminTable();
          }
        });
      }
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
    
    },
    created: function(){
        //登陆验证代码
        //权限验证代码
        this.token = localStorage.getItem("token");
        if(this.token == ""||this.token == null){
        this.$router.push({ path: "/login" });
        return;
        }
        // 加载Role列表数据
        this.initRoleSelectiveList();
        // 加载学院列表数据
        this.initCollageSelectiveList();
        // 加载admin table data
        this.initAdminTable();
    }

}
</script>

<style  lang="scss">
.el-main {
  padding: 0px;
}
.main-col {
  padding: 20px;
}
.el-form-item{
  margin-top: 20px;
  margin-left: 20px;
  margin-right: 0px;
   margin-bottom: 0px;
}
</style>