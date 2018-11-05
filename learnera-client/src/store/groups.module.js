import {groupService} from '../services'
import generalMutations from '../helpers/store-mutations'
import Vue from 'vue';

const state = {
  items: []
}

const actions = {
  create({commit}, group) {
    return new Promise((resolve, reject) => {
      commit('createRequest', group);

      groupService.create(group)
        .then(g => {
          commit('createSuccess', g);
          resolve(g);
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

      groupService.getById(id)
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
  getByCuratorId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByCuratorIdRequest');

      groupService.getByCuratorId(id)
        .then(courses => {
          commit('getByCuratorIdSuccess', courses);
          resolve(courses);
        })
        .catch(e => {
          commit('getByCuratorIdFailure', e);
          reject(e);
        });
    });
  },
  delete({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('deleteRequest', id);

      groupService.delete(id)
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
  getByCuratorIdRequest(state) {
  },
  getByCuratorIdSuccess(state, courses) {
    courses.forEach(course => {
      const idx = state.items.findIndex(c => c.id === course.id);
      if (idx === -1) {
        state.items.push(course);
      } else {
        Vue.set(state.items, idx, course);
      }
    });
  },
  getByCuratorIdFailure(state, id) {
  }
}

export const groups = {
  namespaced: true,
  state,
  actions,
  mutations
}