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
  },
  scoreAttempts({commit}, attempts) {
    return new Promise((resolve, reject) => {
      commit('scoreAttemptsRequest');

      questionAttemptService.scoreAttempts(attempts)
        .then(questionAttempts => {
          commit('scoreAttemptsSuccess', questionAttempts);
          resolve(questionAttempts);
        })
        .catch(e => {
          commit('scoreAttemptsFailure', e);
          reject(e);
        });
    });
  },
  getLatestUserWeekAttempts({commit}, {userId, weekId}) {
    return new Promise((resolve, reject) => {
      commit('getLatestUserWeekAttemptsRequest', {weekId, userId});

      questionAttemptService.findLatestUserWeekAttempts(userId, weekId)
        .then(questionAttempts => {
          commit('getLatestUserWeekAttemptsSuccess', questionAttempts);
          resolve(questionAttempts);
        })
        .catch(e => {
          commit('getLatestUserWeekAttemptsFailure', e);
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
  },
  scoreAttemptsRequest(state) {

  },
  scoreAttemptsSuccess(state, questionAttempts) {
    questionAttempts.forEach(questionAttempt => {
      const idx = state.items.findIndex(i => i.id === i.id);
      if (idx === -1) {
        state.items.push(questionAttempt);
      } else {
        Vue.set(state.items, idx, questionAttempt);
      }
    });
  },
  scoreAttemptsFailure(state, e) {
  },
  getLatestUserWeekAttemptsRequest(state, {weekId, userId}) {

  },
  getLatestUserWeekAttemptsSuccess(state, questionAttempts) {
    questionAttempts.forEach(questionAttempt => {
      const idx = state.items.findIndex(i => i.id === i.id);
      if (idx === -1) {
        state.items.push(questionAttempt);
      } else {
        Vue.set(state.items, idx, questionAttempt);
      }
    });
  },
  getLatestUserWeekAttemptsFailure(state, e) {

  }
}

export const questionAttempts = {
  namespaced: true,
  state,
  actions,
  mutations
}