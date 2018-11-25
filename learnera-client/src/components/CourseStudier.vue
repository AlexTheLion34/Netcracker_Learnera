<template>
  <v-tabs v-model="activeModule" color="cyan" dark slider-color="yellow">
    <template v-for="(moduleDate, i) in moduleDates">
      <v-tab :key="`tab-w-${i}`" :disabled="moduleDate.dates.startDate > new Date()" ripple>
        <v-layout row fill-height>
          <v-flex d-flex align-center>
            <div>
              {{ moduleDate.name ? moduleDate.name : 'Module ' + (moduleDate.moduleNumber + 1) }}
            </div>
            <v-chip v-if="isCurrentModule(moduleDate)" disabled small color="blue darken-3" text-color="white">
              ACTIVE
            </v-chip>
          </v-flex>
        </v-layout>
      </v-tab>
      <v-divider :key="`div-w-${i}`" class="cyan lighten-4" inset vertical/>
    </template>

    <v-tab-item v-for="(moduleDate, i) in moduleDates" :key="`tab-item-${i}`">
      <v-card>
        <v-tabs v-model="activeLessons[i]" color="teal lighten-1" dark slider-color="yellow">
          <template v-for="(lesson, j) in moduleDate.lessons">
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

          <v-tab-item v-for="(lesson, j) in moduleDate.lessons"
                      :key="`tab-item-w-${i}-l-${j}`">
            <v-card v-if="lesson.id" flat>
              <lesson-studier v-model="course.template.modules[i].lessons[j]"
                              :course-id="course.id" :module-id="moduleDate.id"
                              :not-available="isLessonDisabled(lesson, moduleDate)"
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
      activeModule: null,
      activeLessons: []
    };
  },
  computed: {
    ...mapState('account', ['user']),
    modules: function() {
      if (!this.course || !this.course.template || !this.course.template.modules) {
        return [];
      }
      return this.course.template.modules;
    },
    moduleDates: function() {
      if (!this.course || !this.course.moduleDates || !this.modules) {
        return [];
      }
      const ret = this.modules.map(w =>
        ({
          ...w, 
          dates: this.course.moduleDates.find(wd => w.id === wd.id.moduleId)
        })
      );
      return ret;
    }
  },
  beforeMount() {
    if (this.modules) {
      this.course.template.modules = this.course.template.modules.map(module => {
        this.getLessonsByModuleId(module.id).then(ls => {
          ls.forEach(l => {
            if (l.type === 'assignment') {
              l.questions = l.questions.map(q => ({...q, userAttempt: {answer: '', score: 0.0, submitted: false}}));
            }
          })
          if (ls) {
            ls[0].module = {id: module.id}
          }
          module.lessons = ls;
        }).catch(e => this.alertError(`Failed to get lessons: ${e.data.message}`))
          .then(_ => this.getLatestUserModuleAttempts({userId: this.user.id, moduleId: module.id}))
          .then(qas => {
            qas.forEach(qa => {
              
              let questionIndex = -1;
              let lessonIndex = module.lessons.findIndex(l => l.type === 'assignment' 
                && (questionIndex = l.questions.findIndex(q => q.id === qa.question)) !== -1);
              if (lessonIndex === -1) {
                console.error('QUESTION NOT FOUND: ', JSON.stringify(qa));
                return;
              }
              this.$set(module.lessons[lessonIndex].questions[questionIndex], 'userAttempt', {...qa, submitted: true});
            });
          });
        return module;
      });
      this.activeLessons = this.modules.map(_ => 0);
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
      getLessonsByModuleId: 'getByModuleId'
    }),
    ...mapActions('questionAttempts', {
      getLatestUserModuleAttempts: 'getLatestUserModuleAttempts'
    }),
    hasPrev(moduleIndex, lessonIndex) {
      return !(moduleIndex === 0 & lessonIndex === 0);
    },
    hasNext(moduleIndex, lessonIndex) {
      return !(moduleIndex === this.modules.length - 1 && lessonIndex === this.modules[moduleIndex].lessons.length - 1);
    },
    goPrev() {
      const activeModule = parseInt(this.activeModule);
      const activeLesson = parseInt(this.activeLessons[activeModule]);
      if (activeLesson === 0) {
        this.activeModule = activeModule - 1;
        this.$set(this.activeLessons, activeModule - 1, this.modules[activeModule - 1].lessons.length - 1);
      } else {
        this.$set(this.activeLessons, activeModule, activeLesson - 1);
      }
    },
    goNext() {
      const activeModule = parseInt(this.activeModule);
      const activeLesson = parseInt(this.activeLessons[activeModule]);
      if (activeLesson === this.modules[activeModule].lessons.length - 1) {
        this.activeModule = activeModule + 1;
        this.$set(this.activeLessons, activeModule, 0);
      } else {
        this.$set(this.activeLessons, activeModule, activeLesson + 1);
      }
    },
    isCurrentModule(moduleDate) {
      return new Date(moduleDate.dates.startDate) <= Date.now() && new Date(moduleDate.dates.endDate) >= Date.now();
    },
    isLessonDisabled(lesson, moduleDate) {
      if (lesson.type === "lecture") {
        return false;
      }
      return !this.isCurrentModule(moduleDate);
    }
  },
}
</script>

<style>

</style>
