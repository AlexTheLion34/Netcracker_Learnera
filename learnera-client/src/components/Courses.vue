<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12><h3 class="display-2">Courses</h3></v-flex>
      <v-flex v-if="user && user.role === 'TEACHER' && currentUser.id === user.id" xs12>
        <v-btn :to="`/create-course`">Add course</v-btn>
      </v-flex>
      <user-course-list :user="user" :actions="isAuthorized ? listActions : []"
                        @action-clicked="onActionClicked"/>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import UserCourseList from './base/UserCourseList.vue'

export default {
  name: 'Courses',
  components: {UserCourseList},
  props: ['userIdStr'],
  data() {
    return {
      listActions: [{name: "Edit", icon: "build"}]
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
    }),
    isAuthorized: function() {
      return this.user && this.user.role === 'TEACHER' && this.currentUser.id === this.user.id;
    }
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
    }),
    onActionClicked({name, course}) {
      this.$router.push(`/course/${course.id}/edit`);
    }
  }
}
</script>
