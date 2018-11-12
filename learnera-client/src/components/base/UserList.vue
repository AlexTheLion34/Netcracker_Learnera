<template>
  <item-list :items="mappedUsers"
             :actions="actions"
             api-url="/user" @action-clicked="onActionClicked"/>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import ItemList from './ItemList.vue'

export default {
  name: 'UserList',
  components: {ItemList},
  props: ['items', 'actions'],
  data() {
    return {
    };
  },
  computed: {
    mappedUsers() {
      if (!this.items) {
        return [];
      }
      return this.items.map(user => {
        return {
          id: user.id,
          //nickname: user.info ? user.info.nickname : undefined,
          name: user.info ? user.info.firstName : undefined,
          avatar: user.info ? user.info.avatar : undefined,
        };
      });
    }
  },
  methods: {
    onActionClicked(info) {
      this.$emit('action-clicked', info);
    }
  }
}
</script>