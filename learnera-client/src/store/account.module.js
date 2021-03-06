import {userService} from '../services';
import {router} from '../router';

let user = null;
try {
  user = JSON.parse(localStorage.getItem('user'));
} catch (e) {
  user = null;
}
const state = user
  ? {status: {loggedIn: true}, user}
  : {status: {loggedIn: false}, user: null};

const actions = {
  login({dispatch, commit}, {email, password}) {
    commit('loginRequest', {email});
    
    userService.login(email, password)
      .then(
        user => {
          commit('loginSuccess', user);
          router.push('/');
        })
      .catch(error => {
        dispatch('alert/error', error.response.status === 500 ? 'Could not connect' : 'Invalid email or password', 
          {root: true});
      });
  },
  logout({commit}) {
    userService.logout();
    commit('logout');
  },
  register({dispatch, commit}, user) {
    commit('registerRequest', user);
    
    userService.register(user)
      .then(
        user => {
          commit('registerSuccess', user);
          router.push('/login');
          setTimeout(() => {
            // display success message after route change completes
            dispatch('alert/success', 'Registration successful', {root: true});
          })
        },
        error => {
          commit('registerFailure', error);
          dispatch('alert/error', error.response.status === 500 ? 'Could not connect' : error.response.data.message, {root: true});
        }
      );
  },
  create({dispatch, commit}, user, creatorId) {
    commit('registerRequest', user);
    
    userService.register(user)
      .then(
        user => {
          commit('createSuccess', user);
          setTimeout(() => {
            // display success message after route change completes
            dispatch('alert/success', 'Creation successful', {root: true});
          })
        },
        error => {
          commit('createFailure', error);
          dispatch('alert/error', error, {root: true});
        }
      );
  }
};

const mutations = {
  loginRequest(state, user) {
    state.status = {loggingIn: true};
    state.user = user;
  },
  loginSuccess(state, user) {
    state.status = {loggedIn: true};
    state.user = user;
  },
  loginFailure(state) {
    state.status = {loggedIn: false};
    state.user = null;
  },
  logout(state) {
    state.status = {loggedIn: false};
    state.user = null;
  },
  registerRequest(state, user) {
    state.status = {registering: true};
    state.user = user;
  },
  registerSuccess(state, user) {
    state.status = {loggedIn: true};
    state.user = user;
  },
  registerFailure(state, error) {
    state.status = {};
    console.log(error);
  },
  createSuccess(state) {
    let currentUser = JSON.parse(localStorage.getItem('user'));
    state.status = {loggedIn: true};
    state.user = currentUser;
  },
  createFailure(state, error) {
    state.status = {};
    console.log(error);
  },
};

export const account = {
  namespaced: true,
  state,
  actions,
  mutations
};