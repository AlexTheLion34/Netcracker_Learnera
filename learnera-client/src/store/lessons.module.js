import {lessonService} from '../services'
import Vue from 'vue';

import crudModule from './crud.module'
const {state, actions: crudActions, mutations: crudMutations} = crudModule(lessonService);

const getters = {
  lessonsByWeekId: (state) => (weekId) => {
    return state.items.filter(i => i.week === weekId);
  }
}

const actions = {
  ...crudActions,
  getByWeekId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByWeekIdRequest', id);

      lessonService.getByWeekId(id)
        .then(lessons => {
          commit('getByWeekIdSuccess', lessons);
          resolve(lessons);
        })
        .catch(e => {
          commit('getByWeekIdFailure', {id, error: e});
          reject(e);
        });
    });
  }
}

const mutations = {
  ...crudMutations,
  getByWeekIdRequest(state, id) {
    console.log('Getting lessons with week id: ', id)
  },
  getByWeekIdSuccess(state, lessons) {
    console.log('Got lessons: ', lessons);
    lessons.forEach(lesson => {
      const idx = state.items.findIndex(i => i.id === lesson.id);
      if (idx === -1) {
        state.items.push(lesson);
      } else {
        Vue.set(state.items, idx, lesson);
      }
    });
  },
  getByWeekIdFailure(state, {id, error}) {
    console.error('Failed to get item with id: ', id);
    console.error('Error: ', error);
  }
}

export const lessons = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}