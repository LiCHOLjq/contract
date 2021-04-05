<template>
  <div class="header">
    <el-menu class="el-menu-demo" mode="horizontal" @select="handleSelect" background-color="#E0E0E0" text-color="#00000" active-text-color="#8d8d8d">
      <img class="head-img" width="250" height="60" src="../assets/img/logo-ow-bg.png" />

      <div class="head-title">
        标案合同管理系统
      </div>
    </el-menu>
  </div>
</template>
<script>

export default {

  data() {
    return {

    };
  },
  methods: {



  },
  created: function () {



  },
}
</script>
<style scoped>
.head-img {
  float: left;
}
.head-title {
  height: 60px;
  line-height: 60px;
  font-size: 25px;
  width: 400px;
  text-align: center;
  color: #000;
  z-index: 99;

  margin: 0px auto;
}
.head-left {
  float: right;
  width: 625px;
}
.head-left li {
  float: left;
}
.el-menu--horizontal > .el-menu-item {
  float: right;
}
.el-menu--horizontal > .el-submenu {
  float: right;
}
</style>
