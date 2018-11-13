<template>
  <v-container v-if="course">
    <v-layout row wrap>
      <v-textarea v-if="course.description" 
                  v-model="course.description"
                  box
                  label="Description"
                  auto-grow/>
      <v-flex xs12><v-divider style="margin: -1.2em 0 1em 0;"/></v-flex>
      <v-flex xs12>
        <v-card>
          <v-card-title><h3 class="headline mb-0">Deadlines</h3></v-card-title>
          <v-responsive>
            <v-layout column>
              <template v-for="(weekDate, i) in weekDates">
                <v-layout :key="weekDate.week.id" row>
                  <v-flex d-flex justify-center align-center xs2 style="margin: 0 0 0 0.5em">
                    <v-chip>{{ weekDate.week.name || 'Week ' + weekDate.week.weekNumber }}</v-chip>
                  </v-flex>
                  <v-flex d-flex justify-center align-center style="margin: 0 1em 0 1em">
                    <v-menu
                      :close-on-content-click="false"
                      v-model="weekDateMenus[i].startMenu"
                      :nudge-right="40"
                      lazy
                      transition="scale-transition"
                      offset-y
                      min-width="250px"
                    >
                      <v-text-field
                        slot="activator"
                        v-model="weekDate.startDate"
                        label="Start date"
                        readonly/>
                      <v-date-picker v-model="weekDate.startDate" @input="weekDateMenus[i].startMenu = false"/>
                    </v-menu>
                  </v-flex>
                  <v-flex d-flex justify-center align-center style="margin: 0 1em 0 1em">
                    <v-menu
                      :close-on-content-click="false"
                      v-model="weekDateMenus[i].endMenu"
                      :nudge-right="40"
                      lazy
                      transition="scale-transition"
                      offset-y
                      min-width="250px"
                    >
                      <v-text-field
                        slot="activator"
                        v-model="weekDate.endDate"
                        label="End date"
                        readonly/>
                      <v-date-picker v-model="weekDate.endDate" @input="weekDateMenus[i].endMenu = false"/>
                    </v-menu>
                  </v-flex>
                </v-layout>
              </template>
            </v-layout>
          </v-responsive>
        </v-card>
      </v-flex>
      <v-flex xs12><v-divider style="margin: 1em 0 1em 0;"/></v-flex>
      <v-flex xs12>
        <v-btn v-if="course.template.weeks[0]" @click="saveCourse" block color="primary" dark>
          <strong v-if="teacher && teacher.id === user.id">Save course</strong>
        </v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'

export default {
  name: 'CourseEditor',
  model: {
    prop: 'course',
    event: 'course-changed'
  },
  props: ['course'],
  data() {
    return {
      weekDateMenus: [],
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
      return this.course.weekDates;
    },
    weeks: function() {
      return this.course.template.weeks;
    },
  },
  watch: {
    course: function(val) {
      if (val) {
        this.getUser(val.template.teacher);
      }
    },
    weekDates: function() {
      this.weekDateMenus = this.weekDates.map(() => ({startMenu: false, endMenu: false}));
    }
  },
  beforeMount() {
    if (!this.weeks) {
      this.weekDateMenus = [];
      return;
    }

    this.weekDateMenus = this.weekDates.map(() => ({startMenu: false, endMenu: false}));
  },
  methods: {
    ...mapActions('courses', {
      getCourse: 'get',
      updateCourse: 'update'
    }),
    ...mapActions('users', {
      getUser: 'get'
    }),
    saveCourse() {
      let {template, startDate, endDate, weekDates, groups, ...other} = this.course;
      console.log(JSON.stringify(this.course));
      if (weekDates) {
        weekDates[0].course = {id: weekDates[0].course.id || weekDates[0].course};
      }
      weekDates = weekDates.map(({week, startDate, endDate, ...other}) => ({
        ...other, 
        startDate: new Date(startDate).toISOString().substr(0, 10),
        endDate: new Date(endDate).toISOString().substr(0, 10),
        week: {id: week.id}
      }));
      const ret = {
        ...other,
        template: {id: template.id},
        startDate: this.weekDates[0].startDate,
        endDate: this.weekDates[this.weekDates.length - 1].endDate,
        weekDates: weekDates,
        groups: groups.map(g => ({id: g}))
      };
      console.log(JSON.stringify(ret));
      this.updateCourse(ret).then(x => {
        x.weekDates = x.weekDates.map(({startDate, endDate, ...o}) => ({
          ...o,
          startDate: new Date(startDate).toISOString().substr(0, 10),
          endDate: new Date(endDate).toISOString().substr(0, 10)
        }));
        this.$emit('course-changed', x);
      });
    }
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
