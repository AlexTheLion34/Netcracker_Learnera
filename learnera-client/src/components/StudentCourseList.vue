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
          <v-list-tile-title><h3>{{ course.name }}<small> (in group "{{ course.group.name }}")</small></h3></v-list-tile-title>
          <v-list-tile-sub-title>{{ course.description }}</v-list-tile-sub-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-list>
  </v-layout>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'

export default {
  name: 'StudentCourseList',
  props: ['user'],
  data() {
    return {
    };
  },
  computed: {
    courses() {
      if (!this.user || !this.user.studyGroups) {
        return [];
      } 
      const ret = this.user.studyGroups.map(g => {
        return g.courses.map(c => {
          return {...c, groups:[], group: g}
        });
      }).flat();
      return ret;
    }
  },
}
</script>

