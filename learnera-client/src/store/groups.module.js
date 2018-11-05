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
        .then(groups => {
          commit('getByCuratorIdSuccess', groups);
          resolve(groups);
        })
        .catch(e => {
          commit('getByCuratorIdFailure', e);
          reject(e);
        });
    });
  },
  getByCourseId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByCourseIdRequest');

      groupService.getByCourseId(id)
        .then(groups => {
          commit('getByCourseIdSuccess', groups);
          resolve(groups);
        })
        .catch(e => {
          commit('getByCourseIdFailure', e);
          reject(e);
        });
    });
  }
}

const mutations = {
  ...crudMutations,
  getByCuratorIdRequest(state) {
  },
  getByCuratorIdSuccess(state, groups) {
    groups.forEach(group => {
      const idx = state.items.findIndex(i => i.id === i.id);
      if (idx === -1) {
        state.items.push(group);
      } else {
        Vue.set(state.items, idx, group);
      }
    });
  },
  getByCuratorIdFailure(state, id) {
  },
  getByCourseIdRequest(state) {
  },
  getByCourseIdSuccess(state, groups) {
    groups.forEach(group => {
      const idx = state.items.findIndex(i => i.id === group.id);
      if (idx === -1) {
        state.items.push(group);
      } else {
        Vue.set(state.items, idx, group);
      }
    });
  },
  getByCourseIdFailure(state, id) {
  }
}

export const groups = {
  namespaced: true,
  state,
  actions,
  mutations
}