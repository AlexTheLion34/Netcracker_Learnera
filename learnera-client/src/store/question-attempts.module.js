import {questionAttemptService} from '../services'
import Vue from 'vue';

import crudModule from './crud.module'
const {state, actions: crudActions, mutations: crudMutations} = crudModule(questionAttemptService);

const actions = {
  ...crudActions,
  getByQuestionId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByQuestionIdRequest');

      questionAttemptService.getByQuestionId(id)
        .then(questionAttempts => {
          commit('getByQuestionIdSuccess', questionAttempts);
          resolve(questionAttempts);
        })
        .catch(e => {
          commit('getByQuestionIdFailure', e);
          reject(e);
        });
    });
  }
}

const mutations = {
  ...crudMutations,
  getByQuestionIdRequest(state) {
  },
  getByQuestionIdSuccess(state, questionAttempts) {
    questionAttempts.forEach(questionAttempt => {
      const idx = state.items.findIndex(i => i.id === i.id);
      if (idx === -1) {
        state.items.push(questionAttempt);
      } else {
        Vue.set(state.items, idx, questionAttempt);
      }
    });
  },
  getByQuestionIdFailure(state, id) {
  }
}

export const questionAttempts = {
  namespaced: true,
  state,
  actions,
  mutations
}