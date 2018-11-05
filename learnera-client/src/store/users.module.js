import {userService} from '../services';

import crudModule from './crud.module'
const {state, actions: crudActions, mutations: crudMutations} = crudModule(userService);

const actions = {
  ...crudActions
};

const mutations = {
  ...crudMutations
};

export const users = {
  namespaced: true,
  state,
  actions,
  mutations
};
