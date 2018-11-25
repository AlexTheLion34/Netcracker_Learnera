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
                {text: 'Module', value: 'moduleName'},
                {text: 'Start Date', value: 'startDate'}, 
                {text: 'End Date', value: 'endDate'}
              ]"
              :items="moduleDates"
              hide-actions
              class="elevation-1"
            >
              <template slot="items" slot-scope="props">
                <td>{{ props.item.moduleName }}</td>
                <td>{{ props.item.startDate }}</td>
                <td>{{ props.item.endDate }}</td>
              </template>
            </v-data-table>
          </v-responsive>
        </v-card>
      </v-flex>
      <v-flex xs12><v-divider style="margin: 1em 0 1em 0;"/></v-flex>
      <v-flex xs12>
        <v-card>
          <v-card-title><h3 class="headline mb-0">Groups</h3></v-card-title>
          <v-responsive>
            <v-data-table
              :headers="[
                {text: 'Name', value: 'name'}, 
                {text: 'Students', value: 'studentCount'}, 
              ]"
              :items="groupsMapped"
              hide-actions
              class="elevation-1"
            >
              <template slot="items" slot-scope="props">
                <td>{{ props.item.name }}</td>
                <td>{{ props.item.studentCount }}</td>
              </template>
            </v-data-table>
          </v-responsive>
        </v-card>
      </v-flex>
      <v-flex xs12><v-divider style="margin: 1em 0 1em 0;"/></v-flex>
      <v-flex v-if="isCurrentUserStudent" xs12>
        <v-btn v-if="course.template.modules[0]"
               :to="{path: `/course/${course.id}/study`, query: {module: currentModule.id}}" block color="primary" dark>
          <strong>Continue studying!</strong>
        </v-btn>
      </v-flex>
      <v-flex v-else-if="user.role === 'TEACHER'" xs12>
        <v-btn v-if="course.template.modules[0]"
               :to="{path: `/course/${course.id}/observe`, query: {module: currentModule.id}}" block color="primary" dark>
          <strong>Take a look at the course!</strong>
        </v-btn>
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
    moduleDates: function() {
      if (!this.course || !this.course.moduleDates) {
        return [];
      }
      const ret = this.course.moduleDates.map(wd => {
        const module = wd.module;
        return {
          moduleName: module.name || ('Module ' + module.moduleNumber),
          startDate: wd.startDate,
          endDate: wd.endDate
        }
      });
      return ret; 
    },
    currentModule: function() {
      if (!this.course || !this.course.moduleDates) {
        return [];
      }
      const wd = this.course.moduleDates.find(wd => new Date(wd.startDate) >= Date.now() && new Date(wd.endDate) <= Date.now());

      return wd ? wd.module : this.course.template.modules[0];

    },
    isCurrentUserStudent: function() {
      if (this.user.role !== 'STUDENT') {
        return false;
      }

      if (!this.currentUser) {
        return false;
      }
      
      return this.currentUser.studyGroups.find(x => x.id === this.course.id) !== null;
    },
    groupsMapped: function() {
      if (!this.course || !this.course.groups || !this.course.groups[0] || !this.course.groups[0].id) {
        return [];
      }

      return this.course.groups.map(g => ({name: g.name, studentCount: g.students.length}));
    }
  },
  watch: {
    course: function(val) {
      if (val && val.id) {
        this.getUser(val.template.teacher).catch(e => this.alertError(`Failed to fetch teacher: ${e.data.message}`));
        this.getGroupsByCourseId(val.id).catch(e => this.alertError(`Failed to fetch groups: ${e.data.message}`));
      }
    }
  },
  beforeMount() {
    this.getUser(this.user.id).catch(e => this.alertError(`Failed to fetch current user: ${e.data.message}`));
    if (this.course && this.course.id) {
      this.getGroupsByCourseId(this.course.id).then(groups => {
        this.course.groups = groups;
      }).catch(e => this.alertError(`Failed to fetch groups: ${e.data.message}`));
    }
  },
  methods: {
    ...mapActions('alert', {
      alertError: 'error',
      alertSuccess: 'success'
    }),
    ...mapActions('courses', {
      getCourse: 'get'
    }),
    ...mapActions('users', {
      getUser: 'get'
    }),
    ...mapActions('groups', {
      getGroupsByCourseId: 'getByCourseId'
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
