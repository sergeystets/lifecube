import Vue from 'vue'
import Router from 'vue-router'
import Dashboard from '@/components/Dashboard'
import Home from '@/components/Home'
import AddNewSubmission from "@/components/AddNewSubmission";

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {path: '/', component: Home},
    {path: '/dashboard', component: Dashboard},
    {path: '/submission/add', component: AddNewSubmission},
    {path: '*', redirect: '/'}
  ]
});

export default router;