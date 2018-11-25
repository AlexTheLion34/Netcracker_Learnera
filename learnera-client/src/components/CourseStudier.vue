<template>
  <v-tabs v-model="activeWeek" color="cyan" dark slider-color="yellow">
    <template v-for="(weekDate, i) in weekDates">
      <v-tab :key="`tab-w-${i}`" :disabled="weekDate.dates.startDate > new Date()" ripple>
        <v-layout row fill-height>
          <v-flex d-flex align-center>
            <div>
              {{ weekDate.name ? weekDate.name : 'Week ' + (weekDate.weekNumber + 1) }}
            </div>
            <v-chip v-if="isCurrentWeek(weekDate)" disabled small color="blue darken-3" text-color="white">
              ACTIVE
            </v-chip>
          </v-flex>
        </v-layout>
      </v-tab>
      <v-divider :key="`div-w-${i}`" class="cyan lighten-4" inset vertical/>
    </template>

    <v-tab-item v-for="(weekDate, i) in weekDates" :key="`tab-item-${i}`">
      <v-card>
        <v-tabs v-model="activeLessons[i]" color="teal lighten-1" dark slider-color="yellow">
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
              <lesson-studier v-model="course.template.weeks[i].lessons[j]" 
                              :course-id="course.id" :week-id="weekDate.id" 
                              :not-available="isLessonDisabled(lesson, weekDate)"
                              :has-prev="hasPrev(i, j)" :has-next="hasNext(i, j)"
                              @go-prev="goPrev()" @go-next="goNext()"/>
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
    return {
      activeWeek: null,
      activeLessons: []
    };
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
        }).catch(e => this.alertError(`Failed to get lessons: ${e.data.message}`));
        return w;
      });
      this.activeLessons = this.weeks.map(_ => 0);
    }
  },
  methods: {
    ...mapActions('alert', {
      alertError: 'error',
      alertSuccess: 'success'
    }),
    ...mapActions('courses', {
      getCourse: 'get',
    }),
    ...mapActions('lessons', {
      getLessonsByWeekId: 'getByWeekId'
    }),
    hasPrev(weekIndex, lessonIndex) {
      return !(weekIndex === 0 & lessonIndex === 0);
    },
    hasNext(weekIndex, lessonIndex) {
      return !(weekIndex === this.weeks.length - 1 && lessonIndex === this.weeks[weekIndex].lessons.length - 1);
    },
    goPrev() {
      const activeWeek = parseInt(this.activeWeek);
      const activeLesson = parseInt(this.activeLessons[activeWeek]);
      if (activeLesson === 0) {
        this.activeWeek = activeWeek - 1;
        this.$set(this.activeLessons, activeWeek - 1, this.weeks[activeWeek - 1].lessons.length - 1); 
      } else {
        this.$set(this.activeLessons, activeWeek, activeLesson - 1);
      }
    },
    goNext() {
      const activeWeek = parseInt(this.activeWeek);
      const activeLesson = parseInt(this.activeLessons[activeWeek]);
      if (activeLesson === this.weeks[activeWeek].lessons.length - 1) {
        this.activeWeek = activeWeek + 1;
        this.$set(this.activeLessons, activeWeek, 0);
      } else {
        this.$set(this.activeLessons, activeWeek, activeLesson + 1);
      }
    },
    isCurrentWeek(weekDate) {
      return new Date(weekDate.dates.startDate) <= Date.now() && new Date(weekDate.dates.endDate) >= Date.now();
    },
    isLessonDisabled(lesson, weekDate) {
      if (lesson.type === "lecture") {
        return false;
      }
      return !this.isCurrentWeek(weekDate);
    }
  },
}
</script>

<style>

</style>
