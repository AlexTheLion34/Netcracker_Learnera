import {templateService} from '../services'
import Vue from 'vue';

import crudModule from './crud.module'
const {state, actions: crudActions, mutations: crudMutations} = crudModule(templateService);

const getters = {
  templatesByTeacherId: (state) => (teacherId) => {
    return state.items.filter(i => i.teacher === teacherId);
  }
}

const actions = {
  ...crudActions,
  getByTeacherId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByTeacherIdRequest');

      templateService.getByTeacherId(id)
        .then(templates => {
          commit('getByTeacherIdSuccess', {teacherId: id, templates});
          resolve(templates);
        })
        .catch(e => {
          commit('getByTeacherIdFailure', e);
          reject(e);
        });
    });
  },
  
}

const mutations = {
  ...crudMutations,
  getByTeacherIdRequest(state) {
  },
  getByTeacherIdSuccess(state, {templates}) {
    templates.forEach(template => {
      const idx = state.items.findIndex(t => t.id === template.id);
      if (idx === -1) {
        state.items.push(template);
      } else {
        Vue.set(state.items, idx, template);
      }
    });
  },
  getByTeacherIdFailure(state, id) {
  }

}

export const templates = {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}