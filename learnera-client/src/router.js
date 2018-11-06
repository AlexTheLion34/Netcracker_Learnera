import Vue from 'vue'
import Router from 'vue-router'
import {store} from './store/index.js'

import Courses from './components/Courses.vue'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import User from './components/User.vue'
import UserHome from './components/UserHome.vue'
import Course from './components/Course.vue'
import CourseCreator from './components/CourseCreator.vue'
import Groups from './components/Groups.vue'
import {groups} from './store/groups.module.js';

const logoutComponent = {
  functional: true,
  render: (createElement, context) => {
    store.dispatch('account/logout');
    router.replace('/');
    return createElement('p', 'wait');
  }
}

const routes = [
  {path: '/logout', component: logoutComponent},
  {path: '/login', component: Login, meta: {isPublic: true}},
  {path: '/register', component: Register, meta: {isPublic: true}},
  {path: '/user/:userIdStr', component: User, props: true,
    children: [
      {path: '', alias: '/home', component: UserHome, props: true},
      {path: 'courses', component: Courses, props: true},
      {path: 'create-course', component: CourseCreator, props: true},
      {path: 'groups', component: Groups, props: true}
    ]
  },
  {path: '/course/:courseIdStr', component: Course, props: true},
]

Vue.use(Router)

export const router = new Router({
  mode: 'history',
  routes
})

router.beforeEach((to, from, next) => {
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
