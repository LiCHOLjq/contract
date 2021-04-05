import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

var state = {
  admin: null,

  fitting_list_formselect: {
    fittingId: '',
    fittingForm: '',
    fittingOriginComputer: '',
    fittingCurrentComputer: '',
    fittingState: '',

  },
  compuer_list_formselect: {
    computerId: '',
    computerType: '',
    factory: '',
    computerModel: '',
    computerName: '',
    computerText: '',
    computerState: '',
    openRow: '',
    page: 1
  },
  url: {
    baseURL: window.location.protocol + "//" + window.location.hostname,
    pythonURL: 'http://192.168.1.101',
    javaPort: "80/gas",
    pythonPort: "5000",
    dataUrl: ""
  },
  loadingOption1: {
    lock: true,
    text: '数据加载中...',
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.5)'
  },
  loadingOption2: {
    lock: true,
    text: '数据提交中...',
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.5)'
  }
}
var mutations = {
  setfitting_list_formselect (state, data) {
    state.fitting_list_formselect = data;
  },
  setcomputer_list_formselect (state, data) {
    state.compuer_list_formselect = data;
  },
  setadmin (state, data) {
    state.admin = data;
  },
}

const store = new Vuex.Store({
  state,
  mutations
})

export default store;