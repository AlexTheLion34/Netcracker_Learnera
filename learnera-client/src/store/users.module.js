import {userService} from '../services';
import generalMutations from '../helpers/store-mutations'

const state = {
  all: {}
};

const actions = {
  getAll({commit}) {
    commit('getAllRequest');

    userService.getAll()
      .then(
        users => commit('getAllSuccess', users),
        error => commit('getAllFailure', error)
      );
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
