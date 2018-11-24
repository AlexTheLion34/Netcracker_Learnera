<template>
  <v-container v-if="course">
    <v-layout row wrap>
      <v-textarea v-if="course.description" 
                  :value="course.description"
                  box
                  label="Description"
                  auto-grow
                  readonly/>
      <v-flex xs12><v-divider style="margin: -1.2em 0 1em 0;"/></v-flex>
      <v-flex xs12>
        <v-card>
          <v-card-title><h3 class="headline mb-0">Deadlines</h3></v-card-title>
          <v-responsive>
            <v-data-table
              :headers="[
                {text: 'Week', value: 'weekName'}, 
                {text: 'Start Date', value: 'startDate'}, 
                {text: 'End Date', value: 'endDate'}
              ]"
              :items="weekDates"
              hide-actions
              class="elevation-1"
            >
              <template slot="items" slot-scope="props">
                <td>{{ props.item.weekName }}</td>
                <td>{{ props.item.startDate }}</td>
                <td>{{ props.item.endDate }}</td>
              </template>
            </v-data-table>
          </v-responsive>
        </v-card>
      </v-flex>
      <v-flex xs12><v-divider style="margin: 1em 0 1em 0;"/></v-flex>
      <v-flex v-if="isCurrentUserStudent" xs12>
        <v-btn v-if="course.template.weeks[0]" 
               :to="{path: `/course/${course.id}/study`, query: {week: currentWeek.id}}" block color="primary" dark>
          <strong>Continue studying!</strong>
        </v-btn>
      </v-flex>
      <v-flex v-else-if="user.role === 'TEACHER'" xs12>
        <v-btn v-if="course.template.weeks[0]" 
               :to="{path: `/course/${course.id}/observe`, query: {week: currentWeek.id}}" block color="primary" dark>
          <strong>Take a look at the course!</strong>
        </v-btn>
      </v-flex>
      <v-flex xs12><v-divider style="margin: 1em 0 1em 0;"/></v-flex>
      <v-flex xs12>
        <v-card>
          <v-card-title><h3 class="headline mb-0">Messages</h3></v-card-title>
          TODO
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'

export default {
  name: 'CourseHome',
  model: {
    prop: 'course',
    event: 'course-changed'
  },
  props: ['course'],
  data() {
    return {
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
      },
      currentUser: function(state) {
        const ret = state.items.find(i => i.id === this.user.id);
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
    },
    currentWeek: function() {
      if (!this.course || !this.course.weekDates) {
        return [];
      }
      const wd = this.course.weekDates.find(wd => new Date(wd.startDate) >= Date.now() && new Date(wd.endDate) <= Date.now());

      return wd ? wd.week : this.course.template.weeks[0];

    },
    isCurrentUserStudent: function() {
      if (this.user.role !== 'STUDENT') {
        return false;
      }

      if (!this.currentUser) {
        return false;
      }
      
      return this.currentUser.studyGroups.find(x => x.id === this.course.id) !== null;
    }
  },
  watch: {
    course: function(val) {
      if (val) {
        this.getUser(val.template.teacher);
      }
    }
  },
  beforeMount() {
    this.getUser(this.user.id);
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
