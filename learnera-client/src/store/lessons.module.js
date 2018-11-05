import {lessonService} from '../services'
import Vue from 'vue';

import crudModule from './crud.module'
const {state, actions: crudActions, mutations: crudMutations} = crudModule(lessonService);

const actions = {
  ...crudActions,
  getByWeekId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByWeekIdRequest');

      lessonService.getByWeekId(id)
        .then(lessons => {
          commit('getByWeekIdSuccess', lessons);
          resolve(lessons);
        })
        .catch(e => {
          commit('getByWeekIdFailure', e);
          reject(e);
        });
    });
  }
}

const mutations = {
  ...crudMutations,
  getByWeekIdRequest(state) {
  },
  getByWeekIdSuccess(state, lessons) {
    lessons.forEach(lesson => {
      const idx = state.items.findIndex(i => i.id === i.id);
      if (idx === -1) {
        state.items.push(lesson);
      } else {
        Vue.set(state.items, idx, lesson);
      }
    });
  },
  getByWeekIdFailure(state, id) {
  }
}

export const lessons = {
  namespaced: true,
  state,
  actions,
  mutations
}