<template>
  <div id="inds">
    <!-- <img class="head-img" width="700" src="../assets/img/wx.jpg" /> -->
    <!-- <img class="head-img" width="700" src="../assets/img/zfb.jpg" />s -->
    <!-- <el-row v-for="power in powerList" :key="power.powerId">
      <h1 class="index-title">{{power.powerName}}</h1>

      <el-col v-for="child in power.childrenPowerList" :key="child.powerId" class="index-col" :span="4">
        <el-card shadow="hover" class="dlist" @click.native="toLink(child.powerRouter)">
          <i :class="''+child.powerIcon"></i>
          <h3>{{child.powerName}}</h3>
        </el-card>
      </el-col>

    </el-row> -->
    <!-- <el-carousel indicator-position="outside">
      <el-carousel-item
        v-for="item in imagesbox"
        :key="item.id"
      >
        <img
          :src="item.idView"
          class="image"
        />
      </el-carousel-item>
    </el-carousel> -->
  </div>
</template>

<script>
import store from "../vuex/store.js";

export default {
  store,
  data() {
    return {
      token: "",
      admin: "",
      powerList: [],
      imagesbox: [
        // { id: 1, idView: require("../assets/img/index_1.jpg") },
        // { id: 2, idView: require("../assets/img/index_2.jpg") },
        // { id: 3, idView: require("../assets/img/index_3.jpg") },
        // { id: 4, idView: require("../assets/img/index_4.jpg") },
        // { id: 5, idView: require("../assets/img/index_5.jpg") },
        // { id: 6, idView: require("../assets/img/index_6.jpg") },
        // { id: 7, idView: require("../assets/img/index_7.jpg") },
        // { id: 8, idView: require("../assets/img/index_8.jpg") },
        // { id: 9, idView: require("../assets/img/index_9.jpg") }
      ]
    };
  },
  methods: {
    toLink(val) {
      this.$router.push({ path: val });
    },
    tokenCheck() {
      const loading = this.$loading(this.$store.state.loadingOption1);
      this.axios
        .post("/tokenCheck", {
          params: {
          }
        }, { headers: { token: localStorage.getItem("token") } })
        .then(res => {
          if (res.data.code === 200) {
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
          }
          loading.close();
        });
    },
  },
  created: function () {


    // this.token = localStorage.getItem("token");
    // if (this.token == "" || this.token == null) {
    //   this.$router.push({ path: "/login" });
    //   return;
    // }
    // this.tokenCheck();
    // this.powerList = JSON.parse(localStorage.getItem("powerList"));
    // this.admin = JSON.parse(localStorage.getItem("admin"));
    // if (this.admin == "" || this.admin == null) {
    //   this.$router.push({ path: "/login" });
    //   return;
    // }

    // var admin = sessionStorage.getItem("adminId")
    // if (admin == null) {
    //   this.$router.push({ path: '/login' });
    //   return;
    // }
  }
};
</script>
  <style lang="scss">
#inds {
  margin: 30px;
}
.index-col {
  padding: 30px;
}

.dlist {
  height: 200px;
}
.dlist i {
  font-size: 50px;
  color: #606266;
  display: block;
  text-align: center;
  padding: 20px;
}
.dlist:hover {
  background-color: rgb(241, 241, 241);
}
.dlist:hover i {
  color: #409eff !important;
}
.dlist:hover h3 {
  color: #409eff !important;
}
.dlist h3 {
  color: #606266;

  text-align: center;
}
.index-title {
  font-size: 32px;
  text-align: center;
}
// #left {
//   float: left;
//   width: 200px;
// }
// #right {
//   float: left;
//   display: block;
//   padding-top: 20px;
//   padding-bottom: 20px;
//   padding-left: 240px;
//   padding-right: 40px;
// }
// .image {
//   width: 100%;
// }
// #ind {
//   height: 826px;
// }
// .el-carousel .el-carousel--horizontal {
//   height: 826px;
// }
// .el-carousel__container {
//   height: 826px;
// }
// .text {
//   font-size: 18px;
// }

// .item {
//   margin-bottom: 18px;
// }
// .item span {
//   text-align: left;
// }
// .spanleft {
//   width: 100px;
//   min-width: 100px;
//   float: left;
// }
// .text2 {
//   font-size: 14px;
//   margin-left: 10px;
//   margin-bottom: 10px;
// }

// .clearfix {
//   text-align: center;
//   font-size: 24px;
//   margin-bottom: 18px;
// }

// .box-card {
//   width: 100%;
// }
// .el-drawer__body {
//   overflow-y: auto;
// }
// #qrcode {
//   display: inline-block;
//   padding: auto;
// }
// #qrcode img {
//   width: 256px;
//   height: 256px;
//   background-color: #fff;
//   padding: 6px;
// }

// .el-dialog__body {
//   padding-left: 40px;
//   padding-right: 40px;
// }
// .el-select {
//   width: 100%;
// }
</style>