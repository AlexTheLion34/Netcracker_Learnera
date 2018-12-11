import Vue from 'vue'
import Router from 'vue-router'
import {store} from './store/index.js'

import Courses from './components/Courses.vue'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import User from './components/User.vue'
import UserHome from './components/UserHome.vue'
import Course from './components/Course.vue'
import CourseHome from './components/CourseHome.vue'
import CourseCreator from './components/CourseCreator.vue'
import CourseEditor from './components/CourseEditor.vue'
import CourseStudier from './components/CourseStudier.vue'
import CourseObserver from './components/CourseObserver.vue'
import Groups from './components/Groups.vue'
import Group from './components/Group.vue'
import GroupHome from './components/GroupHome.vue'
import GroupCreator from './components/GroupCreator.vue'
import GroupEditor from './components/GroupEditor.vue'
import Templates from './components/Templates.vue'
import TemplateViewer from './components/TemplateViewer.vue'
//import Test from './components/Test.vue'
import UserCreator from './components/UserCreator.vue'

const logoutComponent = {
  functional: true,
  render: (createElement, context) => {
    store.dispatch('account/logout');
    router.replace('login');
    return createElement('p', 'wait');
  }
}

const routes = [
  {path: '/logout', component: logoutComponent, meta: {title: 'Welcome | Learnera'}},
  {path: '/login', component: Login, meta: {title: 'Welcome | Learnera', isPublic: true}},
  {path: '/register', component: Register, meta: {title: 'Register', isPublic: true}},
  {path: '/create', component: UserCreator, meta: {title: 'Students Management'}},
  {path: '/user/:userIdStr', component: User, props: true,
    children: [
      {path: '', alias: '/home', component: UserHome, props: true, meta: {title: 'Home'}},
      {path: 'courses', component: Courses, props: true, meta: {title: 'Courses'}},
      {path: 'groups', component: Groups, props: true, meta: {title: 'Groups'}},
      {path: 'templates', component: Templates, props:true, meta: {title: 'Templates'}},
    ]
  },
  {path: '/create-course', component: CourseCreator, meta: {title: 'Course Management'}},
  {path: '/create-group', component: GroupCreator, meta: {title: 'Group Management'}},
  {path: '/create-template', component: TemplateViewer, meta: {title: 'Template Management'}},
  {path: '/course/:courseIdStr', component: Course, props: true, 
    children: [
      {path: '', component: CourseHome, props: true, meta: {title: 'Course Home'}},
      {path: 'edit', component: CourseEditor, props: true, meta: {title: 'Course Editor'}},
      {path: 'study', component: CourseStudier, props: (route) => ({
        moduleId: parseInt(route.query.module),
        lessonIdStr: route.query.lesson
      }), meta: {title: 'Studying'}},
      {path: 'observe', component: CourseObserver, props: (route) => ({
        moduleIdStr: route.query.module,
        lessonIdStr: route.query.lesson
      }), meta: {title: 'Course Obesrvation'}}
    ]
  },
  {path: '/group/:groupIdStr', component: Group, props: true,
    children: [
      {path: '', component: GroupHome, props: true, meta: {title: 'Group Home'}},
      {path: 'edit', component: GroupEditor, props: true, meta: {title: 'Group Editor'}}
    ]},
  {path: '/template/:templateIdStr', component: TemplateViewer, props: (route) => ({
    templateIdStr: route.params.templateIdStr,
    readOnly: true
  }), meta: {title: 'Template Home'}},
  {path: '/template/:templateIdStr/edit', component: TemplateViewer, props: true, meta: {title: 'Template Editor'}},
]

Vue.use(Router)

console.log('Base URL:', process.env.BASE_URL)
console.log(process.env);
const isProduction = process.env.NODE_ENV === 'production';

export const router = new Router({
  base: isProduction ? process.env.BASE_URL + 'front' : null,
  mode: 'history',
  routes
})

router.beforeEach((to, from, next) => {
  document.title = to.meta.title
  if (to.fullPath === '/')
  {
    const loggedIn = store.state.account.status.loggedIn;

    if (loggedIn) {
      next(`/user/${store.state.account.user.id}`);
    } else {
      next('/login');
    }
  } else {
    next();
  }
})

router.beforeEach((to, from, next) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const loggedIn = store.state.account.status.loggedIn;

  if (to.matched.some(record => !record.meta.isPublic)) {
    // this route requires auth, check if logged in
    // if not, redirect to login page.
    if (!loggedIn) {
      const query = to.fullPath === '/' ? {} : {redirect: to.fullPath};
      next({
        path: '/login',
        query
      })
    } else {
      next()
    }
  } else {
    next() // make sure to always call next()!
  }
})
