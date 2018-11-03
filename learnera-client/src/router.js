import Vue from 'vue'
import Router from 'vue-router'
import {store} from './store/index.js'

const logoutComponent = {
  functional: true,
  render: (createElement, context) => {
    store.dispatch('account/logout');
    router.replace('/');
    return createElement('p', 'wait');
  }
}

const routerOptions = [
  {path: '/login', component: 'Login', meta: {isPublic: true}},
  {path: '/register', component: 'Register', meta: {isPublic: true}},
  {path: '/profile', component: 'Profile', alias: '/'},
  {path: '/course_list', component: 'CourseList'}
]

const routes = [ 
  {path: '/logout', component: logoutComponent},
  ...routerOptions.map(route => {
    return {
      ...route,
      component: () => import(`@/components/${route.component}.vue`)
    }
  })
]

Vue.use(Router)

export const router = new Router({
  mode: 'history',
  routes
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
