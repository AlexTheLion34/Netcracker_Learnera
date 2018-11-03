<template>
  <v-layout row>
    <v-list two-line>
      <v-list-tile
        v-for="course in courses"
        :key="course.id"
        :to="`/course/${course.id}`"
        avatar
      >
        <v-list-tile-avatar>
          <v-avatar color="teal">
            <img v-if="course.info && course.info.avatar" :src="course.info.avatar">
            <span v-else class="white--text headline">{{ course.name[0] }}</span>
          </v-avatar>
        </v-list-tile-avatar>

        <v-list-tile-content>
          <v-list-tile-title>{{ course.name }}</v-list-tile-title>
          <v-list-tile-sub-title>{{ course.description }}</v-list-tile-sub-title>
        </v-list-tile-content>

        <v-list-tile-action>
          <v-btn icon ripple>
            <v-icon color="grey lighten-1">build</v-icon>
          </v-btn>
          <v-btn icon ripple>
            <v-icon color="red lighten-1">delete</v-icon>
          </v-btn>
        </v-list-tile-action>
      </v-list-tile>

    </v-list>
  </v-layout>
</template>

<script>
import {mapState} from 'vuex';

export default {
  name: 'CoursesList',
  data() {
    return {
      ...mapState('account', ['user']),
    };
  },
  computed: {
    courses() { return this.user.courses }
  }
}
</script>

