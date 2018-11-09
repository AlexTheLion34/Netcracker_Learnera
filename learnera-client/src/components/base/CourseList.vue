<template>
  <item-list :items="mappedCourses"
             :actions="actions"
             api-url="/course" @action-clicked="onActionClicked"/>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import ItemList from './ItemList.vue'

export default {
  name: 'CourseList',
  components: {ItemList},
  props: ['items', 'actions'],
  data() {
    return {
    };
  },
  computed: {
    mappedCourses() {
      if (!this.items) {
        return [];
      }
      return this.items.map(course => {
        return {
          id: course.id,
          name: course.name,
          description: course.description,
          avatar: course.info ? course.info.avatar : undefined,
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