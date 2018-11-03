import Vue from 'vue'

export default {
  getAllRequest(state) {
    state.all = {loading: true};
  },
  getAllSuccess(state, items) {
    state.all = {items};
  },
  getAllFailure(state, error) {
    state.all = {error};
  },
  getRequest(state, id) {
    const idx = state.all.items.findIndex(i => i.id === id);
    if (idx === -1) {
      state.all.items.push({id, loading: true});
    } else {
      Vue.set(state.all.items, idx, {...state.all.items[idx], loading:true});
    }
  },
  getSuccess(state, item) {
    const idx = state.all.items.findIndex(i => i.id === item.id);
    Vue.set(state.all.items, idx, item);
  },
  getFailure(state, id) {
    state.all.items = state.all.items.filter(i => i.id !== id);
  },
  deleteRequest(state, id) {
    state.all.items = state.all.items.map(item =>
      item.id === id
        ? {...item, deleting: true}
        : item
    );
  },
  deleteSuccess(state, id) {
    // remove deleted user from state
    state.all.items = state.all.items.filter(item => item.id !== id)
  },
  deleteFailure(state, {id, error}) {
    // remove 'deleting:true' property and add 'deleteError:[error]' property to user 
    state.all.items = state.all.items.map(item => {
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