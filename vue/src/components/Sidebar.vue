<template>
  <div class="sidebar" :style="{'width': collapse ? 'auto' : '250px'}">
    <el-row style="height:25px">

      <el-button type="text" v-if="!collapse" style="font-size:25px;float:right;margin-right:5px;padding:0px;" @click="collapse = true" icon="el-icon-s-fold"></el-button>
      <el-button type="text" v-if="collapse" style="font-size:25px;float:right;margin-right:5px;padding:0px;" @click="collapse = false" icon="el-icon-s-unfold"></el-button>
    </el-row>
    <el-menu class="el-menu-vertical-demo" @select="handleSelect" background-color="#E0E0E0" text-color="#000" :default-active="active" :default-openeds="opened" :collapse="collapse">
      <el-menu-item :index="{powerRouter:'/home/index'}">
        <i class="el-icon-s-home"></i>
        <span slot="title">首页</span>
      </el-menu-item>
      <el-submenu v-for="power in powerList" :key="power.powerId" :index="power" :router="power.powerRouter">
        <template slot="title">
          <i :class="''+power.powerIcon"></i>
          <span>{{power.powerName}}</span>
        </template>
        <el-menu-item-group>
          <el-menu-item v-for="child in power.childrenPowerList" :key="child.powerId" :index="child" :router="child.powerRouter">
            <template slot="title">
              <i :class="''+child.powerIcon"></i>
              <span>{{child.powerName}}</span>
            </template>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>

  </div>
</template>
<script>
import store from "../vuex/store.js";
export default {
  store,
  data() {
    return {
      collapse: false,
      admin: "",
      powerList: [],
      active: "",
      opened: [],
      activeIndex: "1",
      activeIndex2: "1"
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      //console.log(keyPath);
      // if(keyPath.length==1){
      //   localStorage.setItem("sidebarOpened", JSON.stringify(keyPath[0]));
      //   localStorage.removeItem("sidebarActive");
      // }else if(keyPath.length==2){
      //   localStorage.setItem("sidebarOpened", JSON.stringify(keyPath[0]));
      //   localStorage.setItem("sidebarActive", JSON.stringify(keyPath[1]));
      // }
      this.$router.push({ path: key.powerRouter });

      // if (keyPath[0] == "1") {
      //   this.$router.push({ path: "/home/setting/media" });
      // }
      // if (keyPath[0] == "2") {
      //   this.$router.push({ path: "/home/unit/equipment" });
      // }
      // if (keyPath[0] == "3") {
      //   if (keyPath[1] == "3-1") {
      //     this.$router.push({ path: "/home/mult/attribute/type" });
      //   }
      //   if (keyPath[1] == "3-2") {
      //     this.$router.push({ path: "/home/mult/equipment/type" });
      //   }
      //   if (keyPath[1] == "3-3") {
      //     this.$router.push({ path: "/home/mult/equipment/list" });
      //   }
      // }
      // // if (keyPath[0] == "2") {
      // //   if (keyPath[1] == "2-1") {
      // //     this.$router.push({ path: "/home/production/equipment" });
      // //   }
      // //   if (keyPath[1] == "2-2") {
      // //     this.$router.push({ path: "/home/production/condition" });
      // //   }
      // //   if (keyPath[1] == "2-3") {
      // //     this.$router.push({ path: "/home/production/proplan" });
      // //   }
      // //   if (keyPath[1] == "2-4") {
      // //     this.$router.push({ path: "/home/production/maiplan" });
      // //   }
      // // }
      // if (keyPath[0] == "4") {
      //   if (keyPath[1] == "4-1") {
      //     this.$router.push({ path: "/homeEcharts/forecast/runtime" });
      //   }
      //   if (keyPath[1] == "4-2") {
      //     this.$router.push({ path: "/homeEcharts/forecast/history" });
      //   }
      // }
      // if (keyPath[0] == "5") {
      //   if (keyPath[1] == "5-1") {
      //     this.$router.push({ path: "/homeEcharts/optimize/runtime" });
      //   }
      //   if (keyPath[1] == "5-2") {
      //     this.$router.push({ path: "/homeEcharts/optimize/history" });
      //   }
      // }
      // if (keyPath[0] == "6") {
      //   this.$router.push({ path: "/home/admin" });
      // }

      //console.log(keyPath[0]);
    },
    checkPower(data) {
      return true;
      //return sessionStorage.getItem("adminPower").indexOf(data) != -1
    },




  },
  created: function () {
    this.powerList = JSON.parse(localStorage.getItem("powerList"));

    // this.admin = JSON.parse(localStorage.getItem("admin"));
    // if (this.admin == "" || this.admin == null) {
    //   this.$router.push({ path: "/login" });
    //   return;
    // }

    // var opened = JSON.parse(localStorage.getItem("sidebarOpened"));
    // if (opened == "" || opened == null) {
    //   for(var i=0;i<this.powerList.length;i++){
    //     if(this.powerList[i].powerId == opened.powerId){
    //       //this.opened = this.powerList[i];
    //       this.opened.push(opened);
    //       break;
    //     }
    //   }
    // }
    // var active = JSON.parse(localStorage.getItem("sidebarActive"));
    // if (active == "" || active == null) {
    //   for(var i=0;i<this.opened.childrenPowerList.length;i++){
    //     if(this.opened.childrenPowerList[i].powerId == active.powerId){
    //       this.active = this.opened.childrenPowerList[i];
    //       break;
    //     }
    //   }
    // }

  }
};
</script>
<style scoped>
.el-submenu__title i {
  color: #e31f03;
}
.el-menu-item i {
  color: #e31f03;
}
.sidebar {
  /* height: 9999px;
  margin-bottom: -9999px;
  overflow: hidden; */
  background-color: #e0e0e0;
}
</style>
