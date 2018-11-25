<template>
  <v-container v-if="user">
    <v-form v-model="formValid">
      <v-layout row wrap>
        <v-flex d-flex justify-center align-center>
          <h3 class="headline">Create course based on template </h3>
          <v-select
            :items="userTemplatesSelection"
            v-model="selectedTemplate"
            label="Select template"
          />
        </v-flex>
        <v-flex xs12>
          <v-text-field v-model="courseName" :rules="nameRules" label="Course name" required/>
        </v-flex>
        <v-flex style="margin: 15px 0 0 0">
          <v-textarea v-model="courseDescription" box label="Description" auto-grow/>
        </v-flex>
        <v-flex xs12>
          <v-card>
            <v-card-title><h3 class="headline mb-0">Deadlines</h3></v-card-title>
            <v-responsive>
              <v-layout column>
                <template v-for="weekDateElem in weekDateElems">
                  <v-layout :key="weekDateElem.week.id" row>
                    <v-flex d-flex justify-center align-center xs2 style="margin: 0 0 0 0.5em">
                      <v-chip disabled>{{ weekDateElem.week.name || 'Week ' + weekDateElem.week.weekNumber }}</v-chip>
                    </v-flex>
                    <v-flex d-flex justify-center align-center style="margin: 0 1em 0 1em">
                      <v-menu
                        :close-on-content-click="false"
                        v-model="weekDateElem.startMenu"
                        :nudge-right="40"
                        lazy
                        transition="scale-transition"
                        offset-y
                        min-width="250px"
                      >
                        <v-text-field
                          slot="activator"
                          v-model="weekDateElem.startDate"
                          label="Start date"
                          readonly/>
                        <v-date-picker v-model="weekDateElem.startDate" :first-day-of-week="1"
                                       @input="weekDateElem.startMenu = false"/>
                      </v-menu>
                    </v-flex>
                    <v-flex d-flex justify-center align-center style="margin: 0 1em 0 1em">
                      <v-menu
                        :close-on-content-click="false"
                        v-model="weekDateElem.endMenu"
                        :nudge-right="40"
                        lazy
                        transition="scale-transition"
                        offset-y
                        min-width="250px"
                      >
                        <v-text-field
                          slot="activator"
                          v-model="weekDateElem.endDate"
                          label="End date"
                          readonly/>
                        <v-date-picker v-model="weekDateElem.endDate" :first-day-of-week="1" 
                                       @input="weekDateElem.endMenu = false"/>
                      </v-menu>
                    </v-flex>
                  </v-layout>
                </template>
              </v-layout>
            </v-responsive>
          </v-card>
        </v-flex>
        <v-flex xs12 style="margin: 15px 0 0 0">
          <v-card>
            <v-card-title><h3 class="headline mb-0">Group management</h3></v-card-title>
            <v-responsive>
              <two-lists-selector :items="userGroups" :list-renderer="GroupList" header-all="All groups" header-selected="Participants"
                                  @update:selected-items="selectedGroups = $event" />
            </v-responsive>
          </v-card>
        </v-flex>
        <v-flex xs12 style="margin: 15px 0 0 0">
          <v-card>
            <v-card-title><h3 class="headline mb-0">Confirm your action</h3></v-card-title>
            <v-responsive>
              <v-layout column>
                <v-flex>
                  <error-message v-model="showError" :message="hasError"/>
                </v-flex>
                <v-flex>
                  <v-layout row>
                    <v-flex style="margin: 0 10px 0 10px" xs6>
                      <v-btn :disabled="!!hasError" color="primary" block @click="onCourseCreate">Create course</v-btn>
                    </v-flex>
                    <v-flex style="margin: 0 10px 0 10px" xs6>
                      <v-btn color="secondary" block @click="
                      $router.back()">Cancel</v-btn>
                    </v-flex>
                  </v-layout>
                </v-flex>
              </v-layout>
            </v-responsive>
          </v-card>
        </v-flex>
      </v-layout>
    </v-form>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'
import {router} from '../router'
import GroupList from './base/GroupList.vue'
import TwoListsSelector from './base/TwoListsSelector.vue'
import ErrorMessage from './base/ErrorMessage.vue'

