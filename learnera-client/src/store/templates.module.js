import {templateService} from '../services'
import generalMutations from '../helpers/store-mutations'
import Vue from 'vue';

const state = {
  items: []
}

const getters = {
  templatesByTeacherId: (state) => (teacherId) => {
    return state.items.filter(i => i.teacher === teacherId);
  }
}

const actions = {
  create({commit}, template) {
    return new Promise((resolve, reject) => {
      commit('createRequest', template);

      templateService.create(template)
        .then(t => {
          commit('createSuccess', t);
          resolve(t);
        })
        .catch(e => {
          commit('createFailure', e);
          reject(e);
        });
    });
  },
  get({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getRequest', id);

      templateService.getById(id)
        .then(c => {
          commit('getSuccess', c);
          resolve(c);
        })
        .catch(e => {
          commit('getFailure', {id, error: e.toString()});
          reject(e);
        });
    });
  },
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
  delete({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('deleteRequest', id);

      templateService.delete(id)
        .then(() => {
          commit('deleteSuccess', id);
          resolve();
        })
        .catch(e => {
          commit('deleteFailure', {id, error: e.toString()});
          reject(e);
        });
    });
  }
}

const mutations = {
  ...generalMutations,
  getByTeacherIdRequest(state) {
  },
  getByTeacherIdSuccess(state, {templates}) {
    console.log(templates);
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