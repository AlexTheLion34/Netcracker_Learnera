<template>
  <group-list :items="groups"
              :actions="actions"
              @action-clicked="onActionClicked"/>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import GroupList from './GroupList.vue'

export default {
  name: 'UserGroupList',
  components: {GroupList},
  props: ['user', 'actions'],
  data() {
    return {
    };
  },
  computed: {
    ...mapState('account', {
      currentUser: state => state.user
    }),
    groups: function() {
      if (!this.user || !this.user.role) {
        return [];
      }
      if (this.user.role == 'TEACHER') {
        return this.user.curatedGroups;
      } else if (this.user.role == 'STUDENT') {
        return this.user.studyGroups;
      }
    }
  },
  methods: {
    onActionClicked(info) {
      this.$emit('action-clicked', info);
    }
  }
}
</script>