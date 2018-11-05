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
  }
};

const mutations = {
  ...crudMutations,
  getByStudyGroupIdRequest(state) {
  },
  getByStudyGroupIdSuccess(state, users) {
    users.forEach(user => {
      const idx = state.items.findIndex(i => i.id === i.id);
      if (idx === -1) {
        state.items.push(user);
      } else {
        Vue.set(state.items, idx, user);
      }
    });
  },
  getByStudyGroupIdFailure(state, id) {
  }
};

export const users = {
  namespaced: true,
  state,
  actions,
  mutations
};
