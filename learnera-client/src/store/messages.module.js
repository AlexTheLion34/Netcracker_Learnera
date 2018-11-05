import {messageService} from '../services'
import Vue from 'vue';
import crudModule from './crud.module'

export const messages = function(apiUrl) {
  const messageServiceActual = messageService(apiUrl);

  const {state, actions: crudActions, mutations: crudMutations} = crudModule(messageServiceActual);

  const actions = {
    ...crudActions,
    getByDestinationId({commit}, id) {
      return new Promise((resolve, reject) => {
        commit('getByWeekIdRequest');

        messageServiceActual.getByDestinationId(id)
          .then(messages => {
            commit('getByWeekIdSuccess', messages);
            resolve(messages);
          })
          .catch(e => {
            commit('getByWeekIdFailure', e);
            reject(e);
          });
      });
    }
  }

  const mutations = {
    ...crudMutations,
    getByDestinationIdRequest(state) {
    },
    getByDestinationIdSuccess(state, messages) {
      messages.forEach(message => {
        const idx = state.items.findIndex(i => i.id === i.id);
        if (idx === -1) {
          state.items.push(message);
        } else {
          Vue.set(state.items, idx, message);
        }
      });
    },
    getByDestinationIdFailure(state, id) {
    }
  }

  return {
    namespaced: true,
    state,
    actions,
    mutations
  };
}
