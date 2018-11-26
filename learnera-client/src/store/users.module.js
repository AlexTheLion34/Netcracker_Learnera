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
  getByCourseId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByCourseIdRequest');

      userService.getByCourseId(id)
        .then(users => {
          commit('getByCourseIdSuccess', users);
          resolve(users);
        })
        .catch(e => {
          commit('getByCourseIdFailure', e);
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
    users.forEach(user => {
      const idx = state.items.findIndex(i => i.id === user.id);
      if (idx === -1) {
        state.items.push(user);
      } else {
        Vue.set(state.items, idx, user);
      }
    });
  },
  getByStudyGroupIdFailure(state, id) {
  },
  getByCourseIdRequest(state) {

  },
  getByCourseIdSuccess(state, users) {
    users.forEach(user => {
      const idx = state.items.findIndex(i => i.id === user.id);
      if (idx === -1) {
        state.items.push(user);
      } else {
        Vue.set(state.items, idx, user);
      }
    });
  },
  getByCourseIdFailure(state, e) {

  },
  getAllStudentsRequest(state) {
  },
  getAllStudentsSuccess(state, students) {
    console.log('Got students: ', students);
    students.forEach(student => {
      const idx = state.items.findIndex(i => i.id === student.id);
      if (idx === -1) {
        state.items.push(student);
      } else {
        Vue.set(state.items, idx, student);
      }
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
