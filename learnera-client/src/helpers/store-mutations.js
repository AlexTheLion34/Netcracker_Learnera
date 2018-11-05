import Vue from 'vue'

export default {
  getRequest(state, id) {
    const idx = state.items.findIndex(i => i.id === id);
    if (idx === -1) {
      state.items.push({id, loading: true});
    } else {
      Vue.set(state.items, idx, {...state.items[idx], loading:true});
    }
  },
  getSuccess(state, item) {
    const idx = state.items.findIndex(i => i.id === item.id);
    Vue.set(state.items, idx, item);
  },
  getFailure(state, id) {
    state.items = state.items.filter(i => i.id !== id);
  },
  createRequest(state, item) {
  },
  createSuccess(state, item) {
    state.items.push(item);
  },
  createFailure(state, item) {
  },
  deleteRequest(state, id) {
    state.items = state.items.map(item =>
      item.id === id
        ? {...item, deleting: true}
        : item
    );
  },
  deleteSuccess(state, id) {
    // remove deleted user from state
    state.items = state.items.filter(item => item.id !== id)
  },
  deleteFailure(state, {id, error}) {
    // remove 'deleting:true' property and add 'deleteError:[error]' property to user 
    state.items = state.items.map(item => {
      if (item.id === id) {
        // make copy of user without 'deleting:true' property
        const {deleting, ...itemCopy} = item;
        // return copy of user with 'deleteError:[error]' property
        return {...itemCopy, deleteError: error};
      }

      return item;
    })
  }
}