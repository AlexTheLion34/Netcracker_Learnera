import Vue from 'vue';
import Vuex from 'vuex';

import {alert} from './alert.module';
import {account} from './account.module';
import {courses} from './courses.module';
import {groups} from './groups.module';
import {lessons} from './lessons.module';
import {messages} from './messages.module';
import {questionAttempts} from './question-attempts.module'
import {templates} from './templates.module';
import {users} from './users.module';

const groupMessages = messages('/api/group-messages');
const lessonMessages = messages('/api/lesson-messages');


Vue.use(Vuex);

export const store = new Vuex.Store({
  modules: {
    alert,
    account,
    courses,
    groups,
    groupMessages,
    lessons,
    lessonMessages,
    questionAttempts,
    templates,
    users
  }
});