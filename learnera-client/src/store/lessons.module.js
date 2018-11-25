import {lessonService} from '../services'
import Vue from 'vue';

import crudModule from './crud.module'
const {state, actions: crudActions, mutations: crudMutations} = crudModule(lessonService);

const getters = {
  lessonsByModuleId: (state) => (moduleId) => {
    return state.items.filter(i => i.module === moduleId);
  }
}

const actions = {
  ...crudActions,
  getByModuleId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByModuleIdRequest', id);

      lessonService.getByModuleId(id)
        .then(lessons => {
          commit('getByModuleIdSuccess', lessons);
          resolve(lessons);
        })
        .catch(e => {
          commit('getByModuleIdFailure', {id, error: e});
          reject(e);
        });
    });
  }
}

const mutations = {
  ...crudMutations,
  getByModuleIdRequest(state, id) {
    console.log('Getting lessons with module id: ', id)
  },
  getByModuleIdSuccess(state, lessons) {
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
  getByModuleIdFailure(state, {id, error}) {
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