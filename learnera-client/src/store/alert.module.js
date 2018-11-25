let id = 2;

const state = {
  id: 1,
  type: null,
  message: null
};

const actions = {
  success({commit}, message) {
    commit('success', message);
  },
  error({commit}, message) {
    commit('error', message);
  },
  clear({commit}) {
    commit('clear');
  }
};

const mutations = {
  success(state, message) {
    state.id = id++;
    state.type = 'success';
    state.message = message;
  },
  error(state, message) {
    state.id = id++;
    state.type = 'error';
    state.message = message;
  },
  clear(state) {
    state.id = id++;
    state.type = null;
    state.message = null;
  }
};

export const alert = {
  namespaced: true,
  state,
  actions,
  mutations
};
