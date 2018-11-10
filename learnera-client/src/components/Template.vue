<template>
  <v-container v-if="template">
    <v-layout row wrap>
      <v-flex xs12>
        <v-layout row>
          <v-avatar size="100" color="teal">
            <img v-if="template.info && template.info.avatar" :src="template.info.avatar">
            <span v-else-if="template.name" class="ava">{{ template.name[0] }}</span>
          </v-avatar>
          <div class="mainInfo">
            <v-layout justify-space-between column fill-height>
              <v-flex d-flex justify-center align-center class="head">{{ template.name ? template.name : '' }}</v-flex>
              <v-flex d-flex justify align-center>
                <v-chip :color="(template.completed ? 'orange' : 'green') + ' darken-3'" align text-color="white">
                  {{ template.completed ? 'FINAL' : 'EDITABLE' }}
                </v-chip>
              </v-flex>
            </v-layout>
          </div>
          <v-spacer/>
          <div>
            <v-layout row>
              <v-flex xs5>
                <v-layout column fill-height>
                  <v-flex d-flex justify-space-around="" align-center>Teacher:</v-flex>
                </v-layout>
              </v-flex>
              <v-flex xs7>
                <v-layout justify-space-between column fill-height>
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
      <v-textarea v-if="template.description" 
                  :value="template.description"
                  box
                  label="Description"
                  auto-grow
                  readonly/>
      <v-flex xs12><v-divider style="margin: -1.2em 0 1em 0;"/></v-flex>
      <v-flex xs12>
        <v-card v-if="weeks">
          <v-card-title><h3 class="headline mb-0">Weeks</h3></v-card-title>
          <v-responsive>
            <v-tabs color="cyan" dark slider-color="yellow">
              <v-tab v-for="week in weeks" :key="week.id" ripple>
                {{ week.name ? week.name : 'Week ' + (week.weekNumber + 1) }}
              </v-tab>

              <v-tab-item v-for="week in weeks" :key="week.id">
                <v-card>
                  <v-tabs color="teal lighten-1" dark slider-color="yellow">
                    <v-tab v-for="(lesson, index) in lessonsByWeekId(week.id)" :key="week.weekNumber * 100 + index" ripple>
                      {{ lesson.name ? lesson.name : ('Lesson ' + (lesson.ordering + 1)) }}
                    </v-tab>

                    <v-tab-item v-for="(lesson, index) in lessonsByWeekId(week.id)" :key="week.weekNumber * 100 + index">
                      <v-card flat>
                        Lesson info should be here.
                      </v-card>
                    </v-tab-item>
                  </v-tabs>
                </v-card>
              </v-tab-item>
            </v-tabs>
          </v-responsive>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'

export default {
  name: 'Template',
  props: ['templateIdStr'],
  data() {
    return {
    };
  },
  computed: {
    ...mapState('templates', {
      template: function(state) {
        const ret = state.items.find(i => i.id === this.templateId);
        return ret;
      }
    }),
    ...mapState('account', ['user']),
    ...mapState('users', {
      teacher: function(state) {
        if (!this.template) {
          return undefined;
        }
        const ret = state.items.find(i => i.id === this.template.teacher);
        return ret;
      }
    }),
    ...mapGetters('lessons', ['lessonsByWeekId']),
    weeks: function() {
      return this.template ? this.template.weeks : []
    },
    templateId: function() {
      return parseInt(this.templateIdStr);
    },
    teacherName: function() {
      if (!this.teacher) { return ''; }
      if (this.teacher.info) {
        return this.teacher.info.firstName || this.teacher.info.lastName || this.teacher.email;
      }
      return this.teacher.email;
    },
  },
  watch: {
    templateId: function(val) {
      this.getTemplate(val);
    },
    template: function(val) {
      if (val) {
        this.getUser(val.teacher);
        val.weeks.forEach(week => {
          this.getLessonsByWeekId(week.id);
        });
      }
    }
  },
  beforeMount() {
    this.getTemplate(this.templateId);
  },
  methods: {
    ...mapActions('templates', {
      getTemplate: 'get'
    }),
    ...mapActions('users', {
      getUser: 'get'
    }),
    ...mapActions('lessons', {
      getLessonsByWeekId: 'getByWeekId'
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
