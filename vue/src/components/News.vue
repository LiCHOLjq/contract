<template>
  <div id="news">
    <img  width="1" height="1" src="../assets/img/logo.png" />
   <div class="content">
     <div class="title">{{news.newsTitle == null ? "" : news.newsTitle}}</div>
     <div class="sign">
       <span class="author">{{news.newsAdminObj == null ? "" : news.newsAdminObj.adminName == null ? "" : news.newsAdminObj.adminName}}</span><span>{{news.newsAdminObj == null ? "" : "发布于"}}</span>
       <span class="time">{{news.newsDateStr == null ? "" : news.newsDateStr}}</span>
       </div>
       <div class="ql-container ql-snow" style="border:0px">
       <div class="ql-editor" v-html="news.html"  style="padding:0px"></div>
        <!--   -->
      </div>
      <div class="sign" style="margin-top:30px">
        <span class="author">{{news.newsGroupObj == null ? "" : news.newsGroupObj.groupName == null ? "" : news.newsGroupObj.groupName}}</span>
        <span class="read">{{news.newsViews == null ? "" :( "阅读：" + news.newsViews +"次")}}</span>
      </div>
   </div>
   <div v-if="showCode" class="qr_code" :style="'left:' +( screenWidth - (screenWidth - 710) / 2 ) + 'px'">
     <div class="qrcode" ref="qrCodeUrl"></div>
     <p>微信扫一扫<br>用手机查看</p>
     </div>
  </div>

</template>
      
<script>
import store from "../vuex/store.js";
import QRCode from 'qrcodejs2'
export default {
  store,
  data: function () {
    return {
      screenWidth:1920,
      showCode:true,
      news: {
        newsTitle: "",
        html: "",
        newsProject: "",
        newsProjectObj: null,
        newsDateStr: "",
        newsAdminObj: null,
        newsStateObj: null,
        newsViews: null,
        submitState: 'Add'
      },

    };
  },

  methods: {
    init(newsId) {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post(
          "/news/getPublicNewsById",
          {
            params: {
              newsId: newsId
            }
          },
          // { headers: { token: this.token } }
        )
        .then(res => {
          if (res.data.code === 200) {
            this.news = res.data.object;
            document.title = this.news.newsTitle;
            // this.$refs.editerCenter.setContentData(this.news.html)
            this.isAdd = false;
            this.news.submitState = 'Upd'
            this.$nextTick(() => {
              this.isEdit = false;
            })
            
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
    creatQrCode() {
        var qrcode = new QRCode(this.$refs.qrCodeUrl, {
            text: window.location.href, // 需要转换为二维码的内容
            width: 100,
            height: 100,
            colorDark: '#000000',
            colorLight: '#ffffff',
            correctLevel: QRCode.CorrectLevel.L
        })
    },
  },
  mounted(){
    this.screenWidth = document.body.clientWidth
        if(this.screenWidth>1095){
            this.creatQrCode();
          this.showCode = true;
          
        }else{
          this.showCode = false;
        }
    window.onresize = () => {
      return (() => {
        this.screenWidth = document.body.clientWidth
        if(this.screenWidth>1095){
          if(this.showCode == false){
            this.showCode = true;
            this.$nextTick(() => {
              this.creatQrCode();
            })
            
          }
          
        }else{
          this.showCode = false;
        }
      })()
    }
  },
  created: function () {

    if (this.$route.params.newsId != null) {
      this.init(this.$route.params.newsId)
      
    }else{
      this.$alert("新闻不存在", "错误", {
        confirmButtonText: "确定",
        type: "error",
        callback: action => { }
      });
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
#news {
  padding: 16px;
  // background-color: #fafafa;
  .content{
    max-width: 677px;
    margin-left: auto;
    margin-right: auto;
  }
  .sign{
    margin-bottom: 22px;
    line-height: 20px;
    font-size: 15px;
    .author{
      color: #409EFF;
      margin-right:10px ;
    }
    .read{
      float: right ;
    }
  }
  .title {
    font-size: 22px;
    line-height: 1.4;
    margin-bottom: 14px;
  }
  .ql-editor p{
      font-size: 16px;
      .ql-size-small{
        font-size: 12px;
      }
      .ql-size-large{
        font-size: 20px;
      }
      .ql-size-huge {
        font-size: 28px;
      }
  }
  .qr_code {
    text-align: center;
    position: absolute;
    top: 50px;
    width: 140px;
    padding: 16px;
    border: 1px solid #d9dadc;
    background-color: #fff;
    word-wrap: break-word;
    -webkit-hyphens: auto;
    -ms-hyphens: auto;
    hyphens: auto;
    p{
      font-size: 14px;
    line-height: 20px;
    margin-bottom: 0px ;
    }
  }
  .qrcode{
    img{
      margin: auto;
    }
  }
}
</style>