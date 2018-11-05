<template>
  <v-container fluid>
    <v-layout row wrap>
      <v-flex xs12><h3 class="display-2">Courses</h3></v-flex>
      <v-flex v-if="user && user.role === 'TEACHER' && currentUser.id === user.id" xs12>
        <v-btn>Add course</v-btn>
      </v-flex>
      <teacher-course-list v-if="user && user.role === 'TEACHER'" :user="user"/>
      <student-course-list v-if="user && user.role === 'STUDENT'" :user="user"/>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import TeacherCourseList from './TeacherCourseList.vue'
import StudentCourseList from './StudentCourseList.vue'

export default {
  name: 'Courses',
  components: {StudentCourseList, TeacherCourseList},
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