export default {
  name: 'CourseCreator',
  components: {GroupList, TwoListsSelector, ErrorMessage},
  data() {
    return {
      formValid: false,
      nameRules: [
        v => !!v || 'Name is required',
        v => !!v && v.length < 15 || 'Name must be less than 15 characters'
      ],
      showError: true,
      selectedTemplate: null,
      courseName: '',
      courseDescription: '',
      weekDateElems: [],
      selectedGroups: [],
      GroupList
    };
  },
  computed: {
    ...mapState('account', {currentUser: 'user'}),
    ...mapState('users', {
      user(state) { 
        const ret = state.items.find(x => x.id === this.userId);
        return ret;
      }
    }),
    userId: function() {
      return this.currentUser.id;
    },
    userTemplates: function() {
      return this.user ? this.user.templates.filter(t => t.completed) : [];
    },
    userGroups: function() {
      return this.user ? this.user.curatedGroups : [];
    },
    userTemplatesSelection: function() {
      return this.userTemplates.map(t => {return {text: t.name, value: t.id}; })
    },
    currentTemplate: function() {
      return this.userTemplates.find(t => t.id === this.selectedTemplate);
    },
    weeks: function() {
      if (!this.currentTemplate) {
        return undefined;
      }
      return this.currentTemplate.weeks;
    },
    hasError: function() {
      if (!this.formValid) {
        return 'Fix all fields';
      }

      if (!this.selectedTemplate) {
        return 'Please select template';
      }

      if (!this.selectedGroups.length > 0) {
        return 'Select at least one group';
      }

      return this.validateWeekDates();
    }
  },
  watch: {                                                                 
    weeks: function(val) {
      if (!val) {
        this.weekDateElems = [];
        return;
      }

      let i = -1;
      this.weekDateElems = val.map(w => { 
        i++;
        return {
          week: w,
          startDate: this.weekStart(i).toISOString().substr(0, 10), 
          endDate: this.weekEnd(i).toISOString().substr(0, 10),
          startMenu: false,
          endMenu: false
        };
      });
    },
    userId: function(val) {
      this.getUser(val);
    },
    hasError: function(val) {
      this.showError = !!val;
    }
  },
  beforeMount() {
    this.getUser(this.userId);
  },
  methods: {
    ...mapActions('courses', {
      createCourse: 'create'
    }),
    ...mapActions('users', {
      getUser: 'get'
    }),
    ...mapActions('alert', {
      alertError: 'error',
      alertSuccess: 'success'
    }),
    onCourseCreate() {
      const course = {
        template: {id: this.currentTemplate.id},
        name: this.courseName,
        description: this.courseDescription,
        startDate: this.weekDateElems[0].startDate,
        endDate: this.weekDateElems[this.weekDateElems.length - 1].endDate,
        weekDates: this.weekDateElems.map(wde => {
          const {week, startDate, endDate} = wde;
          return {
            id: {
              weekId: week.id
            },
            week: {
              id: week.id
            },
            startDate,
            endDate
          };
        }),
        avatar: null, // TODO
        passPercent: 60, // TODO
        goodPercent: 75, // TODO
        excellentPercent: 90, // TODO
        groups: this.selectedGroups.map(g => ({id: g.id})),
      };

      this.createCourse(course).then(course => {
        this.alertSuccess('Course created');
        router.push(`/course/${course.id}`);
      }).catch(e => this.alertError(`Course failed to create: ${e.data.message}`));
    },
    addDays(date, days) {
      var result = new Date(date);
      result.setDate(result.getDate() + days);
      return result;
    },
    weekStart(index) {
      let currDate = Date.now();
      
      currDate = this.addDays(currDate, 7 * index);
      currDate = this.addDays(currDate, 8 - currDate.getDay());
      return currDate;
    },
    weekEnd(index) {
      let currDate = Date.now();

      currDate = this.addDays(currDate, 7 * index);
      currDate = this.addDays(currDate, 14 - currDate.getDay());
      return currDate;
    },
    validateWeekDates() {
      for (let i = 0; i < this.weekDateElems.length; ++i) {
        const currWeekDate = this.weekDateElems[i];

        if (currWeekDate.startDate >= currWeekDate.endDate) {
          return `Week #${i + 1} range is incorrect`;
        }

        if (i === 0) {
          continue;
        }

        const prevWeekDate = this.weekDateElems[i - 1];

        if (currWeekDate.startDate < prevWeekDate.endDate) {
          return `Week #${i + 1} cannot start before week #${i} ends`;
        }
      }
      return '';
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
