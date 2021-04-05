<template>
  <div id="news">
    <editor class="news_editor" v-model="news.html" ref="editerCenter" @contentData="onEditorChange($event)"></editor>

    <!-- <div class="login-wrap">
      <el-row>
        <el-col :span="24">
          <el-button style="float:left;" type="primary" icon="el-icon-back" @click="back">返回</el-button>
          <div class="ms-login">
            <el-form :model="form" label-width="0px" class="demo-ruleForm">
              <div class="ms-title">东北大学招生宣传工作新闻编辑器</div>
              <h1>新闻标题</h1>
              <el-row>
                <el-form-item>
                  <el-input v-model="news.newsTitle" autocomplete="off" @change="onTitleChange()"></el-input>
                </el-form-item>
              </el-row>
              <el-row class="auto-table">
                <p v-if="!isAdd">
                  发布人:{{news.newsAdminObj == null ? '' : news.newsAdminObj.adminName == null ? '' : news.newsAdminObj.adminName}}&nbsp;&nbsp;&nbsp;&nbsp;
                  发布时间:{{news.newsDateStr == null ? '' : news.newsDateStr}}&nbsp;&nbsp;&nbsp;&nbsp;
                </p>
              </el-row>
              <h1>新闻内容</h1>
              <el-row>
                
              </el-row>
              <el-row class="auto-table">
                <p v-if="!isAdd">
                  所属备案:{{news.newsProjectObj == null ? '' : news.newsProjectObj.projectName == null ? '' : news.newsProjectObj.projectName}}&nbsp;&nbsp;&nbsp;&nbsp;
                  发布状态:{{news.newsStateObj == null ? '' : news.newsStateObj.dictionaryName == null ? '' : news.newsStateObj.dictionaryName}}&nbsp;&nbsp;&nbsp;&nbsp;
                  浏览次数:{{news.newsViews == null ? '' : news.newsViews}}
                </p>
              </el-row>
              <el-row class="auto-table">
                <el-form-item>
                  <el-button v-if="isAdd" icon="el-icon-upload2" @click="alterNews" type="primary">创建新闻</el-button>
                  <el-button v-if="!isAdd&&news.newsState == 'news_state_close'" :disabled="!isEdit" icon="el-icon-upload2" @click="alterNews" type="primary">提交修改</el-button>
                  <el-button v-if="!isAdd&&news.newsState == 'news_state_close'" icon="el-icon-news" @click="publicNews" type="success">发布新闻</el-button>
                  <el-button v-if="!isAdd&&news.newsState == 'news_state_open'" icon="el-icon-refresh-left" @click="privateNews" type="danger">撤回新闻</el-button>
                  <el-button v-if="!isAdd" icon="el-icon-delete" @click="handelDelNews" type="danger">删除新闻</el-button>
                </el-form-item>
              </el-row>
            </el-form>
          </div>
        </el-col>
      </el-row>
    </div> -->
  </div>
</template>

<script>

// import store from "../vuex/store.js";
import editor from "./plan/editor.vue";
export default {
  // store,
  data: function () {
    return {
      isAdd: false,
      isEdit: false,
      news: {
        newsTitle: "",
        html: "",
        newsProject: "",
        newsProjectObj: null,
        newsDateStr: "",
        newsAdminObj: null,
        newsStateObj: null,
        newsViews: 0,
        submitState: 'Add'
      },

    };
  },
  components: {
    editor
  },
  computed: {
    editor() {
      return this.$refs.myQuillEditor.quill;
    },
  },
  methods: {
    // back() {
    //   this.$router.push({ path: "/home/project/list" });
    // },
    // onTitleChange() {
    //   this.isEdit = true;
    // },
    onEditorChange(html) {
      //console.log(event)
      this.news.html = html;
      //this.isEdit = true;
      // this.questionAddUpdForm.html = html
    }, // 内容改变事件
    show() {
      console.log(this.news.html)
    },
    selc() {
      alert("sdfasdf")
    }
    // handelDelNews() {
    //   this.$confirm('您确定删除此新闻吗？', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     this.news.submitState = 'Del'
    //     this.alterNews();
    //   }).catch(() => {

    //   });
    // },

    // alterNews() {
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios.post("/news/alterNews", {
    //     params: {
    //       news: this.news
    //     }
    //   }, { headers: { token: this.token } }).then(res => {
    //     if (res.data.code === 200) {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "success"
    //       });
    //       if (this.news.submitState == "Add" || this.news.submitState == "Upd") {
    //         this.init(res.data.object)
    //         this.isEdit = false;
    //         this.$router.push({ path: "/home/project/news/" + res.data.object });
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

    // publicNews() {
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios.post("/news/publicNews", {
    //     params: {
    //       news: this.news,
    //     }
    //   }, { headers: { token: this.token } }).then(res => {
    //     if (res.data.code === 200) {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "success"
    //       });
    //       this.init(this.news.newsId)
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
    // privateNews() {
    //   const loading = this.$loading(this.$store.state.loadingOption2);
    //   this.axios.post("/news/privateNews", {
    //     params: {
    //       news: this.news,
    //     }
    //   }, { headers: { token: this.token } }).then(res => {
    //     if (res.data.code === 200) {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.data,
    //         type: "success"
    //       });
    //       this.init(this.news.newsId)
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
    // init(newsId) {
    //   const loading = this.$loading(this.$store.state.loadingOption1);
    //   this.axios
    //     .post(
    //       "/news/getNewsById",
    //       {
    //         params: {
    //           newsId: newsId
    //         }
    //       },
    //       { headers: { token: this.token } }
    //     )
    //     .then(res => {
    //       if (res.data.code === 200) {
    //         this.news = res.data.object;
    //         this.$refs.editerCenter.setContentData(this.news.html)
    //         this.isAdd = false;
    //         this.news.submitState = 'Upd'
    //         this.$nextTick(() => {
    //           this.isEdit = false;
    //         })

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
  },
  created: function () {
    window.show = this.show;
    window.selc = this.selc;
    // this.token = localStorage.getItem("token");
    // if (this.token == "" || this.token == null) {
    //   this.$router.push({ path: "/login" });
    //   return;
    // }
    // if (this.$route.params.newsId == null && this.$route.params.projectId != null) {
    //   this.isAdd = true;
    //   this.news.newsProject = this.$route.params.projectId;
    //   this.news.submitState = 'Add'
    // } else if (this.$route.params.newsId != null && this.$route.params.projectId == null) {
    //   this.news.submitState = 'Upd'
    //   this.init(this.$route.params.newsId)
    // }


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
#news {
  // height: 966px;
  //background: url("../assets/img/login-bg2.png") no-repeat center bottom;
  background-size: 100%;
  height: 100%;
  .ql-toolbar {
    display: none;
  }
  .news_editor {
    height: 100%;
  }
  .ql-editor p {
    font-size: 16px;
    .ql-size-small {
      font-size: 12px;
    }
    .ql-size-large {
      font-size: 20px;
    }
    .ql-size-huge {
      font-size: 28px;
    }
  }
}
</style>