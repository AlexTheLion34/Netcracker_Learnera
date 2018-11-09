<template>
  <item-list :items="mappedGroups"
             :actions="actions"
             api-url="/group" @action-clicked="onActionClicked"/>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import ItemList from './ItemList.vue'

export default {
  name: 'GroupList',
  components: {ItemList},
  props: ['items', 'actions'],
  data() {
    return {
    };
  },
  computed: {
    mappedGroups() {
      if (!this.items) {
        return [];
      }
      return this.items.map(group => {
        return {
          id: group.id,
          name: group.name,
          description: group.description,
          avatar: group.info ? group.info.avatar : undefined,
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