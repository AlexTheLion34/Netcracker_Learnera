import Vue from 'vue'

export default function(service) {
  return {
    state: {
      items: []
    },
    actions: {
      create({commit}, item) {
        return new Promise((resolve, reject) => {
          commit('createRequest', item);
    
          service.create(item)
            .then(i => {
              commit('createSuccess', i);
              resolve(i);
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
    
          service.getById(id)
            .then(i => {
              commit('getSuccess', i);
              resolve(i);
            })
            .catch(e => {
              commit('getFailure', {id, error: e.toString()});
              reject(e);
            });
        });
      },
      update({commit}, item) {
        return new Promise((resolve, reject) => {
          commit('updateRequest', item);

          service.update(item)
            .then(i => {
              commit('updateSuccess', i);
              resolve(i);
            })
            .catch(e => {
              commit('updateFailure', {item, error: e.toString()});
              reject(e);
            });
        });
      },
      delete({commit}, id) {
        return new Promise((resolve, reject) => {
          commit('deleteRequest', id);
    
          service.delete(id)
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
    },
    mutations: {
      getRequest(state, id) {
        console.log('Getting item with id: ', id)
      },
      getSuccess(state, item) {
        console.log('Got item: ', item);
        const idx = state.items.findIndex(i => i.id === item.id);
        if (idx === -1) {
          state.items.push(item);
        } else {
          Vue.set(state.items, idx, item);
        }
      },
      getFailure(state, {id, error}) {
        console.error('Failed to get item with id: ', id);
        console.error('Error: ', error);
      },
      createRequest(state, item) {
        console.log('Creating item: ', item)
      },
      createSuccess(state, item) {
        console.log('Created item: ', item)
        state.items.push(item);
      },
      createFailure(state, e) {
        console.error('Failed to create: ', e)
      },
      updateRequest(state, item) {
        console.log('Updating item: ', item)
      },
      updateSuccess(state, item) {
        console.log('Updated item: ', item)
        const idx = state.items.findIndex(i => i.id === item.id);
        if (idx === -1) {
          state.items.push(item);
        } else {
          Vue.set(state.items, idx, item);
        }
      },
      updateFailure(state, {item, error}) {
        console.error('Failed to update: ', error)
      },
      deleteRequest(state, id) {
        console.log('Deleting item with id: ', id)
        state.items = state.items.map(item =>
          item.id === id
            ? {...item, deleting: true}
            : item
        );
      },
      deleteSuccess(state, id) {
        console.log('Deleted item with id: ', id)

        // remove deleted user from state
        state.items = state.items.filter(item => item.id !== id)
      },
      deleteFailure(state, {id, error}) {
        console.log('Failed to delete item with id: ', id)
        // remove 'deleting:true' property and add 'deleteError:[error]' property 
        state.items = state.items.map(item => {
          if (item.id === id) {
            // make copy without 'deleting:true' property
            const {deleting, ...itemCopy} = item;
            // return copy with 'deleteError:[error]' property
            return {...itemCopy, deleteError: error};
          }
    
          return item;
        })
      }
      // TODO: ADD UPDATE
    }
  }
}