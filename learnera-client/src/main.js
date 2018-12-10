import Vue from 'vue'
import App from './App.vue'
import {store} from './store';
import {router} from './router'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import axios from './helpers/axios-base'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios)
Vue.use(Vuetify)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
