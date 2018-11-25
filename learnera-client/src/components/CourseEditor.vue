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
              <template v-for="(moduleDate, i) in moduleDates">
                <v-layout :key="moduleDate.module.id" row>
                  <v-flex d-flex justify-center align-center xs2 style="margin: 0 0 0 0.5em">
                    <v-chip disabled>{{ moduleDate.module.name || 'Module ' + moduleDate.module.moduleNumber }}</v-chip>
                  </v-flex>
                  <v-flex d-flex justify-center align-center style="margin: 0 1em 0 1em">
                    <v-menu
                      :close-on-content-click="false"
                      v-model="moduleDateMenus[i].startMenu"
                      :nudge-right="40"
                      lazy
                      transition="scale-transition"
                      offset-y
                      min-width="250px"
                    >
                      <v-text-field
                        slot="activator"
                        v-model="moduleDate.startDate"
                        label="Start date"
                        readonly/>
                      <v-date-picker v-model="moduleDate.startDate" readonly @input="moduleDateMenus[i].startMenu = false"/>
                    </v-menu>
                  </v-flex>
                  <v-flex d-flex justify-center align-center style="margin: 0 1em 0 1em">
                    <v-menu
                      :close-on-content-click="false"
                      v-model="moduleDateMenus[i].endMenu"
                      :nudge-right="40"
                      lazy
                      transition="scale-transition"
                      offset-y
                      min-width="250px"
                    >
                      <v-text-field
                        slot="activator"
                        v-model="moduleDate.endDate"
                        label="End date"
                        readonly/>
                      <v-date-picker v-model="moduleDate.endDate" readonly @input="moduleDateMenus[i].endMenu = false"/>
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
        <v-btn v-if="course.template.modules[0]" block color="primary" dark @click="saveCourse">
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
      moduleDateMenus: [],
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
    moduleDates: function() {
      if (!this.course || !this.course.moduleDates) {
        return [];
      }
      return this.course.moduleDates;
    },
    modules: function() {
      return this.course.template.modules;
    },
  },
  watch: {
    course: function(val) {
      if (val) {
        this.getUser(val.template.teacher);
      }
    },
    moduleDates: function() {
      this.moduleDateMenus = this.moduleDates.map(() => ({startMenu: false, endMenu: false}));
    }
  },
  beforeMount() {
    if (!this.modules) {
      this.moduleDateMenus = [];
      return;
    }

    this.moduleDateMenus = this.moduleDates.map(() => ({startMenu: false, endMenu: false}));
  },
  methods: {
    ...mapActions('courses', {
      getCourse: 'get',
      updateCourse: 'update'
    }),
    ...mapActions('users', {
      getUser: 'get'
    }),
    ...mapActions('alert', {
      alertError: 'error',
      alertSuccess: 'success'
    }),
    saveCourse() {
      let {template, startDate, endDate, moduleDates, groups, ...other} = this.course;
      if (moduleDates) {
        moduleDates[0].course = {id: moduleDates[0].course.id || moduleDates[0].course};
      }
      moduleDates = moduleDates.map(({module, startDate, endDate, ...other}) => ({
        ...other, 
        startDate: new Date(startDate).toISOString().substr(0, 10),
        endDate: new Date(endDate).toISOString().substr(0, 10),
        module: {id: module.id}
      }));
      const ret = {
        ...other,
        template: {id: template.id},
        startDate: this.moduleDates[0].startDate,
        endDate: this.moduleDates[this.moduleDates.length - 1].endDate,
        moduleDates: moduleDates,
        groups: groups.map(g => ({id: g}))
      };

      this.updateCourse(ret).then(x => {
        x.moduleDates = x.moduleDates.map(({startDate, endDate, ...o}) => ({
          ...o,
          startDate: new Date(startDate).toISOString().substr(0, 10),
          endDate: new Date(endDate).toISOString().substr(0, 10)
        }));
        this.alertSuccess('Course updated successfully');
        this.$emit('course-changed', x);
      }).catch(e => `Course failed to be updated: ${e.data.message}`);
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
