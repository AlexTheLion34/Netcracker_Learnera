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
            <v-chip small color="green" text-color="white">
              Lecture
              <v-icon right>star</v-icon>
            </v-chip>
          </div>
          <div v-else-if="lesson.type === 'assignment'">
            <v-chip small color="blue" text-color="white">
              Assignment
              <v-icon right>person</v-icon>
            </v-chip>
          </div>
        </v-flex>
      </v-layout>
      <v-flex><v-divider style="margin: -0.5em 0 1em 0;"/></v-flex>
      <v-flex v-if="lesson.type === 'lecture'">
        <v-textarea :readonly="!editable" v-model="lesson.lectureText" box label="Lecture text" auto-grow/>
      </v-flex>
      <v-flex v-else-if="lesson.type === 'assignment'">
        <v-expansion-panel v-model="panels" expand>
          <v-expansion-panel-content v-for="(question, i) in lesson.questions" 
                                     :key="`q-${i}}`">
            <v-layout slot="header" align-center row>
              <v-flex xs4>
                <span class="title"> 
                  Question {{ question.ordering + 1 }}
                  <v-chip color="orange" disabled small text-color="white">
                    <v-avatar :color="'orange darken-' + question.points" small>{{ question.points }}</v-avatar>
                    points
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
            <question-studier v-model="lesson.questions[i]"/>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-flex>
      <v-flex><v-divider style="margin: 1em 0 0.5em 0;"/></v-flex>
      <v-flex>
        <v-layout justify-space-between row>
          <v-btn>
            Previous
          </v-btn>
          <v-flex d-flex xs4>
            <v-btn v-if="lesson.type === 'assignment'">
              Save attempt
            </v-btn>
          </v-flex>
          <v-btn>
            Next
          </v-btn>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import QuestionStudier from './QuestionStudier.vue'

export default {
  name: 'LessonStudier',
  components: {QuestionStudier},
  model: {
    prop: 'lesson',
    event: 'lesson-changed'
  },
  props: ['lesson'],
  data() {
    return {
      panels: []
    }
  },
  computed: {
    questions: function() {
      return this.lesson.questions;
    }
  },
  watch: {
    questions(val) {
      if (val) {
        this.panels = val.questions.map(_ => true);
      }
    }
  },
  beforeMount() {
    if (this.questions) {
      this.panels = this.questions.map(_ => true);
    }
  },
  methods: {
  }
}
</script>

<style>

</style>
