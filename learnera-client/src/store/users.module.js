import {userService} from '../services';
import Vue from 'vue';

import crudModule from './crud.module'
const {state, actions: crudActions, mutations: crudMutations} = crudModule(userService);

const actions = {
  ...crudActions,
  getByStudyGroupId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByStudyGroupIdRequest');

      userService.getByStudyGroupId(id)
        .then(users => {
          commit('getByStudyGroupIdSuccess', users);
          resolve(users);
        })
        .catch(e => {
          commit('getByStudyGroupIdFailure', e);
          reject(e);
        });
    });
  },
  getAllStudents({commit}) {
    return new Promise((resolve, reject) => {
      commit('getAllStudentsRequest');

      userService.getAllStudents()
        .then(students => {
          commit('getAllStudentsSuccess', students);
          resolve(students);
        })
        .catch(e => {
          commit('getAllStudentsFailure', e);
          reject(e);
        });
    });
  }
};

const mutations = {
  ...crudMutations,
  getByStudyGroupIdRequest(state) {
  },
  getByStudyGroupIdSuccess(state, users) {
    console.log('Got students: ', users);
    state.items = []
    users.forEach(user => {
      state.items.push(user);
    });
    // users.forEach(user => {
    //   const idx = state.items.findIndex(i => i.id === i.id);
    //   if (idx === -1) {
    //     state.items.push(user);
    //   } else {
    //     Vue.set(state.items, idx, user);
    //   }
    // });
  },
  getByStudyGroupIdFailure(state, id) {
  },
  getAllStudentsRequest(state) {
  },
  getAllStudentsSuccess(state, students) {
    console.log('Got students: ', students);
    state.items = []
    students.forEach(student => {
      state.items.push(student);
    });
  },
  getAllStudentsFailure(state, id) {
  },
};

export const users = {
  namespaced: true,
  state,
  actions,
  mutations
};
