import {groupService} from '../services'
import Vue from 'vue';

import crudModule from './crud.module'
const {state, actions: crudActions, mutations: crudMutations} = crudModule(groupService);

const actions = {
  ...crudActions,
  getByCuratorId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByCuratorIdRequest', id);

      groupService.getByCuratorId(id)
        .then(groups => {
          commit('getByCuratorIdSuccess', groups);
          resolve(groups);
        })
        .catch(e => {
          commit('getByCuratorIdFailure', {id, error: e});
          reject(e);
        });
    });
  },
  getByCourseId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByCourseIdRequest', id);

      groupService.getByCourseId(id)
        .then(groups => {
          commit('getByCourseIdSuccess', groups);
          resolve(groups);
        })
        .catch(e => {
          commit('getByCourseIdFailure', {id, error: e});
          reject(e);
        });
    });
  }
}

const mutations = {
  ...crudMutations,
  getByCuratorIdRequest(state, id) {
    console.log('Getting groups with curator id: ', id)
  },
  getByCuratorIdSuccess(state, groups) {
    console.log('Got groups: ', groups);
    groups.forEach(group => {
      const idx = state.items.findIndex(i => i.id === i.id);
      if (idx === -1) {
        state.items.push(group);
      } else {
        Vue.set(state.items, idx, group);
      }
    });
  },
  getByCuratorIdFailure(state, {id, error}) {
    console.error('Failed to get item with id: ', id);
    console.error('Error: ', error);
  },
  getByCourseIdRequest(state, id) {
    console.log('Getting groups with course id: ', id)
  },
  getByCourseIdSuccess(state, groups) {
    console.log('Got groups: ', groups);
    groups.forEach(group => {
      const idx = state.items.findIndex(i => i.id === group.id);
      if (idx === -1) {
        state.items.push(group);
      } else {
        Vue.set(state.items, idx, group);
      }
    });
  },
  getByCourseIdFailure(state, {id, error}) {
    console.error('Failed to get item with id: ', id);
    console.error('Error: ', error);
  }
}

export const groups = {
  namespaced: true,
  state,
  actions,
  mutations
}