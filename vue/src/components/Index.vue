<template>
  <div id="inds">
    <el-row style="height:50%;">
      <el-col :span="12" style="height:calc(100% - 40px)">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>合同信息统计表</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="exportAgreementData">导出Excel</el-button>
          </div>
          <el-table ref="agreementTable" v-bind:data="agreementData.dataList" border style="width: 100%" :max-height="tableHeight">
            <el-table-column label="年份" fixed="left">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.year == null ? '' : scope.row.year }}</span>
              </template>
            </el-table-column>
            <el-table-column v-for="(item,index) in agreementData.typeList" :key="index" :label="item">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.data[index] == null ? '' : scope.row.data[index] }}</span>
              </template>
            </el-table-column>
            <el-table-column label="总计">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.sum == null ? '' : scope.row.sum }}</span>
              </template>
            </el-table-column>
          </el-table>

        </el-card>
      </el-col>
      <el-col :span="12" style="height:calc(100% - 40px)">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>合同信息统计图</span>
            <el-dropdown style="float: right; padding: 3px 0;margin-left:10px;" @command="setAgreementDataYear">
              <span class="el-dropdown-link">{{agreementData.yearList[agreementData.thisDataIndex] == null ? "" : agreementData.yearList[agreementData.thisDataIndex]}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-for="(item,index) in agreementData.yearList" :key="index" icon="el-icon-date" :command="index">{{item}}</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-dropdown style="float: right; padding: 3px 0;margin-left:10px;" @command="setAgreementDataType">
              <span class="el-dropdown-link">{{agreementData.type == "bar" ? "条形图" : agreementData.type == "pie" ? "饼状图" : "请选择图形"}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item icon="el-icon-s-data" command="bar">条形图</el-dropdown-item>
                <el-dropdown-item icon="el-icon-pie-chart" command="pie">饼状图</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-dropdown style="float: right; padding: 3px 0;margin-left:10px;" @command="setAgreementDataAuto">
              <span class="el-dropdown-link">{{agreementData.auto ? "已开启自动切换" : "已关闭自动切换"}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item icon="el-icon-video-play" :command="true">开启自动切换</el-dropdown-item>
                <el-dropdown-item icon="el-icon-video-pause" :command="false">关闭自动切换</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <!-- <el-button style="float: right; padding: 3px 0" type="text">合同信息统计图</el-button> -->
          </div>
          <div id="agreementChart" :style="{width: '100%', height: chartHeight}"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row style="height:50%;">
      <el-col :span="12" style="height:calc(100% - 40px)">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>产品信息统计表</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="exportProductData">导出Excel</el-button>
            <!-- <el-button style="float: right; padding: 3px 0" type="text">产品信息统计表</el-button> -->
          </div>
          <el-table v-bind:data="productData.typeList" border style="width: 100%" :max-height="tableHeight">
            <el-table-column label="类型" fixed="left">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row == null ? '' : scope.row }}</span>
              </template>
            </el-table-column>
            <el-table-column label="合同数">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ productData.dataList[0].data[scope.$index] == null ? '' : productData.dataList[0].data[scope.$index] }}</span>
              </template>
            </el-table-column>
            <el-table-column label="台数（台）">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ productData.dataList[1].data[scope.$index] == null ? '' : productData.dataList[1].data[scope.$index] }}</span>
              </template>
            </el-table-column>
            <el-table-column label="金额（万）">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ productData.dataList[2].data[scope.$index] == null ? '' : productData.dataList[2].data[scope.$index] }}</span>
              </template>
            </el-table-column>

          </el-table>

        </el-card>
      </el-col>
      <el-col :span="12" style="height:calc(100% - 40px)">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>产品信息统计图</span>
            <el-dropdown style="float: right; padding: 3px 0;margin-left:10px;" @command="setProductDataTitle">
              <span class="el-dropdown-link">{{productData.titleList[productData.thisDataIndex] == null ? "" : productData.titleList[productData.thisDataIndex]}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-for="(item,index) in productData.titleList" :key="index" icon="el-icon-bangzhu" :command="index">{{item}}</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-dropdown style="float: right; padding: 3px 0;margin-left:10px;" @command="setProductDataType">
              <span class="el-dropdown-link">{{productData.type == "bar" ? "条形图" : productData.type == "pie" ? "饼状图" : "请选择图形"}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item icon="el-icon-s-data" command="bar">条形图</el-dropdown-item>
                <el-dropdown-item icon="el-icon-pie-chart" command="pie">饼状图</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-dropdown style="float: right; padding: 3px 0;margin-left:10px;" @command="setProductDataAuto">
              <span class="el-dropdown-link">{{productData.auto ? "已开启自动切换" : "已关闭自动切换"}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item icon="el-icon-video-play" :command="true">开启自动切换</el-dropdown-item>
                <el-dropdown-item icon="el-icon-video-pause" :command="false">关闭自动切换</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <div id="productChart" :style="{width: '100%', height: chartHeight}"></div>
        </el-card>
      </el-col>
    </el-row>

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
      screenWidth: '',
      screenHeight: '',
      powerList: [],
      agreementChart: "",
      chartHeight: "100px",
      tableHeight: "100",
      timer1: null,
      agreementData: {
        type: "bar",  //bar pie
        thisDataIndex: 0,
        auto: true,
        yearList: ["2020年", "2019年", "全部"],
        typeList: ["合同", "中标通知书", "订单", "验收证明", "框架", "其他"],
        dataList: [{
          year: "2020年",
          data: [10, 20, 30, 40, 50, 60],
          sum: 210
        }, {
          year: "2019年",
          data: [30, 20, 90, 40, 20, 60],
          sum: 260
        }, {
          year: "全部",
          data: [40, 40, 120, 80, 70, 120],
          sum: 470
        }],
      },
      productChart: "",
      timer2: null,
      productData: {
        type: "bar",  //bar pie
        thisDataIndex: 0,
        auto: true,
        titleList: ["合同数", "台数", "金额"],
        typeList: ["台式机", "笔记本", "工作站", "平板", "XC", "合计"],
        dataList: [{
          title: "合同数",
          data: [10, 20, 30, 40, 50, 60],

        }, {
          title: "台数",
          data: [30, 20, 90, 40, 20, 60],

        }, {
          title: "金额",
          data: [40, 40, 120, 80, 70, 120],

        }],
      },
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

    drawAgreementCharts(data) {
      // 基于准备好的dom，初始化echarts实例
      //let myChart = this.$echarts.init(document.getElementById('agreementChart'))
      // 绘制图表
      this.agreementChart.clear();
      if (this.agreementData.type == "bar") {
        this.agreementChart.setOption({
          title: { text: data.year + "合同信息统计", left: 'center' },
          tooltip: {},
          xAxis: {
            data: this.agreementData.typeList
          },
          yAxis: {},
          series: [{
            name: '合同数量',
            type: 'bar',
            barMaxWidth: 50,//柱图宽度
            data: data.data
          }]
        });
      }
      if (this.agreementData.type == "pie") {
        var datas = [];
        var sum = 0;
        for (var i = 0; i < data.data.length; i++) {
          sum = sum + data.data[i];
        }
        for (var i = 0; i < data.data.length; i++) {
          datas.push({
            value: data.data[i], name: this.agreementData.typeList[i] + "(" + ((data.data[i] / sum * 100).toFixed(2)) + "%)"
          });

        }
        this.agreementChart.setOption({
          title: {
            text: data.year + "合同信息统计", left: 'center'
          },
          tooltip: {
            trigger: 'item'
          },
          color: ["#5470c6", "#91cc75", "#fac858", "#ee6666", "#73c0de", "#3ba272", "#fc8452", "#9a60b4", "#ea7ccc"],
          series: [
            {
              name: '合同数量',
              type: 'pie',
              radius: '50%',
              data: datas,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              },

            }
          ]
        });
      }
    },
    setAgreementDataType(command) {
      this.agreementData.type = command;
      this.drawAgreementCharts(this.agreementData.dataList[this.agreementData.thisDataIndex]);
      localStorage.setItem("agreementIndexData", JSON.stringify(this.agreementData));
    },
    setAgreementDataYear(command) {
      this.agreementData.thisDataIndex = command;
      this.drawAgreementCharts(this.agreementData.dataList[this.agreementData.thisDataIndex]);
      localStorage.setItem("agreementIndexData", JSON.stringify(this.agreementData));
    },
    setAgreementDataAuto(command) {
      this.agreementData.auto = command;
      if (command) {
        var that = this;
        this.timer1 = setInterval(function () {
          if (that.agreementData.thisDataIndex < that.agreementData.dataList.length - 1) {
            that.agreementData.thisDataIndex = that.agreementData.thisDataIndex + 1;
          } else {
            that.agreementData.thisDataIndex = 0;
          }
          that.drawAgreementCharts(that.agreementData.dataList[that.agreementData.thisDataIndex]);
        }, 5000)
      } else {
        clearInterval(this.timer1)
      }
      localStorage.setItem("agreementIndexData", JSON.stringify(this.agreementData));
    },
    drawProductCharts(data) {
      // 基于准备好的dom，初始化echarts实例
      //let myChart = this.$echarts.init(document.getElementById('agreementChart'))
      // 绘制图表
      this.productChart.clear();
      if (this.productData.type == "bar") {
        this.productChart.setOption({
          title: { text: "产品" + data.title + "信息统计", left: 'center' },
          tooltip: {},
          xAxis: {
            type: 'value',

          },
          yAxis: {
            type: 'category',
            data: this.productData.typeList.slice(0, this.productData.typeList.length - 1)
          },
          series: [{
            name: '合同数量',
            type: 'bar',
            barMaxWidth: 50,//柱图宽度
            data: data.data.slice(0, data.data.length - 1)
          }]
        });
      }
      if (this.productData.type == "pie") {
        var datas = [];
        var sum = data.data[data.data.length - 1];
        // for (var i = 0; i < data.data.length-1; i++) {
        //   sum = sum + data.data[i];
        // }
        for (var i = 0; i < data.data.length - 1; i++) {
          datas.push({
            value: data.data[i], name: this.productData.typeList[i] + "(" + ((data.data[i] / sum * 100).toFixed(2)) + "%)"
          });
        }
        this.productChart.setOption({
          title: {
            text: "产品" + data.title + "信息统计", left: 'center'
          },
          tooltip: {
            trigger: 'item'
          },
          color: ["#5470c6", "#91cc75", "#fac858", "#ee6666", "#73c0de", "#3ba272", "#fc8452", "#9a60b4", "#ea7ccc"],
          series: [
            {
              name: '合同数量',
              type: 'pie',
              radius: '50%',
              data: datas,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              },

            }
          ]
        });
      }
    },
    setProductDataType(command) {
      this.productData.type = command;
      this.drawProductCharts(this.productData.dataList[this.productData.thisDataIndex]);
      localStorage.setItem("productIndexData", JSON.stringify(this.productData));
    },
    setProductDataYear(command) {
      this.productData.thisDataIndex = command;
      this.drawProductCharts(this.productData.dataList[this.productData.thisDataIndex]);
      localStorage.setItem("productIndexData", JSON.stringify(this.productData));
    },
    setProductDataAuto(command) {
      this.productData.auto = command;
      if (command) {
        var that = this;
        this.timer2 = setInterval(function () {
          if (that.productData.thisDataIndex < that.productData.dataList.length - 1) {
            that.productData.thisDataIndex = that.productData.thisDataIndex + 1;
          } else {
            that.productData.thisDataIndex = 0;
          }
          that.drawProductCharts(that.productData.dataList[that.productData.thisDataIndex]);
        }, 5000)
      } else {
        clearInterval(this.timer2)
      }
      localStorage.setItem("productIndexData", JSON.stringify(this.productData));
    },
    // toLink(val) {
    //   this.$router.push({ path: val });
    // },
    exportAgreementData() {

      const loading = this.$loading(this.$store.state.loadingOption1);
      let url = '/index/exportAgreementData';
      this.axios({
        method: "post",
        url: url,
        responseType: "blob",
        headers: { token: this.token },
        data: {
          params: {
          }
        }
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
        link.setAttribute("download", "合同信息统计表.xlsx");
        document.body.appendChild(link);
        link.click();

        loading.close();
      });
    },
    exportProductData() {

      const loading = this.$loading(this.$store.state.loadingOption1);
      let url = '/index/exportProductData';
      this.axios({
        method: "post",
        url: url,
        responseType: "blob",
        headers: { token: this.token },
        data: {
          params: {
          }
        }
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
        link.setAttribute("download", "产品信息统计表.xlsx");
        document.body.appendChild(link);
        link.click();

        loading.close();
      });
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
  watch: {
    // screenWidth(val) {
    //   if(!this.timer) {
    //     this.screenWidth= val
    //     this.timer = true
    //     let _this = this
    //     setTimeout(function () {
    //       _this.timer = false
    //     }, 500)
    //   }
    //   // 这里可以添加修改时的方法
    //   this.windowHeight(val);
    // },
    //   screenWidth(val) {
    //   if(!this.timer) {
    //     this.screenWidth= val
    //     this.timer = true
    //     let _this = this
    //     setTimeout(function () {
    //       _this.timer = false
    //     }, 500)
    //   }
    //   // 这里可以添加修改时的方法
    //   this.windowHeight(val);
    // }
  },


  mounted() {
    this.screenWidth = document.body.clientWidth;
    this.screenHeight = document.body.clientHeight;
    this.chartHeight = ((this.screenHeight - 91) / 2 - 110) + "px"


    this.$nextTick(() => {
      this.tableHeight = ((this.screenHeight - 91) / 2 - 140)
    })
    this.agreementChart = this.$echarts.init(document.getElementById('agreementChart'))
    this.productChart = this.$echarts.init(document.getElementById('productChart'))

    var agreementIndexData = localStorage.getItem("agreementIndexData");
    if (agreementIndexData != "" && agreementIndexData != null && agreementIndexData != "null") {
      this.agreementData = JSON.parse(agreementIndexData)
    }
    var productIndexData = localStorage.getItem("productIndexData")
    if (productIndexData != "" && productIndexData != null && productIndexData != "null") {
      this.productData = JSON.parse(productIndexData)
    }
    const loading = this.$loading(this.$store.state.loadingOption1);

    this.axios
      .post(
        "/index/getAgreementData",
        {
          params: {
          }
        },
        { headers: { token: this.token } }
      )
      .then(res => {
        if (res.data.code === 200) {
          this.agreementData.yearList = res.data.data.yearList;
          this.agreementData.typeList = res.data.data.typeList;
          this.agreementData.dataList = res.data.data.dataList;




          this.drawAgreementCharts(this.agreementData.dataList[this.agreementData.thisDataIndex]);
          this.$nextTick(() => {
            this.agreementChart.resize();

          })
          if (this.agreementData.auto) {
            var that = this;
            this.timer1 = setInterval(function () {
              if (that.agreementData.thisDataIndex < that.agreementData.dataList.length - 1) {
                that.agreementData.thisDataIndex = that.agreementData.thisDataIndex + 1;
              } else {
                that.agreementData.thisDataIndex = 0;
              }
              that.drawAgreementCharts(that.agreementData.dataList[that.agreementData.thisDataIndex]);
            }, 5000)
          } else {
            clearInterval(this.timer1)
          }
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


    this.axios
      .post(
        "/index/getProductData",
        {
          params: {
          }
        },
        { headers: { token: this.token } }
      )
      .then(res => {
        if (res.data.code === 200) {
          this.productData.yearList = res.data.data.yearList;
          this.productData.typeList = res.data.data.typeList;
          this.productData.dataList = res.data.data.dataList;


          this.drawProductCharts(this.productData.dataList[this.productData.thisDataIndex]);
          this.$nextTick(() => {
            this.productChart.resize();
          })
          if (this.productData.auto) {
            var that = this;
            this.timer2 = setInterval(function () {
              if (that.productData.thisDataIndex < that.productData.dataList.length - 1) {
                that.productData.thisDataIndex = that.productData.thisDataIndex + 1;
              } else {
                that.productData.thisDataIndex = 0;
              }
              that.drawProductCharts(that.productData.dataList[that.productData.thisDataIndex]);
            }, 5000)
          } else {
            clearInterval(this.timer2)
          }
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



    window.onresize = () => {
      return (() => {
        this.screenWidth = document.body.clientWidth;
        this.screenHeight = document.body.clientHeight;
        this.chartHeight = ((this.screenHeight - 91) / 2 - 110) + "px"
        this.tableHeight = ((this.screenHeight - 91) / 2 - 140)
        this.$nextTick(() => {
          this.agreementChart.resize();
          this.productChart.resize();
        })

      })();
    };



    // const _this = this;
    // window.onresize = () => {
    //   return (() => {
    //     _this.drawAgreementCharts();
    //   })();
    // };


  },
  created: function () {


    this.token = localStorage.getItem("token");
    if (this.token == "" || this.token == null) {
      this.$router.push({ path: "/login" });
      return;
    }
    this.tokenCheck();

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
  margin: 20px;
  height: calc(100% - 40px);
}
.box-card {
  margin: 20px;
  height: 100%;
}
.index-col {
  padding: 30px;
}
.el-dropdown-link {
  cursor: pointer;
  color: #e1140a;
}
// .dlist {
//   height: 200px;
// }
// .dlist i {
//   font-size: 50px;
//   color: #606266;
//   display: block;
//   text-align: center;
//   padding: 20px;
// }
// .dlist:hover {
//   background-color: rgb(241, 241, 241);
// }
// .dlist:hover i {
//   color: #409eff !important;
// }
// .dlist:hover h3 {
//   color: #409eff !important;
// }
// .dlist h3 {
//   color: #606266;

//   text-align: center;
// }
// .index-title {
//   font-size: 32px;
//   text-align: center;
// }
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