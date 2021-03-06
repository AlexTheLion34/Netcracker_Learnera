<template>
  <v-container fluid>
    <v-layout column>
      <v-layout align-center row>
        <v-flex xs6>
          <v-subheader class="headline"> 
            {{ lesson.name ? lesson.name : ('Lesson ' + (lesson.ordering + 1)) }}
          </v-subheader>
        </v-flex>
        <v-spacer/>
        <v-flex d-flex xs2>
          <div v-if="lesson.type === 'lecture'">
            <v-chip disabled small color="green" text-color="white">
              Lecture
              <v-icon right>star</v-icon>
            </v-chip>
          </div>
          <div v-else-if="lesson.type === 'assignment'">
            <v-chip disabled small color="blue" text-color="white">
              Assignment
              <v-icon right>person</v-icon>
            </v-chip>
          </div>
        </v-flex>
      </v-layout>
      <v-flex><v-divider style="margin: -0.5em 0 1em 0;"/></v-flex>
      <v-flex v-if="lesson.type === 'lecture'">
        <v-textarea v-model="lesson.lectureText" box label="Lecture text" readonly rows="20"/>
      </v-flex>
      <v-flex v-else-if="lesson.type === 'assignment'">
        <v-expansion-panel v-model="panels" expand>
          <v-expansion-panel-content v-for="(question, i) in questions" 
                                     :key="`q-${i}}`">
            <v-card slot="header" flat>                         
              <v-layout align-center row>
                <v-flex>
                  <span class="title"> 
                    Question {{ question.ordering + 1 }}
                    <v-chip color="orange" disabled small text-color="white">
                      <v-avatar :color="'orange darken-' + question.points" small>{{ question.points }}</v-avatar>
                      pts
                    </v-chip>
                    <v-chip v-if="questionPassed(question)" :color="questionColor(question)" disabled small text-color="white">
                      {{ questionPassed(question) === 'yes' ? 'PASS' : 'FAIL' }}
                    </v-chip>
                  </span>
                </v-flex>
                <v-spacer/>
                <div v-if="question.type === 'fixed'">
                  <v-chip disabled small color="green" text-color="white">
                    Fixed
                    <v-icon right>star</v-icon>
                  </v-chip>
                </div>
                <div v-else-if="question.type === 'regex'">
                  <v-chip disabled small color="blue" text-color="white">
                    Regex
                    <v-icon right>person</v-icon>
                  </v-chip>
                </div>
                <div v-else-if="question.type === 'mc'">
                  <v-chip disabled small color="red" text-color="white">
                    Multiple choice
                    <v-icon right>person</v-icon>
                  </v-chip>
                </div>
              </v-layout>
            </v-card>
            <question-studier v-model="questions[i]" :not-available="notAvailable"/>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-flex>
      <v-flex><v-divider style="margin: 1em 0 0.5em 0;"/></v-flex>
      <v-flex>
        <v-layout justify-space-between row>
          <v-btn :disabled="!hasPrev" @click="doPrev()">
            Previous
          </v-btn>
          <v-flex d-flex xs4>
            <v-btn v-if="lesson.type === 'assignment'"
                   :disabled="notAvailable || questionPassed(questions[0]).length > 0" @click="doSave()">
              Save attempt
            </v-btn>
          </v-flex>
          <v-btn :disabled="!hasNext" @click="doNext()">
            Next
          </v-btn>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import QuestionStudier from './QuestionStudier.vue'

export default {
  name: 'LessonStudier',
  components: {QuestionStudier},
  model: {
    prop: 'lesson',
    event: 'lesson-changed'
  },
  props: ['lesson', 'courseId', 'moduleId', 'hasPrev', 'hasNext', 'notAvailable'],
  data() {
    return {
      panels: [],
      questions: [],
      answers: []
    }
  },
  computed: {
    ...mapState('account', {
      currentUser: 'user'
    }),
  },
  watch: {
  },
  beforeMount() {
    if (this.lesson.questions) {
      this.panels = this.questions.map(_ => true);
      this.questions = this.lesson.questions.map(q => {
        if (q.id) {
          return q;
        } else {
          return {
            ...q,
            userAttempt: {answer: '', score: 0.0, submitted: false}
          }
        }
      });
      this.answers = this.questions.map(_ => ({}));
    }
  },
  methods: {
    ...mapActions('alert', {
      alertError: 'error',
      alertSuccess: 'success'
    }),
    ...mapActions('questionAttempts', {
      scoreAttempts: 'scoreAttempts',
      getLatestUserModuleAttempts: 'getLatestUserModuleAttempts'
    }),
    doPrev() {
      this.$emit('go-prev');
    },
    doNext() {
      this.$emit('go-next');
    },
    doSave() {
      const ret = this.questions.map(q => ({
        student: this.currentUser.id,
        course: this.courseId,
        question: {id: q.id, answer: q.answer, points: q.points, type: q.type, variants: q.variants},
        answer: (q.type === 'mc') 
          ? q.userAttempt.answer.reduce((acc, x) => acc + +x, '') 
          : q.userAttempt.answer
      }));
      if (ret.length > 0) {
        ret[0].student = {id: this.currentUser.id};
        ret[0].course = {id: this.courseId};
      }

      this.scoreAttempts(ret).then(attempts => {
        this.alertSuccess('Attempt scored successfully');
        attempts.forEach(attempt => {
          const idx = this.questions.findIndex(q => q.id === attempt.question);
          this.questions[idx].userAttempt = {...attempt, submitted: true};
        });
      }).catch(e => this.alertError(`Attempt failed to be scored: ${e.data.message}`));
    },
    questionPassed(question) {
      if (!question.userAttempt.submitted) {
        return '';
      }

      return question.userAttempt.score === 0 ? 'no' : 'yes';
    },
    questionColor(question) {
      const passed = this.questionPassed(question);
      return passed === 'yes' ? 'green' : passed === 'no' ? 'red' : '';
    }
  }
}
</script>

<style>

</style>
