import {userService} from '../services';
import generalMutations from '../helpers/store-mutations'

const state = {
  items: []
};

const actions = {
  create({commit}, user) {
    return new Promise((resolve, reject) => {
      commit('createRequest', user);

      userService.create(user)
        .then(u => {
          commit('createSuccess', u);
          resolve(u);
        })
        .catch(e => {
          commit('createFailure', e);
          reject(e);
        });
    });
  },
  getAll({commit}) {
    commit('getAllRequest');

    userService.getAll()
      .then(
        users => commit('getAllSuccess', users),
        error => commit('getAllFailure', error)
      );
  },
  get({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getRequest', id);

      userService.getById(id)
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
    commit('deleteRequest', id);

    userService.delete(id)
      .then(
        () => commit('deleteSuccess', id),
        error => commit('deleteFailure', {id, error: error.toString()})
      );
  }
};

const mutations = {
  ...generalMutations
};

export const users = {
  namespaced: true,
  state,
  actions,
  mutations
};
