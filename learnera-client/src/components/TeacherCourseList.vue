<template>
  <v-layout row wrap>
    <v-list two-line>
      <v-list-tile
        v-for="course in courses"
        :key="course.id"
        :to="`/course/${course.id}`"
        avatar
      >
        <v-list-tile-avatar>
          <v-flex>
            <v-avatar color="teal">
              <img v-if="course.info && course.info.avatar" :src="course.info.avatar">
              <span v-else class="white--text headline">{{ course.name[0] }}</span>
            </v-avatar>
          </v-flex>
        </v-list-tile-avatar>

        <v-list-tile-content>
          <v-list-tile-title>{{ course.name }}</v-list-tile-title>
          <v-list-tile-sub-title>{{ course.description }}</v-list-tile-sub-title>
        </v-list-tile-content>

        <v-list-tile-action v-if="currentUser.id === user.id">
          <v-tooltip bottom>
            <v-btn slot="activator" icon ripple>
              <v-icon color="grey lighten-1">build</v-icon>
            </v-btn>
            <span>Edit course</span>
          </v-tooltip>
          <v-tooltip bottom>
            <v-btn slot="activator" icon ripple>
              <v-icon color="red lighten-1">delete</v-icon>
            </v-btn>
            <span>Delete course</span>
          </v-tooltip>
        </v-list-tile-action>
      </v-list-tile>
    </v-list>
  </v-layout>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import {store} from '../store'

export default {
  name: 'TeacherCourseList',
  props: ['user'],
  data() {
    return {
    };
  },
  computed: {
    ...mapState('account', {
      currentUser: state => state.user
    }),
    ...mapGetters('courses', {coursesByTeacherId: 'coursesByTeacherId', getCourse: 'get'}),
    courses() {
      if (!this.user && !this.user.id) {
        return [];
      } 
      const ret = this.coursesByTeacherId(this.user.id);
      return ret;
    }
  },
  watch: {
    user: function(val) {
      if (val && val.id) {
        this.getByTeacherId(this.user.id);
      }
    }
  },
  methods: {
    ...mapActions('courses', ['getByTeacherId'])
  }
}
</script>

