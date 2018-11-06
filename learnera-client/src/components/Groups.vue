<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12><h3 class="display-2">Groups</h3></v-flex>
      <v-flex v-if="user && user.role === 'TEACHER' && currentUser.id === user.id" xs12>
        <v-btn>Add group</v-btn>
      </v-flex>
            <groups-list :user="user"/>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions} from 'vuex'
import GroupsList from './GroupsList.vue'
import TeacherCourseList from './TeacherCourseList.vue'
import StudentCourseList from './StudentCourseList.vue'
export default {
    name: 'Groups',
    components: {'groups-list': GroupsList},
  props: ['userIdStr'],
  data() {
    return {
    };
  },
  computed: {
    userId: function() {
      return parseInt(this.userIdStr);
    },
    ...mapState('account', {
      currentUser: state => state.user
    }),
    ...mapState('users', {
      user(state) { 
        const ret = state.items.find(x => x.id === this.userId);
        return ret;
      }
    })
  },
  watch: {
    userIdStr: function() { 
      this.getUser(this.userId);
    }
  },
  created() {
    this.getUser(this.userId);
  },
  methods: {
    ...mapActions('users', {
      getUser: 'get'
    })
  }
}
</script>