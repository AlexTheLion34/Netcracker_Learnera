import {groupService} from '../services'
import Vue from 'vue';

import crudModule from './crud.module'
const {state, actions: crudActions, mutations: crudMutations} = crudModule(groupService);

const actions = {
  ...crudActions,
  getByCuratorId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByCuratorIdRequest');

      groupService.getByCuratorId(id)
        .then(courses => {
          commit('getByCuratorIdSuccess', courses);
          resolve(courses);
        })
        .catch(e => {
          commit('getByCuratorIdFailure', e);
          reject(e);
        });
    });
  }
}

const mutations = {
  ...crudMutations,
  getByCuratorIdRequest(state) {
  },
  getByCuratorIdSuccess(state, courses) {
    courses.forEach(course => {
      const idx = state.items.findIndex(c => c.id === course.id);
      if (idx === -1) {
        state.items.push(course);
      } else {
        Vue.set(state.items, idx, course);
      }
    });
  },
  getByCuratorIdFailure(state, id) {
  }
}

export const groups = {
  namespaced: true,
  state,
  actions,
  mutations
}