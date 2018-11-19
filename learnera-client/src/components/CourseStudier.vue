<template>
  <v-tabs color="cyan" dark slider-color="yellow">
    <template v-for="(weekDate, i) in weekDates">
      <v-tab :key="`tab-w-${i}`" :disabled="weekDate.dates.startDate > new Date()" ripple>
        <v-layout row fill-height>
          <v-flex d-flex align-center>
            <div>
              {{ weekDate.name ? weekDate.name : 'Week ' + (weekDate.weekNumber + 1) }}
            </div>
          </v-flex>
        </v-layout>
      </v-tab>
      <v-divider :key="`div-w-${i}`" class="cyan lighten-4" inset vertical/>
    </template>

    <v-tab-item v-for="(weekDate, i) in weekDates" :key="`tab-item-${i}`">
      <v-card>
        <v-tabs color="teal lighten-1" dark slider-color="yellow">
          <template v-for="(lesson, j) in weekDate.lessons">
            <v-tab v-if="lesson.id" :key="`tab-w-${i}-l-${j}`" ripple>
              <v-layout row fill-height>
                <v-flex d-flex align-center="">
                  <div>
                    {{ lesson.name ? lesson.name : ('Lesson ' + (lesson.ordering + 1)) }}
                  </div>
                </v-flex>
              </v-layout>
            </v-tab>
            <v-divider v-if="lesson.id" :key="`div-w-${i}-l-${j}`" 
                       class="teal lighten-4" inset vertical/>
          </template>

          <v-tab-item v-for="(lesson, j) in weekDate.lessons"
                      :key="`tab-item-w-${i}-l-${j}`">
            <v-card v-if="lesson.id" flat>
              <lesson-studier v-model="course.template.weeks[i].lessons[j]"/>
            </v-card>
          </v-tab-item>
        </v-tabs>
      </v-card>
    </v-tab-item>
  </v-tabs>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'
import LessonStudier from './base/LessonStudier.vue'

export default {
  name: 'CourseStudier',
  components: {LessonStudier},
  model: {
    prop: 'course',
    event: 'course-changed'
  },
  props: ['course'],
  data() {
    return {};
  },
  computed: {
    ...mapState('account', ['user']),
    weeks: function() {
      if (!this.course || !this.course.template || !this.course.template.weeks) {
        return [];
      }
      return this.course.template.weeks;
    },
    weekDates: function() {
      if (!this.course || !this.course.weekDates || !this.weeks) {
        return [];
      }
      const ret = this.weeks.map(w => 
        ({
          ...w, 
          dates: this.course.weekDates.find(wd => w.id === wd.id.weekId)
        })
      );
      return ret;
    }
  },
  beforeMount() {
    if (this.weeks) {
      this.course.template.weeks = this.course.template.weeks.map(w => {
        this.getLessonsByWeekId(w.id).then(ls => {
          if (ls) {
            ls[0].week = {id: w.id}
          }
          w.lessons = ls;
        });
        return w;
      });
    }
  },
  methods: {
    ...mapActions('courses', {
      getCourse: 'get',
    }),
    ...mapActions('lessons', {
      getLessonsByWeekId: 'getByWeekId'
    }),
  },
}
</script>

<style>

</style>
