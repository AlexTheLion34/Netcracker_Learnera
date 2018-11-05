import {groupService} from '../services'
import generalMutations from '../helpers/store-mutations'

const state = {
  all: {}
}

const actions = {
  getAll({commit}) {
    return new Promise((resolve, reject) => {
      commit('getAllRequest');

      groupService.getAll()
        .then(c => {
          commit('getAllSuccess', c);
          resolve(c);
        })
        .catch(e => {
          commit('getAllFailure', e);
          reject(e);
        });
    });
  },
  get({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getRequest');

      groupService.get(id)
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
  ...generalMutations
}

export const groups = {
  namespaced: true,
  state,
  actions,
  mutations
}