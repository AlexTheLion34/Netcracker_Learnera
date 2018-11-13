<template>
  <v-container v-if="course.id">
    <v-layout row wrap>
      <v-flex xs12>
        <v-layout row>
          <v-avatar size="100" color="teal">
            <img v-if="course.info && course.info.avatar" :src="course.info.avatar">
            <span v-else-if="course.name" class="ava">{{ course.name[0] }}</span>
          </v-avatar>
          <div class="mainInfo">
            <v-layout justify-space-between column fill-height>
              <v-flex d-flex justify-center align-center class="head">{{ course.name ? course.name : '' }}</v-flex>
              <v-flex d-flex justify-center align-center>
                <v-chip align color="green" text-color="white">
                  <v-avatar class="green darken-4">{{ course.groups.length }}</v-avatar>
                  Group{{ course.groups.length > 1 ? 's' : '' }}
                </v-chip>
                <!-- TODO: ADD MENU POPUP TO SELECT GROUP TO GO TO -->
              </v-flex>
            </v-layout>
          </div>
          <v-spacer/>
          <div>
            <v-layout row>
              <v-flex xs5>
                <v-layout justify-space-between column fill-height>
                  <v-flex v-if="user.role === 'TEACHER'" d-flex justify-center align-center>Base template:</v-flex>
                  <v-flex d-flex justify-center align-center>Teacher:</v-flex>
                </v-layout>
              </v-flex>
              <v-flex xs7>
                <v-layout justify-space-between column fill-height>
                  <v-btn v-if="user.role === 'TEACHER'" :to="`/template/${course.template.id}`" color="secondary">
                    {{ course.template.name || course.template.id }}
                  </v-btn>
                  <v-btn v-if="teacher" :to="`/user/${teacher.id}`" color="primary">
                    {{ teacherName }}
                  </v-btn>
                </v-layout>
              </v-flex>
            </v-layout>
          </div>
        </v-layout>
      </v-flex>
      <v-flex xs12><v-divider style="margin: 1em 0 1em 0;"/></v-flex>
      <v-flex xs12>
        <router-view v-model="course"/>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'

export default {
  name: 'Course',
  props: ['courseIdStr'],
  data() {
    return {
      course: {
      }
    };
  },
  computed: {
    ...mapState('account', ['user']),
    ...mapState('users', {
      teacher: function(state) {
        if (!this.course) {
          return undefined;
        }
        const ret = state.items.find(i => i.id === this.course.template.teacher);
        return ret;
      }
    }),
    courseId: function() {
      return parseInt(this.courseIdStr);
    },
    teacherName: function() {
      if (!this.teacher) { return ''; }
      if (this.teacher.info) {
        return this.teacher.info.firstName || this.teacher.info.lastName || this.teacher.email;
      }
      return this.teacher.email;
    },
    weekDates: function() {
      if (!this.course || !this.course.weekDates) {
        return [];
      }
      const ret = this.course.weekDates.map(wd => {
        const week = wd.week;
        return {
          weekName: week.name || ('Week ' + week.weekNumber),
          startDate: wd.startDate,
          endDate: wd.endDate
        }
      });
      return ret; 
    }
  },
  watch: {
    courseId: function(val) {
      this.getCourse(val);
    },
    course: function(val) {
      if (val) {
        this.getUser(val.template.teacher);
      }
    }
  },
  beforeMount() {
    this.getCourse(this.courseId).then(c => this.course = c);
  },
  methods: {
    ...mapActions('courses', {
      getCourse: 'get'
    }),
    ...mapActions('users', {
      getUser: 'get'
    })
  },
}
</script>

<style>
.ava {
  font-size: 5em;
  color: white;
  text-align: center;
}

.mainInfo {
  padding-left: 0.4em;
}

.head {
  font-size: 1.5em;
  color: black;
}

</style>
