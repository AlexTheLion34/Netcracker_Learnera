import Vue from 'vue';
import Vuex from 'vuex';

import {alert} from './alert.module';
import {account} from './account.module';
import {users} from './users.module';
import {courses} from './courses.module';
import {groups} from './groups.module';
import {templates} from './templates.module';

Vue.use(Vuex);

export const store = new Vuex.Store({
  modules: {
    alert,
    account,
    users,
    courses,
    groups,
    templates
  }
});