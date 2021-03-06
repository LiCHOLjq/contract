import Vue from 'vue';

//配置路由
import VueRouter from 'vue-router';
Vue.use(VueRouter);


//1.创建组件



import Home from '../components/Home.vue';
import Login from '../components/Login.vue';
import Index from '../components/Index.vue';
import Admin from '../components/system/Admin.vue';
import Dictionary from '../components/system/Dictionary.vue';
import Log from '../components/system/Log.vue';
import AgreementList from '../components/agreement/AgreementList.vue';
import AgreementUserList from '../components/agreement/AgreementUserList.vue';
import Agreement from '../components/agreement/Agreement.vue';
import Share_Admin from '../components/agreement/Share.vue';

import Share_Login from '../components/share/Login.vue';
import Share_Error from '../components/share/Error.vue';
import Share_Items from '../components/share/Share.vue';
// import Role from '../components/Role.vue';
// 
// import Group from '../components/system/Group.vue';
// import AdminCheck from '../components/system/AdminCheck.vue';
// import ProjectList from '../components/plan/ProjectList.vue';
// import Project from '../components/plan/Project.vue';
// import Task from '../components/plan/Task.vue';
// import Statistical from '../components/plan/Statistical.vue';
// import News from '../components/News.vue';
// import News_Edit from '../components/plan/News.vue';
// import News_Phone from '../components/News_Phone.vue';
// import StatisticalManager from '../components/plan/StatisticalManager.vue';
//2.配置路由   注意：名字

const routes = [

    {
        path: '/home',  //登录后进入的主页面
        component: Home,
        redirect: '/home/index',
        children: [
            { path: 'index', component: Index },
            { path: 'admin', component: Admin },
            { path: 'dictionary', component: Dictionary },
            { path: 'log', component: Log },
            { path: 'agreement/list', component: AgreementList },
            { path: 'agreement/user/list', component: AgreementUserList },
            { path: 'agreement/details', component: Agreement },
            { path: 'agreement/details/:agreementId', component: Agreement },
            { path: 'share', component: Share_Admin },
            // { path: 'group', component: Group },
            // { path: 'admin/apply', component: AdminCheck },
            // { path: 'project/list', component: ProjectList },
            // { path: 'project/details', component: Project },
            // { path: 'project/details/:projectId', component: Project },
            // { path: 'project/task/add/:projectId', component: Task },
            // { path: 'project/task/:taskId', component: Task },
            // { path: 'project/news/add/:projectId', component: News_Edit },
            // { path: 'project/news/:newsId', component: News_Edit },
            // { path: 'project/statistical', component: Statistical },
            // { path: 'project/source', component: StatisticalManager },

        ]
    },

    { path: '/login', component: Login, },   //登录页面路由
    { path: '/share/login/:shareId', component: Share_Login, },   //登录页面路由
    { path: '/share/items/:shareId', component: Share_Items, },   //登录页面路由
    { path: '/share/error', component: Share_Error, },   //登录页面路由
    // { path: '/role', component: Role, },   //登录页面路由
    { path: '*', redirect: '/home' },   /*默认跳转路由*/
    // { path: '/news/:newsId', component: News, },
]


//3.实例化VueRouter  注意：名字

const router = new VueRouter({
    mode: 'history',   /*hash模式改为history*/
    base: '/agreement',
    routes // （缩写）相当于 routes: routes
})


//5 <router-view></router-view> 放在 App.vue

export default router;






