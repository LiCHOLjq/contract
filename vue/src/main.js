import Vue from 'vue'
import App from './App.vue'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/element-variables.scss'

import router from './router/router.js';
import axios from 'axios';
import Vueaxios from 'vue-axios';

import echarts from 'echarts'
Vue.prototype.$echarts = echarts
import 'echarts/theme/vintage.js'

import md5 from 'js-md5';
Vue.prototype.$md5 = md5;

import Clipboard from 'vue-clipboard2';
Vue.use(Clipboard)


Vue.use(ElementUI);
Vue.prototype.axios = axios
Vue.use(Vueaxios, axios);

var baseURLStr = window.location.protocol + "//" + window.location.hostname;
if (window.location.port != "") {
  baseURLStr = baseURLStr + ":" + window.location.port;
}
baseURLStr = baseURLStr + "/contract/";
//baseURLStr = "https://apps.neu.edu.qizhiqiang.com/promotion/"
//baseURLStr = "http://192.168.3.3/promotion/"
//baseURLStr = "http://192.168.0.2/promotion/"
//baseURLStr = "http://localhost/contract/"
axios.defaults.baseURL = process.env.NODE_ENV === 'development' ? baseURLStr : baseURLStr


new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
