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
  getLatestUserModuleAttempts({commit}, {userId, moduleId}) {
    return new Promise((resolve, reject) => {
      commit('getLatestUserModuleAttemptsRequest', {moduleId, userId});

      questionAttemptService.findLatestUserModuleAttempts(userId, moduleId)
        .then(questionAttempts => {
          commit('getLatestUserModuleAttemptsSuccess', questionAttempts);
          resolve(questionAttempts);
        })
        .catch(e => {
          commit('getLatestUserModuleAttemptsFailure', e);
          reject(e);
        });
    });
  },
  getAllCourseAttempts({commit}, courseId) {
    return new Promise((resolve, reject) => {
      commit('getAllCourseAttemptsRequest', courseId);

      questionAttemptService.findAllCourseAttempts(courseId)
        .then(questionAttempts => {
          commit('getAllCourseAttemptsSuccess', questionAttempts);
          resolve(questionAttempts);
        })
        .catch(e => {
          commit('getAllCourseAttemptsFailure', e);
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
  getLatestUserModuleAttemptsRequest(state, {moduleId, userId}) {

  },
  getLatestUserModuleAttemptsSuccess(state, questionAttempts) {
    questionAttempts.forEach(questionAttempt => {
      const idx = state.items.findIndex(i => i.id === i.id);
      if (idx === -1) {
        state.items.push(questionAttempt);
      } else {
        Vue.set(state.items, idx, questionAttempt);
      }
    });
  },
  getLatestUserModuleAttemptsFailure(state, e) {

  },
  getAllCourseAttemptsRequest(state, courseId) {

  },
  getAllCourseAttemptsSuccess(state, questionAttempts) {
    questionAttempts.forEach(questionAttempt => {
      const idx = state.items.findIndex(i => i.id === i.id);
      if (idx === -1) {
        state.items.push(questionAttempt);
      } else {
        Vue.set(state.items, idx, questionAttempt);
      }
    });
  },
  getAllCourseAttemptsFailure(state, e) {

  }
}

export const questionAttempts = {
  namespaced: true,
  state,
  actions,
  mutations
}