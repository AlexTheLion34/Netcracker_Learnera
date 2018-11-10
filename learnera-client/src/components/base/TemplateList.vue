<template>
  <item-list :items="mappedTemplates"
             :actions="actions"
             api-url="/template" @action-clicked="onActionClicked"/>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import ItemList from './ItemList.vue'

export default {
  name: 'TemplateList',
  components: {ItemList},
  props: ['items', 'actions'],
  data() {
    return {
    };
  },
  computed: {
    mappedTemplates() {
      if (!this.items) {
        return [];
      }
      return this.items.map(template => {
        return {
          id: template.id,
          name: template.name,
          description: template.description,
          avatar: template.info ? template.info.avatar : null,
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