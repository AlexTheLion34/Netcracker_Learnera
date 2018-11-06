<template>
  <item-list :items="mappedGroups"
             :actions="actions"
             api-url="/group" @actionClicked="onActionClicked"/>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import ItemList from './ItemList.vue'

export default {
  name: 'UserGroupList',
  components: {ItemList},
  props: ['user', 'actions'],
  data() {
    return {
    };
  },
  computed: {
    ...mapState('account', {
      currentUser: state => state.user
    }),
    mappedGroups: function() {
      console.log(this.user)
      if (!this.user || !this.user.role) {
        return []
      }
      let ret = [];
      if (this.user.role == 'TEACHER') {
        return this.user.curatedGroups
      } else if (this.user.role == 'STUDENT') {
        return this.user.studyGroups
      }
    }
  },
  methods: {
    onActionClicked(info) {
      this.$emit('actionClicked', info);
    }
  }
}
</script>